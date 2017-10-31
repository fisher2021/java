package com.upd.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.upd.business.constant.BacklogType;
import com.upd.business.constant.ORGType;
import com.upd.business.dao.UserDao;
import com.upd.business.entity.Backlog;
import com.upd.business.entity.ORG;
import com.upd.business.entity.ORGUser;
import com.upd.business.entity.User;
import com.upd.business.form.UserForm;
import com.upd.business.service.*;
import com.upd.business.utils.ExcelUtil;
import com.upd.common.basis.base.BusinessException;
import com.upd.common.basis.rest.RestErrorCode;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.DateUtil;
import com.upd.common.util.ExcelUtils;
import com.upd.common.util.TokenUtils;
import com.upd.common.util.easemob.Easemob;
import com.upd.common.util.page.Pagination;
import com.upd.common.util.security.MD5Utils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

/**
 * Created by Administrator on 2017/5/3.
 */
@Service("userService")
public class UserServiceImpl  extends BaseServiceImpl<User,Integer> implements UserService {
    @Autowired
    public UserDao userDao;
    @Autowired
    DictTypeService dictTypeService;
    @Autowired
    BacklogService backlogService;
    @Autowired
    AppraisementService appraisementService;
    @Autowired
    VoteService voteService;
    @Autowired
    ORGService orgService;
    @Autowired
    ORGUserService orgUserService;
    @Autowired
    public void setBaseDao(UserDao dao) {
        this.baseDao = dao;
    }


    /**
     * 查询用户详情
     * @param form
     * @return
     */
    public User info(UserForm form) {
        return findOne("From User where"+form.toQueryDescription(),form.values());
    }

    /**
     * 获取token
     * @param user
     * @return
     */
    public String getToken(User user) {
        Map< String,Object > headerClaims =new HashedMap();
        headerClaims.put("id",user.getId());
        headerClaims.put("account",user.getAccount());
        headerClaims.put("password",user.getPassword());
        headerClaims.put("unitCode",user.getHighestOrg().getCode().substring(0,ORGType.SYS_DEFAULT.getCodeLength() +ORGType.UNIT.getCodeLength()));
        return TokenUtils.create(headerClaims, DateUtil.addDate(new Date(),15));
    }

    /**
     * 修改密码
     * @return
     */
    public boolean upPassword(String account, String password, Integer id){
//        baseDao.updateByHql("update User user set user.password=md5(?) where user.id=? ",account+password,id);
        baseDao.updateByHql("update User user set user.password=md5(?) where user.id=? ",password,id);
        //修改聊天账号密码
        Easemob.newPassword(account,get(id).getPassword());
        return true;
    }

    @Override
    public void create(User user) {
        this.save(user);
        List userList=new ArrayList();
        Map<String, String> emuser=new HashMap<String, String>();
        emuser.put("username",user.getAccount());
        emuser.put("password",user.getPassword());
        userList.add(emuser);
        Easemob.createUser(userList);
    }

    @Override
    public Pagination page(Pagination page, UserForm form) {
        if (form.getUserHave() == null){
            form.setUserHave(false);
        }
        if (form.backlogId != null){
            //在线缴费、民主评议和在线投票，只查询未完成该项任务的用户
            Backlog backlog = backlogService.get(form.backlogId);
            if (backlog.getType() == BacklogType.ZXJF) {
                return userDao.findbypage("select count(*) from User where id in (SELECT user.id from PartyMembershipDues where status = FALSE and year = '"+backlog.getYear()+"') and "+form.toQueryDescription(),page,
                        "from User where id in (SELECT user.id from PartyMembershipDues where status = FALSE and year = '"+backlog.getYear()+"') and "+form.toQueryDescription(),form.values());
            }else if (backlog.getType() == BacklogType.MZPY) {
                String user = appraisementService.get(backlog.getMissionId()).getVoter();//查询已评议的用户
                if (user != null && !user.equals("")){
                    return userDao.findbypage("select count(*) from User where id not in ("+user+") and "+form.toQueryDescription(),page,"from User where id not in ("+user+") and "+form.toQueryDescription(),form.values());
                }
            }else if (backlog.getType() == BacklogType.ZXTP) {
                String user = voteService.get(backlog.getMissionId()).getVoter();//查询已评议的用户
                if (user != null && !user.equals("")){
                    return userDao.findbypage("select count(*) from User where id not in ("+user+") and "+form.toQueryDescription(),page,"from User where id not in ("+user+") and "+form.toQueryDescription(),form.values());
                }
            }
        }
        return userDao.findbypage("select count(*) from User where"+form.toQueryDescription(),page,"from User where"+form.toQueryDescription(),form.values());
    }

    @Override
    public List<User> orgList(UserForm form) {
        return userDao.find("from User where"+form.toQueryDescription(),form.values());
    }

    @Override
    public List<User> findByORGforBacklog(Integer ORGId,Integer backlogId) {
        //在线缴费、民主评议和在线投票，只查询未完成该项任务的用户
        Backlog backlog = backlogService.get(backlogId);
        if (backlog.getType() == BacklogType.ZXJF) {
            return userDao.find("from User where isDeleted = false and id in (SELECT user.id from PartyMembershipDues where status = FALSE and year = '" + backlog.getYear() + "') " +
                    "AND id in (select user.id from ORGUser where org.id = ?)",ORGId);
        } else if (backlog.getType() == BacklogType.MZPY) {
            String user = appraisementService.get(backlog.getMissionId()).getVoter();//查询已评议的用户
            if (user != null && !user.equals("")) {
                return userDao.find("from User where isDeleted = false and id not in (" + user + ") AND id in (select user.id from ORGUser where org.id = ?)",ORGId);
            }
        } else if (backlog.getType() == BacklogType.ZXTP) {
            String user = voteService.get(backlog.getMissionId()).getVoter();//查询已投票的用户
            if (user != null && !user.equals("")) {
                return userDao.find("from User where isDeleted = false and id not in (" + user + ") AND id in (select user.id from ORGUser where org.id = ?)",ORGId);
            }
        }
        //其他通过组织查询
        return findByORG(ORGId);
    }

    @Override
    public void addUser(User user,Integer[] orgs) {
//        user.setPassword(MD5Utils.getMD5(user.getAccount()+user.getPassword()));//密码加密
        user.setPassword(MD5Utils.getMD5(user.getPassword()));//密码加密
        //设置用户默认值
        if (user.getEmployeeNumber() == null){
            user.setEmployeeNumber(user.getAccount());
        }
        if (user.getPhone() == null){
            user.setPhone(user.getAccount());
        }
        if (user.getNickname() == null){
            user.setNickname(user.getAccount());
        }
        if (user.getLeader() == null){
            user.setLeader(false);
        }
        if (user.getPoints() == null){
            user.setPoints(0);
        }
        //设置组织
        if (orgs != null && orgs.length != 0){
            List<ORG> orgList = new ArrayList<>();
            for (Integer id : orgs){
                if (id != null){
                    ORG org = orgService.get(id);
                    if (org.getLevel() == ORGType.SYS_DEFAULT || org.getLevel() == ORGType.UNIT){
                        throw new BusinessException(RestErrorCode.PARAM,"当前组织不能创建用户！");
                    }
                    orgList.add(org);
                }
            }
            user.setOrgs(orgList);
            //组织按等级排序
            Collections.sort(orgList, new Comparator<ORG>() {
                @Override
                public int compare(ORG a, ORG b) {
                    return a.getLevel().ordinal() - (b.getLevel().ordinal());
                }
            });
            user.setHighestOrg(orgList.get(0));
            dictTypeService.updateORGVersion();//更新联系人版本
        }
        user.initTime();
        userDao.save(user);
        //创建环信账号
        List<Map> userlist=new ArrayList<Map>();
        Map map=new HashMap();
        map.put("username",user.getAccount());
        map.put("password",user.getPassword());
        userlist.add(map);
        Easemob.createUser(userlist);
        //设置各级组织默认按创建ID排序
        int id = user.getId();
        user.setGroupRank(id);
        user.setCommitteeRank(id);
        user.setBranchRank(id);
        userDao.update(user);
    }

    @Override
    public User editUser(User user,Integer[] orgs) {
        User user1 = userDao.get(user.getId());
        if(user.getNickname() != null && !user.getNickname().equals("")){
            user1.setNickname(user.getNickname());
            Easemob.upNickName(user1.getAccount(),user1.getNickname());
            dictTypeService.updateORGVersion();//更新联系人版本
        }
        if (user.getEmployeeNumber() != null && !user.getEmployeeNumber().equals("")){
            user1.setEmployeeNumber(user.getEmployeeNumber());
        }
        if (user.getLeader() != null && !user.getLeader().equals("")){
            user1.setLeader(user.getLeader());
        }
        if (user.getImage() != null && !user.getImage().equals("")){
            user1.setImage(user.getImage());
        }
//        if (user.getOrg() != null && !user.getOrg().equals("")){
//            user1.setOrg(user.getOrg());
//
//            dictTypeService.updateORGVersion();//更新联系人版本
//        }
        if (user.getSex() != null && !user.getSex().equals("")){
            user1.setSex(user.getSex());
        }
        if (user.getNativePlace() != null && !user.getNativePlace().equals("")){
            user1.setNativePlace(user.getNativePlace());
        }
        if (user.getNation() != null && !user.getNation().equals("")){
            user1.setNation(user.getNation());
        }
        if (user.getJob() != null && !user.getJob().equals("")){
            user1.setJob(user.getJob());
        }
        if (user.getEducation() != null && !user.getEducation().equals("")){
            user1.setEducation(user.getEducation());
        }
        if (user.getDuty() != null && !user.getDuty().equals("")){
            user1.setDuty(user.getDuty());
        }
        if (user.getBirth() != null && !user.getBirth().equals("")){
            user1.setBirth(user.getBirth());
        }
        if (user.getPartyTime() != null && !user.getPartyTime().equals("")){
            user1.setPartyTime(user.getPartyTime());
        }
        if (user.getPhone() != null && !user.getPhone().equals("")){
            user1.setPhone(user.getPhone());
        }
        if (user.getAddress() != null && !user.getAddress().equals("")){
            user1.setAddress(user.getAddress());
        }
        if (user.getIdCard() != null && !user.getIdCard().equals("")){
            user1.setIdCard(user.getIdCard());
        }
        if (user.getContact() != null && !user.getContact().equals("")){
            user1.setContact(user.getContact());
        }
        if (user.getContactMobile() != null && !user.getContactMobile().equals("")){
            user1.setContactMobile(user.getContactMobile());
        }
        if (user.getTrain() != null && !user.getTrain().equals("")){
            user1.setTrain(user.getTrain());
        }
        if (user.getAward() != null && !user.getAward().equals("")){
            user1.setAward(user.getAward());
        }
        if (user.getPunishment() != null && !user.getPunishment().equals("")){
            user1.setPunishment(user.getPunishment());
        }
        user1.setDifficult(user.isDifficult());
        if (orgs != null && orgs.length != 0){
            orgUserService.deleteByUser(user.getId());
            List<ORG> orgList = new ArrayList<>();
            for (Integer id : orgs){
                if (id != null){
                    ORG org = orgService.get(id);
                    if (org.getLevel() == ORGType.SYS_DEFAULT || org.getLevel() == ORGType.UNIT){
                        throw new BusinessException(RestErrorCode.PARAM,"当前组织不能创建用户！");
                    }
                    orgList.add(org);
                }
            }
            user1.setOrgs(orgList);
            //组织按等级排序
            Collections.sort(orgList, new Comparator<ORG>() {
                @Override
                public int compare(ORG a, ORG b) {
                    return a.getLevel().ordinal() - (b.getLevel().ordinal());
                }
            });
            user1.setHighestOrg(orgList.get(0));
            dictTypeService.updateORGVersion();//更新联系人版本
        }
        user1.initTime();
        userDao.update(user1);
        return user1;
    }

    @Override
    public void deleteUser(Integer id) {
        User user = userDao.get(id);
        Easemob.deleteUser(user.getAccount());
        userDao.delete(user);
    }

    @Override
    public boolean userInfoOnly(User user) {
        user = this.findByAccount(user.getAccount());
        if (user == null) {
            return true;
        }
        return false;
    }

    @Override
    public void deleteBatch(String ids) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            userDao.delete(userDao.get(Integer.parseInt(id)));
            Easemob.deleteUser(userDao.get(Integer.parseInt(id)).getAccount());
        }
    }

    @Override
    public Map<String,Integer> countUser(Integer orgId) {
        Map<String,Integer> map = new HashMap<String, Integer>();
        int all = userDao.findTotalCount("SELECT count(*) from ORGUser where user.isDeleted = false and org.id = ?",orgId);
        int man = userDao.findTotalCount("SELECT count(*) from User where isDeleted = false and id in (SELECT user.id from ORGUser where org.id = ?) and sex = '男'",orgId);
        int han = userDao.findTotalCount("SELECT count(*) from User where isDeleted = false and id in (SELECT user.id from ORGUser where org.id = ?) and nation like '汉%'",orgId);
        int age = userDao.findTotalCount("SELECT count(*) from User where isDeleted = false and id in (SELECT user.id from ORGUser where org.id = ?) and DATE_FORMAT(FROM_DAYS(TO_DAYS(NOW())-TO_DAYS(birth)), '%Y')+0 <= 35",orgId);
        int edu = userDao.findTotalCount("SELECT count(*) from User where isDeleted = false and id in (SELECT user.id from ORGUser where org.id = ?) and education > 3",orgId);
        map.put("all",all);//总人数
        map.put("man",man);//男党员
        map.put("woman",all - man);//女党员
        map.put("han",han);//汉族
        map.put("min",all - han);//少数民族
        map.put("age",age);//年龄小于35岁的
        map.put("edu",edu);//大专学历以上
        return map;
    }

    @Override
    public String addBatch(MultipartFile file) throws IOException, InvalidFormatException {
        List<User> users = ExcelUtil.readUserExcel(file);
        List<User> repeat = new ArrayList<>();
        String message = "";
        for (User user:users){
            user.setAccount(user.getPhone());
            if (userInfoOnly(user)){
                user.setPassword("123456");
                this.addUser(user,null);
                message = "导入成功！";
            }else {
                repeat.add(user);
            }
        }
        if (repeat != null && repeat.size() != 0){
            message = repeat.size()+"条用户信息重复！";
        }
        return message;
    }

    @Override
    public User findByAccount(String account) {
        User user = userDao.findOne("from User where isDeleted = false and account = ? ",account);
        return user;
    }

    @Override
    public List<User> findByORG(Integer ORGId) {
        List<ORGUser> orgUsers = orgUserService.find("from ORGUser where user.isDeleted = false and org.id = ?",ORGId);
        List<User> users = new ArrayList<>();
        for (ORGUser orgUser : orgUsers){
            users.add(orgUser.getUser());
        }
        return users;
    }

    @Override
    public User getByNumber(String number) {
        User user = userDao.findOne("from User where isDeleted = false and employeeNumber = ? ",number);
        return user;
    }

    @Override
    public void rank(Integer id,Integer otherId,Integer level) {
        User user = userDao.get(id);
        User user1 = userDao.get(otherId);
        if (level == ORGType.PARTY_GROUP.ordinal()){
            Integer temp = user.getGroupRank();
            user.setGroupRank(user1.getGroupRank());
            user1.setGroupRank(temp);
        }else if (level == ORGType.PARTY_COMMITTEE.ordinal()){
            Integer temp = user.getCommitteeRank();
            user.setCommitteeRank(user1.getCommitteeRank());
            user1.setCommitteeRank(temp);
        }else if (level == ORGType.PARTY_BRANCH.ordinal()){
            Integer temp = user.getBranchRank();
            user.setBranchRank(user1.getBranchRank());
            user1.setBranchRank(temp);
        }
        dictTypeService.updateORGVersion();//更新联系人版本
        userDao.update(user);
        userDao.update(user1);
    }

    @Override
    public void export(HttpServletResponse response, Integer orgId) {
        String fileName="党员信息.xls";
        response.reset();// 不加这一句的话会出现下载错误
        try {
            response.setHeader("Content-disposition", "attachment;filename="+new String(fileName.getBytes("UTF-8"),"iso8859-1"));  // 设定输出文件头
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("application/vnd.ms-excel");   // 定义输出类型
        response.setCharacterEncoding("utf-8");
        UserForm form = new UserForm();
        if (orgId != null && !orgId.equals("")){
            form.setOrgId(orgId);
            form.setLevel(orgService.get(orgId).getLevel());
        }
        List<User> users = orgList(form);
        List objs = new ArrayList();
        for (User user:users){
            objs.add(user);
        }
        List<String> displayProperts=new ArrayList<>();
        displayProperts.add("nickname");
//        displayProperts.add("employeeNumber");
        displayProperts.add("phone");
        displayProperts.add("sex");
        displayProperts.add("nativePlace");
        displayProperts.add("nation");
        displayProperts.add("job");
        displayProperts.add("education");
        displayProperts.add("duty");
        displayProperts.add("birth");
        displayProperts.add("partyTime");
        displayProperts.add("points");
        displayProperts.add("train");
        displayProperts.add("award");
        displayProperts.add("punishment");
        List<List<Object>> values = ExcelUtils.excelValues(objs, displayProperts);
        List<String> titleNames = new ArrayList<>();
        titleNames.add("姓名");
//        titleNames.add("工号");
        titleNames.add("手机号码");
        titleNames.add("性别");
        titleNames.add("籍贯");
        titleNames.add("民族");
        titleNames.add("岗位");
        titleNames.add("学历");
        titleNames.add("党内职务");
        titleNames.add("出生年月");
        titleNames.add("入党时间");
        titleNames.add("积分");
        titleNames.add("党内培训记录");
        titleNames.add("奖励记录");
        titleNames.add("惩罚记录");
        HSSFWorkbook wb = ExcelUtils.excel(titleNames, values, "Sheet1");
        try {
            OutputStream out = response.getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getLeaders(String unitCode) {
        return userDao.find("from User where leader = 1 and operator.org.code like '"+unitCode+"%'");
    }

    @Override
    public Pagination findMin(Pagination page,Integer ORGId,ORGType type) {
        return userDao.findbypage(page,"from User where isDeleted = false and id in (SELECT user.id from ORGUser where org.id = ?) and (nation not like '%汉%' or nation is null) order by ?",ORGId,getRank(type));
    }

    @Override
    public Pagination findWomen(Pagination page,Integer ORGId,ORGType type) {
        return userDao.findbypage(page,"from User where isDeleted = false and id in (SELECT user.id from ORGUser where org.id = ?) and (sex != '男' or sex is null) order by ?",ORGId,getRank(type));
    }
    public String getRank(ORGType type){
        if (type == ORGType.PARTY_GROUP){
            return "groupRank";
        }else if (type == ORGType.PARTY_COMMITTEE){
            return "committeeRank";
        }else if (type == ORGType.PARTY_BRANCH){
            return "branchRank";
        }
        return null;
    }
}
