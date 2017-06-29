package com.itheima.bos.service;

import com.itheima.bos.domain.User;
import com.itheima.bos.utils.PageBean;

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

    void pageQuery(PageBean<User> pageBean);

    void save(User user, String[] roleIds);
    
    void update(User user);
    
    void delete(User user);

    void save(User user);
}
