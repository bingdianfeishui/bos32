package com.itheima.bos.service.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.ISubareaDao;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.ISubareaService;
import com.itheima.bos.utils.PageBean;

@Service
@Transactional
public class SubareaService implements ISubareaService {
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

}
