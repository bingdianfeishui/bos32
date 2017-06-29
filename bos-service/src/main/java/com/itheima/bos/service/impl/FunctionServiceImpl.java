package com.itheima.bos.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.service.IFunctionService;
import com.itheima.bos.utils.PageBean;

@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService {

	@Autowired
	private IFunctionDao functionDao;

	@Override
	public void save(Function function) {
		if(function.getParent()!=null && StringUtils.isBlank(function.getParent().getId()))
				function.setParent(null);
		functionDao.save(function);
	}

	@Override
	public void pageQuery(PageBean<Function> pageBean) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Function function) {
		// TODO Auto-generated method stub

	}

	@Override
	public Function findById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Function> findAll() {
		return functionDao.findAll();
	}

}
