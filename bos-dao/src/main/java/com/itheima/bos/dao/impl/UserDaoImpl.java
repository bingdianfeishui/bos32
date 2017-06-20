package com.itheima.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.domain.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {

	@SuppressWarnings("unchecked")
	public User findUserByUsernameAndPassword(String username, String password) {
		String hql = "FROM User u WHERE u.username=? AND u.password=?";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username, password);
		if (list.size() > 0)
			return list.get(0);
		return null;
	}

    @Override
    public User findUserByUsername(String username) {
        String hql = "FROM User u WHERE u.username=?";
        List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username);
        if (list.size() > 0)
            return list.get(0);
        return null;
    }

}
