package com.upd.business.service.impl;

import com.upd.business.constant.ORGType;
import com.upd.business.dao.CarouselDao;
import com.upd.business.entity.Carousel;
import com.upd.business.form.CarouselForm;
import com.upd.business.service.CarouselService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 轮播图业务层实现
 * @author cao.xin
 * 2017年1月4日
 */
@Service
public class CarouselServiceImpl extends BaseServiceImpl<Carousel, Integer> implements CarouselService {
	private CarouselDao carouselDao;

	public CarouselDao getCarouselDao() {
		return carouselDao;
	}
	@Autowired
	public void setCarouselDao(CarouselDao carouselDao) {
		this.baseDao = carouselDao;
		this.carouselDao = carouselDao;
	}
	
	@Override
	public Pagination getCarouselListByPage(Pagination page, CarouselForm form) {
		page = carouselDao.findbypage(page, "from Carousel where"+form.toQueryDescription(),form.values());
		return page;
	}
	
	@Override
	public void deleteBatch(String ids) {
		String[] idArray = ids.split(",");
		for (String id : idArray) {
			carouselDao.delete(carouselDao.get(Integer.parseInt(id)));
		}
	}
	
	@Override
	public List<Carousel> getCarouselList(CarouselForm form) {
		List<Carousel> carousels = carouselDao.find("from Carousel where"+form.toQueryDescription(),form.values());
		return carousels;
	}

    @Override
    public Carousel add(Carousel carousel) {
        carousel.initTime();
        carouselDao.save(carousel);
        return carousel;
    }

    @Override
    public Carousel edit(Carousel carousel) {
        Carousel carousel1 = carouselDao.get(carousel.getId());
        if (carousel.getTitle() != null && !carousel.getTitle().equals("")){
            carousel1.setTitle(carousel.getTitle());
        }
        if (carousel.getAuthor() != null && !carousel.getAuthor().equals("")){
            carousel1.setAuthor(carousel.getAuthor());
        }
        if (carousel.getType() != null && !carousel.getType().equals("")){
            carousel1.setType(carousel.getType());
        }
        if (carousel.getImageUrl() != null && !carousel.getImageUrl().equals("")){
            carousel1.setImageUrl(carousel.getImageUrl());
        }
        if (carousel.getTargetOut() != null && !carousel.getTargetOut().equals("")){
            carousel1.setTargetOut(carousel.getTargetOut());
        }
        if (carousel.getTargetUrl() != null && !carousel.getTargetUrl().equals("")){
            carousel1.setTargetUrl(carousel.getTargetUrl());
        }
        if (carousel.getContent() != null && !carousel.getContent().equals("")){
            carousel1.setContent(carousel.getContent());
        }
        if (carousel.getRank() != null && !carousel.getRank().equals("")){
            carousel1.setRank(carousel.getRank());
        }
        carousel1.initTime();
        carouselDao.update(carousel1);
        return carousel1;
    }

    @Override
    public Boolean count(Integer type) {
        int count = 0;
        if (type == 1){
            count = carouselDao.findTotalCount("SELECT count(*) from Carousel where type.id = ?",type);
        }else if (type == 3){
            count = carouselDao.findTotalCount("SELECT count(*) from Carousel where type.id = ?",type);
        }
        return count < 5;
    }

    @Override
    public Carousel getZTXX() {
        return carouselDao.findOne("from Carousel where operator.org.level = ? order by rank", ORGType.SYS_DEFAULT);
    }

}
