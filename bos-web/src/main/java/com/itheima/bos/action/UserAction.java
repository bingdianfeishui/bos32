package com.itheima.bos.action;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.action.base.BaseAction;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.IUserService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.bos.utils.MD5Utils;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {

	private static final long serialVersionUID = 8815472318245773723L;

	private String checkcode;

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	@Autowired
	private IUserService userService;

	public String login() {
		String validateCode = (String) getSession().getAttribute("key");
		//TODO:屏蔽验证码功能
		if (true || StringUtils.isNotBlank(validateCode) && validateCode.equals(checkcode)) {
			User user = userService.login(model);
			if(user != null){
				getSession().setAttribute("loginUser", user);
				return HOME;
			}
			else{
				addActionError("用户名或密码错误！");
				return LOGIN;
			}
		}

		addActionError("验证码输入错误！");
		return LOGIN;
	}
	
	public String logout(){
		getSession().invalidate();
		return RELOGIN;
	}
	
	//密码验证规则正则表达式
    private final static String passwordRegex="^(?=.{4,10}$)(?![0-9]+$)[0-9a-zA-Z_]+$";
	public String updatePwd() throws IOException{
		String ret= "0";
	    if(StringUtils.isNotBlank(model.getPassword()) && Pattern.matches(passwordRegex, model.getPassword())){
	        User user = BOSUtils.getLoginUser();
			user.setPassword(MD5Utils.md5(model.getPassword()));
			try {
			    userService.updatePassword(user);
                ret = "1";
            } catch (Exception e) {
            }
		}
		BOSUtils.getResponse().getWriter().print(ret);
		return NONE;
	}
}
