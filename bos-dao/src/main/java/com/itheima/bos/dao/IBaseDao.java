package com.itheima.bos.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 持久层通用接口
 * 
 * @author Lee
 *
 * @param <T>
 */
public interface IBaseDao<T> {
	
	void save(T entity);
	void delete(T entity);
	void update(T entity);
	T findById(Serializable id);
	List<T> findAll();
}
