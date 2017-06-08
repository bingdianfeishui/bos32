package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.impl.StaffService;
import com.itheima.bos.utils.PageBean;

public interface IStaffService {

    void save(Staff model);

    void pageQuery(PageBean<Staff> pageBean);


    /**
     * 逻辑删除Staff
     * @param ids   id数组
     * @param op    {@link StaffService.Operation}操作
     * @throws Exception 
     */
    void batchDeleteOrRestore(Integer[] ids, StaffService.Operation op) throws Exception;

    Staff findById(Integer id);

    void update(Staff staff);

    List<Staff> findByQ(String q);
}
