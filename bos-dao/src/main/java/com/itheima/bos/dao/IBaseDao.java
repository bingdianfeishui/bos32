package com.itheima.bos.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.utils.PageBean;

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
	void saveOrUpdate(T entity);
	
	/**
	 * 通用条件分页查询
	 * @param pageBean<T> 泛型分页实体类
	 */
	void pageQuery(PageBean<T> pageBean);

    /**
     * 采用命名Query语句执行更新操作
     * @param queryName Query名称
     * @param args 可变参数
     * @return 受影响的记录数
     */
    int executeUpdate(String queryName, Object...args);
    
    /**
     * 采用命名Query语句执行更新操作
     * @param queryName Query名称
     * @param paramsMap 命名参数map
     * @return 受影响的记录数
     */
    int executeUpdate(String queryName, Map<String, Object> paramsMap);
    
    /**
     * 通用离线条件查询，不分页
     * 分页请参见{@link #pageQuery(PageBean)}
     * @param detachedCriteria 离线查询条件
     * @return
     */
    List<T> findListByDetachedCriteria(DetachedCriteria detachedCriteria);
}
