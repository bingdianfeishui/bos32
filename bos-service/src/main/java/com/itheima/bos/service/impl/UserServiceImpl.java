package com.itheima.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.domain.Role;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.IUserService;
import com.itheima.bos.utils.MD5Utils;
import com.itheima.bos.utils.PageBean;


@Service
@Transactional
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDao;
	
	public User login(User model) {
		return userDao.findUserByUsernameAndPassword(model.getUsername(), MD5Utils.md5(model.getPassword()));
	}

    public void updatePassword(User user) {
        userDao.executeUpdate("user.updatePassword", user.getPassword(), user.getId());
    }

    @Override
    public void pageQuery(PageBean<User> pageBean) {
        userDao.pageQuery(pageBean);
    }

    @Override
    public void save(User user) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(User user) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(User user) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void save(User user, String[] roleIds) {
        user.setPassword(MD5Utils.md5(user.getPassword()));
        userDao.save(user);
        if(roleIds !=null && roleIds.length > 0){
            for (String id : roleIds) {
                Role role = new Role();
                role.setId(id);
                user.getRoles().add(role);
            }
        }
    }

    @Override
    public void saveOrUpdate(User user, String[] roleIds) {
        user.setPassword(MD5Utils.md5(user.getPassword()));
        userDao.saveOrUpdate(user);
        if(roleIds !=null && roleIds.length > 0){
            for (String id : roleIds) {
                Role role = new Role();
                role.setId(id);
                user.getRoles().add(role);
            }
        }
    }

    @Override
    public User findById(String id) {
        return userDao.findById(id);
    }

}
