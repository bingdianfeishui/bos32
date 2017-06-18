package com.itheima.bos.service.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IDecidedZoneDao;
import com.itheima.bos.domain.DecidedZone;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.IDecidedZoneService;
import com.itheima.bos.service.ISubareaService;
import com.itheima.bos.utils.PageBean;

@Service
@Transactional
public class DecidedZoneServiceImpl implements IDecidedZoneService {

    @Autowired
    private IDecidedZoneDao decidedZoneDao;

    @Autowired
    private ISubareaService subareaService;

    @Override
    public void pageQuery(PageBean<DecidedZone> pageBean) {
        decidedZoneDao.pageQuery(pageBean);
    }

    @Override
    public void saveOrUpdate(DecidedZone entity) {
        decidedZoneDao.saveOrUpdate(entity);
    }

    @Override
    public void save(DecidedZone model, Integer[] subareaId) {
        decidedZoneDao.save(model);
        for (Integer sbId : subareaId) {
            Subarea subarea = subareaService.findById(sbId);
            if (subarea != null) {
                subarea.setDecidedZone(model);
                subareaService.update(subarea);
            }
        }
    }

    @Override
    public void update(DecidedZone model, Integer[] subareaId) {
        DecidedZone zone = decidedZoneDao.findById(model.getId());
        if (zone == null)
            return;

        zone.setStaff(model.getStaff());
        zone.setName(model.getName());
        decidedZoneDao.saveOrUpdate(zone);

        // 取消分区的定区关联
        detacheSubarea(model);
        // 重新关联定区
        bindSubareas(model, subareaId);
    }

    @Override
    public void detacheSubarea(Serializable decidedZoneId) {
        detacheSubarea(decidedZoneDao.findById(decidedZoneId));
    }

    @Override
    public void detacheSubarea(DecidedZone decidedZone) {
        subareaService.detachToDecidedZone(decidedZone);
    }

    @Override
    public void bindSubareas(DecidedZone decidedZone, Serializable[] subareaIds) {
        subareaService.bindToDecidedZone(decidedZone, subareaIds);
    }

    @Override
    public List<DecidedZone> findByQ(String q) {
        DetachedCriteria criteria = DetachedCriteria
                .forClass(DecidedZone.class);
        criteria.add(Restrictions.like("name", "%" + q + "%"));
        return decidedZoneDao.findListByDetachedCriteria(criteria);
    }
}
