package com.itheima.bos.utils;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.itheima.bos.domain.User;

public class BOSUtils {
	public static HttpSession getSession(){
		return ServletActionContext.getRequest().getSession();
	}
	
	public static User getLoginUser(){
		return (User) getSession().getAttribute("loginUser");
	}
}
