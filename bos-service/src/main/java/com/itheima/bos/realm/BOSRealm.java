package com.itheima.bos.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.domain.User;

public class BOSRealm extends AuthorizingRealm{
    @Autowired
    private IUserDao userDao;
    
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        return null;
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
