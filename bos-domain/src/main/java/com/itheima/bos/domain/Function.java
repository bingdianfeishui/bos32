package com.itheima.bos.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Function entity. @author MyEclipse Persistence Tools
 */

public class Function implements java.io.Serializable {

    // Fields

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String id;
    private Function parent;
    private String name;
    private String code;
    private String description;
    private String page;
    private String generatemenu;
    private Integer zindex;
    private Set<Function> children = new HashSet<>(0);
    private Set<Role> roles = new HashSet<>(0);
    
    public String getpId(){
        if(parent == null){
            return "0";
        }
        return parent.getId();
    }
    
    public String getText(){
        return name;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    public Function getParent() {
		return parent;
	}
	public void setParent(Function parent) {
		this.parent = parent;
	}
	public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPage() {
        return page;
    }
    public void setPage(String page) {
        this.page = page;
    }
    public String getGeneratemenu() {
        return generatemenu;
    }
    public void setGeneratemenu(String generatemenu) {
        this.generatemenu = generatemenu;
    }
    public Integer getZindex() {
        return zindex;
    }
    public void setZindex(Integer zindex) {
        this.zindex = zindex;
    }
    public Set<Function> getChildren() {
        return children;
    }
    public void setChildren(Set<Function> children) {
        this.children = children;
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}