package com.upd.business.service;

import com.upd.business.constant.ORGType;
import com.upd.business.entity.User;
import com.upd.business.form.ArticleForm;
import com.upd.business.form.UserForm;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/3.
 */
public interface UserService extends BaseService<User, Integer> {

    /**
     * 查询用户详情
     * @param form
     * @return
     */
    User info(UserForm form);

    /**
     * 获取token
     * @param user
     * @return
     */
    String getToken(User user);

    /**
     * 修改密码
     *
     * @param account 账号
     * @param password 新密码明文
     * @param id 修改用户id
     * @return
     */
    boolean upPassword(String account, String password, Integer id);

    /**
     * 创建用户
     * @param user
     */
    void create(User user);

    /**
     * 分页查询用户
     * @param page 分页
     * @param form 用户姓名、组织关系
     * @return
     */
    Pagination page(Pagination page, UserForm form);

    /**
     * 查询用户
     * @param form 组织ID
     * @return
     */
    List<User> orgList(UserForm form);

    /**
     * 账号唯一性验证
     *
     * @param userInfo
     * @return
     */
    boolean userInfoOnly(User userInfo);

    /**
     * 批量删除用户信息
     * @param ids
     */
    void deleteBatch(String ids);
    /**
     * 删除用户信息
     * @param
     */
    void deleteUser(Integer id);
    /**
     * 新增用户信息
     * @param user
     * @return
     */
    void addUser(User user,Integer[] orgs);
    /**
     * 修改用户信息
     * @param user
     * @return
     */
    User editUser(User user,Integer[] orgs);

    /**
     * 统计用户信息
     * @param orgId 组织ID
     * @return map 统计数据
     */
    Map<String,Integer> countUser(Integer orgId);

    /**
     * 批量添加用户信息
     * @param
     */
    String addBatch(MultipartFile file) throws IOException, InvalidFormatException;

    /**
     * 通过账号查询用户信息
     * @param account
     * @return
     */
    User findByAccount(String account);
    /**
     * 通过组织查询用户信息
     * @param ORGId
     * @return
     */
    List<User> findByORG(Integer ORGId);
    /**
     * 通过组织查询用户信息
     * @param ORGId
     * @return
     */
    List<User> findByORGforBacklog(Integer ORGId,Integer backlogId);
    /**
     * 根据工号查询用户
     * @param number
     * @return
     */
    User getByNumber(String number);
    /**
     * 排序
     */
    void rank(Integer id,Integer otherId,Integer level);
    /**
     * 导出
     * @param response
     */
    void export(HttpServletResponse response,Integer orgId);

    /**
     * 获取领导列表
     * @return
     */
    List<User> getLeaders(String unitCode);
    /**
     * 通过组织查询少数民族用户信息
     * @param ORGId
     * @return
     */
    Pagination findMin(Pagination page,Integer ORGId,ORGType type);
    /**
     * 通过组织查询女用户信息
     * @param ORGId
     * @return
     */
    Pagination findWomen(Pagination page,Integer ORGId,ORGType type);
}
