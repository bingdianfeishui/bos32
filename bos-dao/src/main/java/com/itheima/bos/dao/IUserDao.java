package com.itheima.bos.dao;

import com.itheima.bos.domain.User;

public interface IUserDao extends IBaseDao<User>{

    /**
     * 根据用户名、密码查找用户
     * @param username 用户名
     * @param password 密码
     * @return 查找结果，未找到返回null
     */
	User findUserByUsernameAndPassword(String username, String password);

    User findUserByUsername(String username);

}
