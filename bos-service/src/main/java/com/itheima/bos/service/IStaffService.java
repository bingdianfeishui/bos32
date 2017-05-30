package com.itheima.bos.service;

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
    void batchDeleteOrRestore(String[] ids, StaffService.Operation op) throws Exception;

    Staff findById(String id);

    void update(Staff staff);

}
