package com.itheima.bos.dao;

import com.itheima.bos.domain.User;

public interface IUserDao extends IBaseDao<User>{

	User findUserByUsernameAndPassword(String username, String password);

}
