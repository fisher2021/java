package com.upd.auth.service;

import com.upd.auth.entity.Log;
import com.upd.business.entity.User;
import com.upd.common.basis.entity.Dict;
import com.upd.common.basis.service.BaseService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */
public interface LogService extends BaseService<Log, Integer> {

    void points(String token, String path, List<Object> args);

    void editPoint(Integer userId,Integer other,Dict dict);

}
