package com.itheima.bos.service;

import com.itheima.bos.domain.WorkOrderManage;
import com.itheima.bos.utils.PageBean;

public interface IWorkOrderManageService {

    void pageQuery(PageBean<WorkOrderManage> pageBean);

    void saveOrUpdate(WorkOrderManage model);

}
