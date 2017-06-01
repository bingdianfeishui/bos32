package com.itheima.bos.action.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.itheima.bos.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

    private static final long serialVersionUID = -7766696297964008314L;

    public static final String HOME = "home";
    public static final String RELOGIN = "relogin";
    public static final String LIST = "list";

    //搜索模式标记
    protected boolean searchMode = false;
    public void setSearchMode(boolean searchMode) {
        this.searchMode = searchMode;
    }
    
    //分页公共抽取
    public void setPage(Integer page) {
        pageBean.setCurrentPage(page);
    }

    public void setRows(Integer rows) {
        pageBean.setPageSize(rows);
    }

    protected PageBean<T> pageBean;
    //分页公共抽取
    
    
    protected T model;

    @SuppressWarnings("unchecked")
    public BaseAction() {
        ParameterizedType genericSuperclass = (ParameterizedType) this
                .getClass().getGenericSuperclass();
        // 获得BaseAction上声明的泛型数组
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        Class<T> entityClass = (Class<T>) actualTypeArguments[0];
        // 通过反射创建对象
        try {
            model = entityClass.newInstance();
            pageBean = new PageBean<T>(entityClass);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public T getModel() {
        return model;
    }

    public HttpSession getSession() {
        return ServletActionContext.getRequest().getSession();
    }

}
