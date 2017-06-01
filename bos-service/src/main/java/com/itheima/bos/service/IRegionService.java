package com.itheima.bos.service;

import java.io.Serializable;
import java.util.List;

import com.itheima.bos.domain.Region;
import com.itheima.bos.utils.PageBean;

public interface IRegionService {

    void saveBatch(List<Region> list);

    void pageQuery(PageBean<Region> pageBean);

    Region findById(Serializable id);

    void update(Region entity);

}
