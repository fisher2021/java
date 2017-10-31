package com.upd.business.service.impl;

import com.upd.business.dao.PartyMembershipDuesDao;
import com.upd.business.entity.PartyMembershipDues;
import com.upd.business.entity.User;
import com.upd.business.form.PartyMembershipDuesForm;
import com.upd.business.form.UserForm;
import com.upd.business.service.PartyMembershipDuesService;
import com.upd.business.service.UserService;
import com.upd.business.utils.ExcelUtil;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.ExcelUtils;
import com.upd.common.util.page.Pagination;
import com.upd.common.util.queryParameter.QueryParam;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/5/31.
 */
@Service("partyMembershipDuesService")
public class PartyMembershipDuesServiceImpl extends BaseServiceImpl<PartyMembershipDues,Integer>  implements PartyMembershipDuesService {
    @Autowired
    public PartyMembershipDuesDao dao;
    @Autowired
    public void setBaseDao(PartyMembershipDuesDao dao) {
        this.baseDao = dao;
    }
    @Autowired
    UserService userService;

    @Override
    public String addBatch(MultipartFile file,Integer type,String year) throws IOException, InvalidFormatException {
         List<PartyMembershipDues> partyMembershipDues = new ArrayList<>();
        List<PartyMembershipDues> repeat = new ArrayList<>();
        String message = "";
        int count = 0;
        if (type == 0){
            partyMembershipDues = ExcelUtil.readDuesAmountExcel(file,year);
            for (PartyMembershipDues dues:partyMembershipDues){
                User user = userService.findByAccount(dues.getUser().getAccount());
                if (user != null){//判断用户是否存在
                    PartyMembershipDues dues1 = existUser(dues.getUser().getAccount(),dues.getYear());
                    if (dues1 == null){//判断党费信息是否存在，不存在则创建应缴
                        dues.setUser(user);
                        double money = (dues.getAmount().subtract(dues.getFeeReceived())).doubleValue();
                        if (money > 0 ){
                            dues.setStatus(false);
                        }else {
                            dues.setStatus(true);
                        }
                        dues.initTime();
                        dao.save(dues);
                        count++;
                    }else {
                        repeat.add(dues);
                    }
                }else {
                    message = "请先导入"+dues.getUser().getAccount()+"用户（确认工号是否正确）！";
                }
            }
            if (repeat != null && repeat.size() != 0){
                message = repeat.size()+"条党费信息已存在！";
            }
            if (message == null || message.equals("")){
                message = "成功导入"+count+"条已缴党费信息！";
            }
        }else if (type == 1){
            partyMembershipDues = ExcelUtil.readDuesFeeReceivedExcel(file,year);
            for (PartyMembershipDues dues:partyMembershipDues){
                User user = userService.findByAccount(dues.getUser().getAccount());
                if (user != null){//判断用户是否存在
                    PartyMembershipDues dues1 = existUser(dues.getUser().getAccount(),dues.getYear());
                    if (dues1 != null){//判断党费信息是否存在,存在则更新实缴
                        dues1.setFeeReceived(dues.getFeeReceived());
                        double money = (dues1.getAmount().subtract(dues1.getFeeReceived())).doubleValue();
                        if (money > 0 ){
                            dues1.setStatus(false);
                        }else {
                            dues1.setStatus(true);
                        }
                        dues1.initTime();
                        dao.update(dues1);
                        count++;
                    }else {
                        repeat.add(dues);
                    }
                }else {
                    message = "请先导入"+dues.getUser().getAccount()+"用户（确认工号是否正确）！";
                }
            }
            if (repeat != null && repeat.size() != 0){
                message = repeat.size()+"条党费信息导入失败（请先导入应缴党费信息）！";
            }
            if (message == null || message.equals("")){
                message = "成功导入"+count+"条已缴党费信息！";
            }
        }
        return message;
    }

    @Override
    public PartyMembershipDues existUser(String account,String year) {
        PartyMembershipDues dues = dao.findOne("from PartyMembershipDues where user.account = ? and year = ?",account,year);
        return dues;
    }

    @Override
    public void edit(PartyMembershipDues dues) {
        PartyMembershipDues partyMembershipDues = dao.get(dues.getId());
        if (dues.getAmount() != null && !dues.getAmount().equals("")){
            partyMembershipDues.setAmount(dues.getAmount());
        }
        if (dues.getFeeReceived() != null && !dues.getFeeReceived().equals("")){
            partyMembershipDues.setFeeReceived(dues.getFeeReceived());
        }
        if (dues.getYear() != null && !dues.getYear().equals("")){
            partyMembershipDues.setYear(dues.getYear());
        }
        if (dues.getStatus() != null && !dues.getStatus().equals("")){
            partyMembershipDues.setStatus(dues.getStatus());
        }
        partyMembershipDues.initTime();
        dao.update(partyMembershipDues);
    }

    @Override
    public void deleteBatch(String ids) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            dao.delete(dao.get(Integer.parseInt(id)));
        }
    }

    @Override
    public void export(HttpServletResponse response, Integer orgId, String month, Boolean status) {
        String fileName="党费信息.xls";
        response.reset();// 不加这一句的话会出现下载错误
        try {
            response.setHeader("Content-disposition", "attachment;filename="+new String(fileName.getBytes("UTF-8"),"iso8859-1"));  // 设定输出文件头
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("application/vnd.ms-excel");   // 定义输出类型
        response.setCharacterEncoding("utf-8");
        PartyMembershipDuesForm form = new PartyMembershipDuesForm();
        form.setOrgId(orgId);
        form.setYear(month);
        form.setStatus(status);
        List<PartyMembershipDues> dues = dao.find("from PartyMembershipDues where "+form.toQueryDescription(),form.values());
        List objs = new ArrayList();
        for (PartyMembershipDues p:dues){
            if (p.getStatus()){
                p.setPayed("已缴");
            }else {
                p.setPayed("未缴");
            }
            objs.add(p);
        }
        List<String> displayProperts=new ArrayList<>();
        displayProperts.add("user.nickname");
//        displayProperts.add("user.employeeNumber");
        displayProperts.add("user.phone");
        displayProperts.add("year");
        displayProperts.add("amount");
        displayProperts.add("feeReceived");
        displayProperts.add("payed");
        List<List<Object>> values = ExcelUtils.excelValues(objs, displayProperts);
        List<String> titleNames = new ArrayList<>();
        titleNames.add("姓名");
//        titleNames.add("工号");
        titleNames.add("手机号码");
        titleNames.add("年月");
        titleNames.add("应缴费");
        titleNames.add("已缴费");
        titleNames.add("是否缴纳");
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

}
