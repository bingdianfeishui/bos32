package com.itheima.bos.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class JacksonUtils {
    @JsonFilter("jackSonUtilsMixInFilter")
    interface JackSonUtilsMixIn{}
    
    private ObjectMapper objectMapper;
    
    private Class<?> target;
    private JacksonUtils(Class<?> target){
        this.objectMapper = new ObjectMapper();
        this.target = target;
    }
    public static JacksonUtils init(Class<?> targetClass){
        return new JacksonUtils(targetClass);
    }
    
    public JacksonUtils setFilter(FilterProvider filterProvider){
        objectMapper.setFilterProvider(filterProvider);
        return this;
    }
    
    /**
     * 为当前类添加混入注解类，等价于addMixIn(this.target, mixinSource)
     * @param mixinSource 混入注解类
     * @return
     */
    public JacksonUtils addMixIn(Class<?> mixinSource){
        objectMapper.addMixIn(this.target, mixinSource);
        return this;
    }
    
    /**
     * 为pojo目标类添加混入注解
     * @param pojo  目标pojo
     * @param mixinSource 混入注解类
     * @return
     */
    public JacksonUtils addMixIn(Class<?> pojo, Class<?> mixinSource){
        objectMapper.addMixIn(pojo, mixinSource);
        return this;
    }
    
    /**
     * 设置序列化时需要排除的属性数组
     * 注意：{@link #setExceptProperties(String...)}与{@link #setIncludeProperties(String...)}不能同时使用。
     * 若要同时添加排除和包含的规则，请另行使用{@link #addMixIn(Class)}方法
     * @param excepts
     * @return
     */
    public JacksonUtils setExceptProperties(String... excepts){
        FilterProvider filter = new SimpleFilterProvider().addFilter(
                "jackSonUtilsMixInFilter",
                SimpleBeanPropertyFilter.serializeAllExcept(excepts));
        objectMapper.setFilterProvider(filter).addMixIn(this.target, JackSonUtilsMixIn.class);
        return this;
    }
    
    /**
     * 设置序列化时需要包含的属性数组
     * 注意：{@link #setExceptProperties(String...)}与{@link #setIncludeProperties(String...)}不能同时使用。
     * 若要同时添加排除和包含的规则，请另行使用{@link #addMixIn(Class)}方法
     * @param includes
     * @return
     */
    public JacksonUtils setIncludeProperties(String... includes){
        FilterProvider filter = new SimpleFilterProvider().addFilter(
                "jackSonUtilsMixInFilter",
                SimpleBeanPropertyFilter.filterOutAllExcept(includes));
        objectMapper.setFilterProvider(filter).addMixIn(this.target, JackSonUtilsMixIn.class);
        return this;
    }

    public String serializeObj(Object obj) throws JsonProcessingException{
        return objectMapper.writeValueAsString(obj);
    }
    public void serializeObj(OutputStream out,Object obj) throws IOException{
        objectMapper.writeValue(out, obj);
    }
    public void serializeObj(Writer out,Object obj) throws IOException{
        objectMapper.writeValue(out, obj);
    }
    public void serializeObj(HttpServletResponse response, Object obj) throws IOException {
        response.setContentType("text/json;charset='utf-8'");
        serializeObj(response.getWriter(), obj);
    }
    
//    private void checkTargetClass(Object obj){
//        Class<?> argClass = null;
//        if(obj instanceof Collection)
//        
//        if(argClass != this.target){
//            throw new IllegalArgumentException("JackSonUtils is inited as "+target.getName() + ", but argument type is "+obj.getClass().getName());
//        }
//    }
}
