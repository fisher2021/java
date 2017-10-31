package com.upd.business.service;

import com.upd.business.constant.BacklogType;
import com.upd.business.entity.BacklogUser;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.List;

/**
 * Created by ljw on 2017/5/23.
 */
public interface BacklogUserService extends BaseService<BacklogUser,Integer> {
    /**
     * 用户和任务匹配
     * @param missionId
     * @param userId
     * @return
     */
    boolean mission(Integer missionId,Integer userId);
    /**
     * 通过任务ID和USERID查找,设置任务状态已完成
     * @param missionId
     * @return
     */
    void finishByMission(Integer missionId, Integer userId,BacklogType type);
    /**
     * 通过用户ID查找,设置任务状态已完成
     * @param userId
     * @return
     */
    void finishByBacklog(Integer userId,Integer backlogId,BacklogType type);
    /**
     * 通过用户和待办查找
     * @param userId
     * @return
     */
    BacklogUser findByBacklogAndUser(Integer userId,Integer backlogId);
    /**
     * 通过事项ID查找
     * @param backlogId
     * @return
     */
    List<BacklogUser> findByBacklog(Integer backlogId);
    /**
     * 通过用户ID分页查询
     * @param page 分页数据
     * @param userId
     * @return
     */
    Pagination page(Pagination page, Integer userId);
    /**
     * 删除指派对象
     * @param id
     * @param backlogId
     */
    void deleteUser(Integer backlogId,Integer id);
    /**
     * 使用AOP完成待办事项反馈
     * @param token
     * @param path
     * @param args
     */
    void backlogs(String token, String path, List<Object> args);
    /**
     * 批量添加指派对象
     * @param ids
     */
    void addBatch(Integer backlogId,Integer[] ids);
}
