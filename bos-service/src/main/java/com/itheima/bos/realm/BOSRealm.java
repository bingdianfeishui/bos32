package com.itheima.bos.realm;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;

public class BOSRealm extends AuthorizingRealm{
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IFunctionDao functionDao;
    
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //根据当前登录用户查询数据库获取权限
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        //User user2 = (User) principals.getPrimaryPrincipal(); //另一种获取登录用户的方法。不能从session中取
        
        List<Function> list = null;
        //给admin用户赋予所有权限
        if(user.getUsername().equals("admin")){
            //超级管理员
            list = functionDao.findListByDetachedCriteria(DetachedCriteria.forClass(Function.class));
        }
        else{
            list = functionDao.findFunctionsByUserId(user.getId());
        }
        
        for (Function function : list) {
            info.addStringPermission(function.getCode());
        }
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {
        System.out.println("自定义Realm执行");
        //根据用户名查数据库的密码
        UsernamePasswordToken pwdToken = (UsernamePasswordToken) token;
        String username = pwdToken.getUsername();
        User user = userDao.findUserByUsername(username);
        if(user == null) return null;
        
        //框架负责比对
        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
        return authenticationInfo;
    }

}
