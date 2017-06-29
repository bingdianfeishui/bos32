package com.itheima.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;

@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements
        IFunctionDao {

    @Override
    public List<Function> findAll() {
        String hql = "FROM Function f WHERE f.parent IS NULL";
        @SuppressWarnings("unchecked")
        List<Function> list = (List<Function>) this.getHibernateTemplate()
                .find(hql);
        return list;
    }

    @Override
    public List<Function> findFunctionsByUserId(String id) {
        // SELECT * FROM auth_function f LEFT JOIN role_function r_f ON f.id=
        // r_f.`function_id` WHERE r_f.`role_id` IN
        // (SELECT u_r.role_id FROM user_role u_r LEFT JOIN t_user u ON u.id=
        // u_r.`user_id` WHERE u.id='402880e55cf462cb015cf46385bf0000' )
        String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles r LEFT OUTER JOIN r.users u WHERE u.id=?";
        @SuppressWarnings("unchecked")
        List<Function> list = (List<Function>) this.getHibernateTemplate()
                .find(hql, id);

        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Function> findAllMenuFunction() {
        String hql = "FROM Function f WHERE f.generatemenu='1' ORDER BY f.zindex DESC";

        return (List<Function>) this.getHibernateTemplate().find(hql);
    }

    @Override
    public List<Function> findMenuFuncitonByUser(User user) {
        String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles r LEFT OUTER JOIN r.users u WHERE  f.generatemenu='1' AND u.id=? ORDER BY f.zindex DESC";
        @SuppressWarnings("unchecked")
        List<Function> list = (List<Function>) this.getHibernateTemplate()
                .find(hql, user.getId());

        return list;
    }
}
