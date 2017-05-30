package com.itheima.bos.service;

import com.itheima.bos.domain.User;

public interface IUserService {

    /**
     * 用户登录
     * @param model
     * @return 登录成功返回User对象，失败返回null
     */
	User login(User model);

	/**
	 * 更新密码
	 * @param user 要更新的用户，至少应有id和password字段
	 */
    void updatePassword(User user);

}
