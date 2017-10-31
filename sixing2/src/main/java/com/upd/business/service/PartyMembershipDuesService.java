package com.upd.business.service;

import com.upd.business.entity.PartyMembershipDues;
import com.upd.business.entity.User;
import com.upd.business.form.PartyMembershipDuesForm;
import com.upd.business.form.UserForm;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/5/31.
 */
public interface PartyMembershipDuesService extends BaseService<PartyMembershipDues,Integer> {
    /**
     * 批量添加党费信息
     * @param
     */
    String addBatch(MultipartFile file,Integer type,String year) throws IOException, InvalidFormatException;

    /**
     * 判断党费是否存在
     * @param account
     * @param year
     * @return
     */
    PartyMembershipDues existUser(String account,String year);

    /**
     * 修改党费
     * @param partyMembershipDues
     */
    void edit(PartyMembershipDues partyMembershipDues);

    /**
     * 批量删除党费
     * @param ids
     */
    void deleteBatch(String ids);
    /**
     * 导出
     * @param response
     */
    void export(HttpServletResponse response, Integer orgId,String month,Boolean status);
}
