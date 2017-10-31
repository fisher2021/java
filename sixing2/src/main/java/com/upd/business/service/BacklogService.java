package com.upd.business.service;

import com.upd.business.constant.BacklogType;
import com.upd.business.entity.Backlog;
import com.upd.business.entity.Calendar;
import com.upd.business.form.BacklogForm;
import com.upd.business.form.CalendarForm;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

import java.util.List;

/**
 * 待办事项业务层
 * Created by ljw on 2017/5/4.
 */
public interface BacklogService extends BaseService<Backlog,Integer> {
    /**
     * 查询待办事项
     * @param month 月份
     * @param userId 用户ID
     * @return
     */
    List<Backlog> search(String month, Integer userId);
    /**
     * 查询待办事项
     * @param missionId 任务ID
     * @return
     */
    List<Backlog> searchMission(Integer missionId);
    /**
     * 分页查询待办事项
     * @param page 分页数据
     * @return
     */
    Pagination page(Pagination page, BacklogForm form);
    /**
     * 新增待办事项
     * @param backlog
     * @return
     */
    Backlog add(Backlog backlog);
    /**
     * 修改待办事项
     * @param backlog
     * @return
     */
    Backlog edit(Backlog backlog);
    /**
     *判断任务ID是否存在
     * @param type
     * @param missionId
     * @return
     */
    boolean existMissionId(BacklogType type, int missionId);



}
