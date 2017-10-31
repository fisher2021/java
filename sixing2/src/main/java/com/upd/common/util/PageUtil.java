package com.upd.common.util;

import com.upd.common.util.page.Pagination;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangshao on 2016/1/8.
 * 分页工具包
 */
public class PageUtil {
    public Pagination limitToChangePage(Pagination pagen, List list) throws Exception {
        List newlist = new ArrayList();
        for (int i = pagen.getFirstResult(); i < pagen.getFirstResult()
                + pagen.getPageSize(); i++) {
            if (i < list.size() - 1) {
                newlist.add(list.get(i));
            } else if (i == list.size() - 1 && list.size() > 0) {
                newlist.add(list.get(i));
                break;
            } else {
                break;
            }
        }
        pagen.setList(newlist);
        return pagen;
    }
}
