package com.itheima.bos.interceptor;

import com.itheima.bos.domain.User;
import com.itheima.bos.utils.BOSUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class BOSLoginInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
//		ActionProxy proxy = invocation.getProxy();	//打印总共有拦截了哪些请求
//		System.out.println(proxy.getNamespace() + proxy.getActionName());

		User loginUser = BOSUtils.getLoginUser();
		if (loginUser == null) {
			return "login";
		}
		return invocation.invoke();
	}

}
