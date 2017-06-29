package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Role;
import com.itheima.bos.utils.PageBean;

public interface IRoleService {
    void save(Role role, String[] funcitonIds);
    void pageQuery(PageBean<Role> pageBean);
    List<Role> findAll();
}
