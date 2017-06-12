package com.itheima.bos.service.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IRegionDao;
import com.itheima.bos.domain.Region;
import com.itheima.bos.service.IRegionService;
import com.itheima.bos.utils.PageBean;

@Service
@Transactional
public class RegionServiceImpl implements IRegionService {

    @Autowired
    private IRegionDao regionDao;
    
    public void saveBatch(List<Region> list) {
        for (Region region : list) {
            regionDao.saveOrUpdate(region);
        }
    }

    public void pageQuery(PageBean<Region> pageBean) {
        regionDao.pageQuery(pageBean);
    }

    public Region findById(Serializable id) {
        return regionDao.findById(id);
    }

    public void update(Region entity) {
        regionDao.update(entity);
    }

    @Override
    public List<Region> findListByCriteria(DetachedCriteria detachedCriteria) {
        return regionDao.findListByDetachedCriteria(detachedCriteria);
    }

}
