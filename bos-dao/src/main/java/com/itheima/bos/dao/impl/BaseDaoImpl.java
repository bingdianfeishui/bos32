package com.itheima.bos.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.itheima.bos.dao.IBaseDao;
import com.itheima.bos.utils.PageBean;

/**
 * 持久层通用DAO
 * @author Lee
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {
	Class<T> entityClass = null;
	
	@Resource(name="sessionFactory")
	public void setMySessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	public BaseDaoImpl() {
		//获取BaseDao的泛型参数
		entityClass = (Class<T>) ((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0];
	}

	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}
	
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	public T findById(Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	public List<T> findAll() {
		String hql = "FROM " + entityClass.getSimpleName();
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

    public void saveOrUpdate(T entity) {
        this.getHibernateTemplate().saveOrUpdate(entity);
    }
	
    public int executeUpdate(String queryName, Object... args) {
        Session session = this.getSessionFactory().getCurrentSession();
        Query query = session.getNamedQuery(queryName);
        int i = 0;
        for(Object obj :args){
            query.setParameter(String.valueOf(i++), obj);
        }
        int ret = query.executeUpdate();
        //System.out.println(ret);
        return ret;
    }
    
    public int executeUpdate(String queryName, Map<String, Object> paramsMap) {
        Session session = this.getSessionFactory().getCurrentSession();
        Query query = session.getNamedQuery(queryName);
        if (paramsMap != null) {  
            Set<String> keySet = paramsMap.keySet();  
            for (String string : keySet) {  
                Object obj = paramsMap.get(string);  
                //这里考虑传入的参数是什么类型，不同类型使用的方法不同  
                if(obj instanceof Collection<?>){  
                    query.setParameterList(string, (Collection<?>)obj);  
                }else if(obj instanceof Object[]){  
                    query.setParameterList(string, (Object[])obj);  
                }else{  
                    query.setParameter(string, obj);  
                }  
            }  
        }  
        int ret = query.executeUpdate();
        //System.out.println(ret);
        return ret;
    }
	
    public void pageQuery(PageBean<T> pageBean) {
        int currentPage = pageBean.getCurrentPage();
        int pageSize = pageBean.getPageSize();
        DetachedCriteria criteria = pageBean.getDetachedCriteria();
        
        //查询总记录数
        criteria.setProjection(Projections.rowCount());
        List<Long> countList = (List<Long>) this.getHibernateTemplate().findByCriteria(criteria);
        Long count = countList.get(0);
        
        pageBean.setTotal(count.intValue());
        
        //查询rows
        criteria.setProjection(null);
        int firstResult = (currentPage - 1) * pageSize;
        List<T> rows = (List<T>) this.getHibernateTemplate().findByCriteria(criteria, firstResult, pageSize);
        pageBean.setRows(rows);
    }

}
