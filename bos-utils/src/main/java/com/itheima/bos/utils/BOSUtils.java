package com.itheima.bos.utils;

import java.io.IOException;

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
	/**
	 * 通过{@link HttpServletResponse#getWriter()}方法写出到响应
	 * 会自动设置ContentType为"text/html;charset=UTF-8"
	 * @param str 需要写回的数据
	 * @throws IOException 
	 */
	public static void writeToResponse(String str) throws IOException{
	    getResponse().setContentType("text/html;charset=UTF-8");
	    getResponse().getWriter().write(str);
	}
}
