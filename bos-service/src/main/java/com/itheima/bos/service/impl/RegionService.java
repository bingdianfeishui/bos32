package com.itheima.bos.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IRegionDao;
import com.itheima.bos.domain.Region;
import com.itheima.bos.service.IRegionService;
import com.itheima.bos.utils.PageBean;

@Service
@Transactional
public class RegionService implements IRegionService {

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

}
