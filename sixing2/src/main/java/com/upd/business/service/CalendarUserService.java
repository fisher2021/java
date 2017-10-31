package com.upd.business.service;

import com.upd.business.entity.BacklogUser;
import com.upd.business.entity.Calendar;
import com.upd.business.entity.CalendarUser;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

import java.util.List;

/**
 * Created by ljw on 2017/5/23.
 */
public interface CalendarUserService extends BaseService<CalendarUser,Integer> {

    /**
     * 通过用户ID查找
     * @param userId
     * @return
     */
    List<CalendarUser> findByUser(Integer userId);
    /**
     * 通过事项ID查找
     * @param backlogId
     * @return
     */
    List<CalendarUser> findByCalendar(Integer backlogId);
    /**
     * 删除指派对象
     *
     * @param id
     * @param calendarId
     */
    void deleteUser(Integer calendarId,Integer id);

    /**
     * 通过用户和日历查询
     * @param calendarId
     * @param userId
     * @return
     */
    CalendarUser findByUserAndCalendar(Integer calendarId,Integer userId);
}
