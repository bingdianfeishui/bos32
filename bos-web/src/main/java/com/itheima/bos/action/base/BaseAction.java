package com.itheima.bos.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;

import com.itheima.bos.utils.BOSUtils;
import com.itheima.bos.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public abstract class BaseAction<T> extends ActionSupport implements
        ModelDriven<T> {

    private static final long serialVersionUID = -7766696297964008314L;

    public static final String HOME = "home";
    public static final String RELOGIN = "relogin";
    public static final String LIST = "list";

    // 搜索模式标记
    protected boolean searchMode = false;

    public void setSearchMode(boolean searchMode) {
        this.searchMode = searchMode;
    }

    // 分页公共抽取
    public void setPage(Integer page) {
        pageBean.setCurrentPage(page);
    }

    public void setRows(Integer rows) {
        pageBean.setPageSize(rows);
    }

    protected PageBean<T> pageBean;
    // 分页公共抽取

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

    public abstract String add() throws IOException;

    public abstract String delete() throws IOException;

    public abstract String edit() throws IOException;

    public abstract String pageQuery() throws IOException;

    private static final String SEARCH_KEYWORDS_REGEX = "^[A-Za-z0-9\u4e00-\u9fa5*?]*$";
    /**
     * 模糊查询条件字符串，允许包含* ？,在内部自动替换为% _
     */
    protected String q;

    public void setQ(String q) {
        if (q != null) {
            q = q.trim();
            //正则判断然后替换
            if (Pattern.matches(SEARCH_KEYWORDS_REGEX, q))
                this.q = q.replace('*', '%').replace("?", "_");
        }
    }

    public abstract String findByQ() throws IOException;

    
    protected void returnBlankJasonObj(){
        try {
            BOSUtils.getResponse().getWriter().write("{}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 将指定Java对象转为json，并响应到客户端页面
     * @param o
     * @param exclueds
     */
    public void java2Json(Object o ,String[] exclueds){
        JsonConfig jsonConfig = new JsonConfig();
        //指定哪些属性不需要转json
        jsonConfig.setExcludes(exclueds);
        String json = JSONObject.fromObject(o,jsonConfig).toString();
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        try {
            ServletActionContext.getResponse().getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 将指定Java对象转为json，并响应到客户端页面
     * @param o
     * @param exclueds
     */
    public void java2Json(@SuppressWarnings("rawtypes") List o ,String[] exclueds){
        JsonConfig jsonConfig = new JsonConfig();
        //指定哪些属性不需要转json
        jsonConfig.setExcludes(exclueds);
        String json = JSONArray.fromObject(o,jsonConfig).toString();
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        try {
            ServletActionContext.getResponse().getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
