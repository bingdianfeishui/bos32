package com.itheima.bos.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IRoleDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;
import com.itheima.bos.service.IRoleService;
import com.itheima.bos.utils.PageBean;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleDao roleDao;

    @Override
    public void save(Role role, String[] functionIds) {
        roleDao.save(role);
        for (String fid : functionIds) {
            if (StringUtils.isNotBlank(fid)) {
                Function function = new Function();
                function.setId(fid);
                role.getFunctions().add(function);
            }
        }
    }

    @Override
    public void pageQuery(PageBean<Role> pageBean) {
        roleDao.pageQuery(pageBean);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

	@Override
	public void saveOrUpdate(Role role, String[] functionIds) {
		roleDao.saveOrUpdate(role);
		for (String fid : functionIds) {
            if (StringUtils.isNotBlank(fid)) {
                Function function = new Function();
                function.setId(fid);
                role.getFunctions().add(function);
            }
        }
	}

}
