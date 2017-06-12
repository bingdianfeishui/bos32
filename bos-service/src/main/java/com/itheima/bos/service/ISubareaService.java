package com.itheima.bos.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.domain.DecidedZone;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.utils.PageBean;

public interface ISubareaService {

    void add(Subarea entity);

    Subarea findById(Serializable id);

    void update(Subarea entity);

    void pageQuery(PageBean<Subarea> pageBean);

    List<Subarea> findListByCriteria(DetachedCriteria detachedCriteria);

    List<Subarea> findAll();

    List<Subarea> findListByDecidedZoneId(Integer decidedZoneId);

    List<Subarea> listNoDecidedZone();

    void detachToDecidedZone(DecidedZone decidedZone);

    void bindToDecidedZone(DecidedZone decidedZone, Serializable[] subareaIds);
    
}
