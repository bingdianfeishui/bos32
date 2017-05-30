package com.itheima.bos.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class JackSonUtils {
    @JsonFilter("jackSonUtilsMixInFilter")
    interface JackSonUtilsMixIn{}
    
    private ObjectMapper objectMapper;
    
    private Class<?> target;
    private JackSonUtils(Class<?> target){
        this.objectMapper = new ObjectMapper();
        this.target = target;
    }
    public static JackSonUtils init(Class<?> targetClass){
        return new JackSonUtils(targetClass);
    }
    
    public JackSonUtils setFilter(FilterProvider filterProvider){
        objectMapper.setFilterProvider(filterProvider);
        return this;
    }
    
    /**
     * 为当前类添加混入注解类，等价于addMixIn(this.target, mixinSource)
     * @param mixinSource 混入注解类
     * @return
     */
    public JackSonUtils addMixIn(Class<?> mixinSource){
        objectMapper.addMixIn(this.target, mixinSource);
        return this;
    }
    
    /**
     * 为pojo目标类添加混入注解
     * @param pojo  目标pojo
     * @param mixinSource 混入注解类
     * @return
     */
    public JackSonUtils addMixIn(Class<?> pojo, Class<?> mixinSource){
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
    public JackSonUtils setExceptProperties(String... excepts){
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
    public JackSonUtils setIncludeProperties(String... includes){
        FilterProvider filter = new SimpleFilterProvider().addFilter(
                "jackSonUtilsMixInFilter",
                SimpleBeanPropertyFilter.filterOutAllExcept(includes));
        objectMapper.setFilterProvider(filter).addMixIn(this.target, JackSonUtilsMixIn.class);
        return this;
    }

    public String SerializeObj(Object obj) throws JsonProcessingException{
        checkTargetClass(obj);
        return objectMapper.writeValueAsString(obj);
    }
    public void SerializeObj(OutputStream out,Object obj) throws IOException{
        checkTargetClass(obj);
        objectMapper.writeValue(out, obj);
    }
    public void SerializeObj(Writer out,Object obj) throws IOException{
        checkTargetClass(obj);
        objectMapper.writeValue(out, obj);
    }
    
    private void checkTargetClass(Object obj){
        if(obj.getClass() != this.target){
            throw new IllegalArgumentException("JackSonUtils is inited as "+target.getName() + ", but argument type is "+obj.getClass().getName());
        }
    }
}
