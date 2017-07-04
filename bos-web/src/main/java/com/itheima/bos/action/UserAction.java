package com.itheima.bos.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.action.base.BaseAction;
import com.itheima.bos.domain.Role;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.IUserService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.bos.utils.MD5Utils;

@Controller
@Scope("prototype")
@Namespace("/user")
@ParentPackage("basicStruts")
@Results({@Result(name="list", location="/WEB-INF/pages/admin/userlist.jsp")})
public class UserAction extends BaseAction<User> {

    private static final long serialVersionUID = 8815472318245773723L;

    // region actions
    /**
     * 登录Action
     * @return
     */
    @Action("login")
    public String login() {
        String validateCode = (String) BOSUtils.getSession()
                .getAttribute("key");

        if (StringUtils.isNotBlank(validateCode)
                && validateCode.equals(checkcode)) {
            Subject subject = SecurityUtils.getSubject();
            AuthenticationToken token = new UsernamePasswordToken(model.getUsername(), MD5Utils.md5(model.getPassword()));
            try {
                subject.login(token);
                User user = (User) subject.getPrincipal();
                BOSUtils.getSession().setAttribute("loginUser", user);
                return HOME;
            } catch(UnknownAccountException e){
                e.printStackTrace();
                addActionError("用户名不存在！");
            } catch(IncorrectCredentialsException e){
                e.printStackTrace();
                addActionError("密码错误！");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return LOGIN;
        }

        addActionError("验证码输入错误！");
        return LOGIN;
    }
    
    public String login_bak() {
        String validateCode = (String) BOSUtils.getSession()
                .getAttribute("key");

        if (StringUtils.isNotBlank(validateCode)
                && validateCode.equals(checkcode)) {
            User user = userService.login(model);
            if (user != null) {
                BOSUtils.getSession().setAttribute("loginUser", user);
                return HOME;
            } else {
                addActionError("用户名或密码错误！");
                return LOGIN;
            }
        }
        
        addActionError("验证码输入错误！");
        return LOGIN;
    }

    /**
     * 注销Action
     * @return
     */
    @Action("logout")
    public String logout() {
        BOSUtils.getSession().invalidate();
        return RELOGIN;
    }

    /**
     * 更改密码Action
     * @return
     * @throws IOException
     */
    @Action("updatePwd")
    public String updatePwd() throws IOException {
        String ret = "0";
        if (StringUtils.isNotBlank(model.getPassword())
                && Pattern.matches(PASSWORD_REGEX, model.getPassword())) {
            User user = BOSUtils.getLoginUser();
            user.setPassword(MD5Utils.md5(model.getPassword()));
            try {
                userService.updatePassword(user);
                ret = "1";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        BOSUtils.getResponse().getWriter().print(ret);
        return NONE;
    }

    @Action("updateRoles")
    public String updateRoles() throws IOException {
    	
		return NONE;
    }
    
    @Action("pageQuery")
    @Override
    public String pageQuery() throws IOException {
        userService.pageQuery(pageBean);
        java2Json(pageBean, new String[]{"detachedCriteria", "roles","noticeBills"});
        return NONE;
    }

    @Action("add")
    @Override
    public String add() throws IOException {
        userService.save(model, roleIds);
        return LIST;
    }

    @Action("delete")
    @Override
    public String delete() throws IOException {
        // TODO Auto-generated method stub
        return NONE;
    }

    @Action("edit")
    @Override
    public String edit() throws IOException {
        userService.saveOrUpdate(model, roleIds);
        return NONE;
    }

    @Action("findByQ")
    @Override
    public String findByQ() throws IOException {
        // TODO Auto-generated method stub
        return NONE;
    }

    @Action("getRoles")
    public String getRoles() throws IOException {
        Set<Role> roles = userService.findById(BOSUtils.getLoginUser().getId()).getRoles();
        List<String> list = new ArrayList<String>();
        for (Role role : roles) {
            list.add(role.getId());
        }
        java2Json(list, new String[]{"users","functions"});
        return NONE;
    }
    // endregion actions

    // region private fields methods
    @Autowired
    private IUserService userService;

    // 属性驱动，输入的验证码
    private String checkcode;

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    private String[] roleIds;
    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }
    
    // 密码验证规则正则表达式
    private final static String PASSWORD_REGEX = "^(?=.{4,10}$)(?![0-9]+$)[0-9a-zA-Z_]+$";
    // endregion private fields methods
}
