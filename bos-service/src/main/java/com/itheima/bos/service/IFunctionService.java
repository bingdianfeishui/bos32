package com.itheima.bos.service;

import java.io.Serializable;
import java.util.List;

import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;
import com.itheima.bos.utils.PageBean;

public interface IFunctionService {
	void save(Function function);
	
	void pageQuery(PageBean<Function> pageBean);
	
	void update(Function function);
			
	Function findById(Serializable id);

	List<Function> findAll();

    List<Function> findAllMenuFunction();

    List<Function> findMenuFuncitonByUser(User user);
}
