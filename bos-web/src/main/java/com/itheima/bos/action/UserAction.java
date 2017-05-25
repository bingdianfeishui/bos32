package com.itheima.bos.action;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.action.base.BaseAction;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.IUserService;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {

	 public static final String HOME = "home";
	 public static final String RELOGIN = "relogin";

	private static final long serialVersionUID = 8815472318245773723L;

	private String checkcode;

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	@Autowired
	private IUserService userService;

	public String login() {
		String validateCode = (String) getSession().getAttribute("key");
		if (StringUtils.isNotBlank(validateCode) && validateCode.equals(checkcode)) {
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
}
