package com.itheima.bos.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IStaffDAO;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.IStaffService;
import com.itheima.bos.utils.PageBean;

@Service
@Transactional
public class StaffService implements IStaffService {
    public static enum Operation {
        RESTORE, DELETE
    };

    @Autowired
    private IStaffDAO staffDao;

    public void save(Staff model) {
        staffDao.save(model);
    }

    public void pageQuery(PageBean<Staff> pageBean) {
        staffDao.pageQuery(pageBean);
    }

    public void batchDelete(String[] ids) throws Exception {
        if (ids.length > 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("flag", 1);
            map.put("ids", ids);
            int ret = staffDao.executeUpdate("staff.delete", map);
            if (ret != ids.length)
                throw new Exception("受影响的行数不等于预期");
        }
    }

    public void batchDeleteOrRestore(Integer[] ids, StaffService.Operation op)
            throws Exception {
        if (ids.length > 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("op", op == Operation.DELETE ? "1" : "0");
            map.put("ids", ids);
            int ret = staffDao.executeUpdate("staff.restoreOrDelete", map);
            if (ret != ids.length)
                throw new Exception("受影响的行数不等于预期");
        }

    }

    public Staff findById(Integer id) {
        return staffDao.findById(id);
    }

    public void update(Staff staff) {
        staffDao.update(staff);
    }

}
