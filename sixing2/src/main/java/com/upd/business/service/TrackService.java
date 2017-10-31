package com.upd.business.service;

import com.upd.auth.entity.Operator;
import com.upd.business.constant.TrackType;
import com.upd.business.entity.Backlog;
import com.upd.business.entity.Track;
import com.upd.business.form.BacklogForm;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

import java.util.List;

/**
 * 党迹业务层
 * Created by ljw on 2017/5/4.
 */
public interface TrackService extends BaseService<Track,Integer> {

    /**
     * 分页查询党迹
     * @param page 分页数据
     * @return
     */
    public Pagination page(Pagination page, Integer userId);
    /**
     * 新增党迹
     * @param userId
     * @param subject
     * @param type
     * @return
     */
    public void add(String subject, TrackType type, Integer userId, Operator operator);
    /**
     * 修改党迹
     * @param track
     * @return
     */
    public void edit(Track track);

    /**
     * 使用AOP添加党迹
     * @param token
     * @param path
     * @param args
     */
    public void tracks(String token, String path, List<Object> args);

}
