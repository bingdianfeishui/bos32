package com.itheima.bos.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Role entity. @author MyEclipse Persistence Tools
 */

public class Role implements java.io.Serializable {

    // Fields

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String code;
    private String description;
    private Set<Role> users = new HashSet<>(0);
    private Set<Role> functions = new HashSet<>(0);
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
    public Set<Role> getUsers() {
        return users;
    }
    public void setUsers(Set<Role> users) {
        this.users = users;
    }
    public Set<Role> getFunctions() {
        return functions;
    }
    public void setFunctions(Set<Role> functions) {
        this.functions = functions;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}