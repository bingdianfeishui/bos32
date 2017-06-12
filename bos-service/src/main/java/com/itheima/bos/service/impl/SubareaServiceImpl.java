package com.itheima.bos.service.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.ISubareaDao;
import com.itheima.bos.domain.DecidedZone;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.ISubareaService;
import com.itheima.bos.utils.PageBean;

@Service
@Transactional
public class SubareaServiceImpl implements ISubareaService {
    @Autowired
    private ISubareaDao subareaDao;
    
    @Override
    public void add(Subarea entity) {
        subareaDao.save(entity);
    }

    @Override
    public Subarea findById(Serializable id) {
        return subareaDao.findById(id);
    }

    @Override
    public void update(Subarea entity) {
        subareaDao.update(entity);
    }

    @Override
    public void pageQuery(PageBean<Subarea> pageBean) {
        subareaDao.pageQuery(pageBean);
    }

    @Override
    public List<Subarea> findListByCriteria(DetachedCriteria detachedCriteria) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Subarea> findAll() {
        return subareaDao.findAll();
    }

    @Override
    public List<Subarea> findListByDecidedZoneId(Integer decidedZoneId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
        detachedCriteria.add(Restrictions.eq("decidedZone.id", decidedZoneId));
        
        return subareaDao.findListByDetachedCriteria(detachedCriteria);
    }

    @Override
    public List<Subarea> listNoDecidedZone() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
        detachedCriteria.add(Restrictions.isNull("decidedZone.id"));
        
        return subareaDao.findListByDetachedCriteria(detachedCriteria);
    }

    @Override
    public void detachToDecidedZone(DecidedZone decidedZone) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
        detachedCriteria.add(Restrictions.eq("decidedZone", decidedZone));
        List<Subarea> list = subareaDao.findListByDetachedCriteria(detachedCriteria );
        for (Subarea subarea : list) {
            subarea.setDecidedZone(null);
            subareaDao.saveOrUpdate(subarea);
        }
    }

    @Override
    public void bindToDecidedZone(DecidedZone decidedZone,
            Serializable[] subareaIds) {
        for (Serializable id : subareaIds) {
            Subarea sub = subareaDao.findById(id);
            if(sub!=null){
                sub.setDecidedZone(decidedZone);
                subareaDao.saveOrUpdate(sub);
            }
        }
    }

}
