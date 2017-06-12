package com.itheima.bos.service;

import java.io.Serializable;

import com.itheima.bos.domain.DecidedZone;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.utils.PageBean;

public interface IDecidedZoneService {

    void pageQuery(PageBean<DecidedZone> pageBean);

    void saveOrUpdate(DecidedZone entity);

	void save(DecidedZone model, Integer[] subareaId);

    void update(DecidedZone model, Integer[] subareaId);
    
    void detacheSubarea(Serializable decidedZoneId);
    
    void detacheSubarea(DecidedZone decidedZone);
    
    void bindSubareas(DecidedZone decidedZone, Serializable[] subareaIds);
}
