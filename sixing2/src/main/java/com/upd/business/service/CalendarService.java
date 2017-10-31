package com.upd.business.service;

import com.upd.business.entity.Article;
import com.upd.business.entity.Calendar;
import com.upd.business.form.CalendarForm;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

import java.util.Date;
import java.util.List;

/**
 * 党务日历业务层
 * Created by ljw on 2017/5/4.
 */
public interface CalendarService extends BaseService<Calendar,Integer> {
    /**
     * 查询党务日历
     * @param month 月份
     * @param id
     * @return
     */
    public Calendar searchByMonth(String month,Integer id);
    /**
     * 分页查询党务日历
     * @param page 分页数据
     * @return
     */
    public Pagination page(Pagination page, CalendarForm form);
    /**
     * 新增党务日历
     * @param calendar
     * @return
     */
    public void add(Calendar calendar);
    /**
     * 修改党务日历
     * @param calendar
     * @return
     */
    public void edit(Calendar calendar);

    /**
     * 批量删除党务日历
     * @param ids
     */
    public void deleteBatch(String ids);
    /**
     * 批量添加指派对象
     *
     * @param ids
     */
    void addBatch(Integer calendarId,Integer[] ids);
}
