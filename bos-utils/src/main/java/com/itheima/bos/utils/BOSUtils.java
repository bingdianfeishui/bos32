package com.itheima.bos.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.itheima.bos.domain.User;

public class BOSUtils {
    private BOSUtils(){}
	public static HttpSession getSession(){
		return ServletActionContext.getRequest().getSession();
	}
	
	public static User getLoginUser(){
		return (User) getSession().getAttribute("loginUser");
	}
	
	public static HttpServletResponse getResponse(){
		return ServletActionContext.getResponse();
	}
	public static HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
	}
}
