package com.itheima.bos.utils;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("pageBeanFilter")
public class PageBean<T>{
    //当前页码
    private int currentPage = 1;
    //每页条数
    private int pageSize = 20;   
    //总记录数
    private int total;  //总记录数
    //查询结果集
    private List<T> rows;  //当前页需要显示的数据集合
    //hibernate离线查询对象
    private DetachedCriteria detachedCriteria;
    //实体类Class
    private Class<T> entityClass;
    
    public PageBean(Class<T> clazz) {
        entityClass = clazz;
        detachedCriteria = DetachedCriteria.forClass(entityClass);
    }
    
    public PageBean(Class<T> clazz, int currentPage, int pageSize) {
        this(clazz);
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        
    }

    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public List<T> getRows() {
        return rows;
    }
    public void setRows(List<T> rows) {
        this.rows = rows;
    }
    public DetachedCriteria getDetachedCriteria() {
        return detachedCriteria;
    }
    public void setDetachedCriteria(DetachedCriteria detachedCriteria) {
        this.detachedCriteria = detachedCriteria;
    }
    
}
