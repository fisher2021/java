package com.upd.business.service;

import com.upd.business.entity.Carousel;
import com.upd.business.form.CarouselForm;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

import java.util.List;

/**
 * 轮播图业务层
 * @author cao.xin
 * 2017年1月4日
 */
public interface CarouselService extends BaseService<Carousel, Integer> {

	/**
	 * 根据条件和类型分页查询轮播图
	 * @param page
	 * @param form 词典ID
	 * @return
	 */
	Pagination getCarouselListByPage(Pagination page, CarouselForm form);
	/**
	 * 批量删除轮播图
	 * @param ids
	 */
	void deleteBatch(String ids);

	/**
	 * 根据轮播图类型获取前端显示的轮播图
	 * @param form 词典类型
	 * @return
	 */
	List<Carousel> getCarouselList(CarouselForm form);
    /**
     * 新增轮播图
     * @param carousel
     * @return
     */
    Carousel add(Carousel carousel);
    /**
     * 修改轮播图
     * @param carousel
     * @return
     */
    Carousel edit(Carousel carousel);

    /**
     * 判断轮播图的数量
     * @return
     */
    Boolean count(Integer type);

    /**
     * 获取admin配置的专题学习
     * @return
     */
    Carousel getZTXX();

}
