package com.itheima.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.IUserService;
import com.itheima.bos.utils.MD5Utils;


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

}
