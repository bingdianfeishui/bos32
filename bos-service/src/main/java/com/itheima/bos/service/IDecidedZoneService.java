package com.itheima.bos.service;

import com.itheima.bos.domain.DecidedZone;
import com.itheima.bos.utils.PageBean;

public interface IDecidedZoneService {

    void pageQuery(PageBean<DecidedZone> pageBean);

    void saveOrUpdate(DecidedZone entity);
}
