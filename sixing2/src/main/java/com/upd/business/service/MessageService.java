package com.upd.business.service;

import com.upd.business.entity.Message;
import com.upd.business.form.MessageForm;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

/**
 * 系统消息业务层
 * Created by ljw on 2017/5/4.
 */
public interface MessageService extends BaseService<Message,Integer> {
    /**
     * 分页查询系统消息
     * @param page 分页数据
     * @param form
     * @return
     */
    Pagination page(Pagination page, MessageForm form);

    /**
     * 批量删除
     * @param ids
     */
    void deleteBatch(String ids);
    /**
     * 新增系统消息
     * @param message
     * @return
     */
    void add(Message message);
    /**
     * 发送系统消息
     * @param orgs
     * @return
     */
    void push(Message message,Integer[] orgs);
    /**
     * 批量添加指派组织
     *
     * @param ids
     */
    void addBatch(Integer messageId,Integer[] ids);
    /**
     * 删除指派组织
     *
     * @param id
     * @param messageId
     */
    void deleteORG(Integer messageId,Integer id);
}
