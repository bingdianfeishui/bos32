package com.itheima.bos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IDecidedZoneDao;
import com.itheima.bos.domain.DecidedZone;
import com.itheima.bos.utils.PageBean;


@Service
@Transactional
public class DecidedZoneService implements IDecidedZoneService {

    @Autowired
    private IDecidedZoneDao decidedZoneDao;

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
		// TODO Auto-generated method stub
		
	}
}
