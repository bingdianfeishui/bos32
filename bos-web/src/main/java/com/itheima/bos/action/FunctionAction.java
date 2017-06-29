package com.itheima.bos.action;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.action.base.BaseAction;
import com.itheima.bos.domain.Function;
import com.itheima.bos.service.IFunctionService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.bos.utils.JacksonUtils;

@Controller
@Scope("prototype")
@Namespace("/function")
@ParentPackage("basicStruts")
@Results({@Result(name="list", location="/WEB-INF/pages/admin/function.jsp")})
public class FunctionAction extends BaseAction<Function> {
	// region actions
	@Action("listAjax")
	public String listAjax() throws IOException {
		List<Function> list = functionService.findAll();
		JacksonUtils.init(Function.class)
				//.setExceptProperties("roles", "parent", "childred")
				.setIncludeProperties("id","name")
				.serializeObj(BOSUtils.getResponse(), list);
		return NONE;
	}

	@Override
	@Action("add")
	public String add() throws IOException {
		functionService.save(model);
		return LIST;
	}

	@Override
	public String delete() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String edit() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Action("pageQuery")
	public String pageQuery() throws IOException {
		functionService.pageQuery(pageBean);
		@
		class FunctionMixin{}
		
		JacksonUtils.init(pageBean.getClass()).setIncludeProperties("rows", "total").addMixIn(FunctionMixin.class)
		.serializeObj(BOSUtils.getResponse(), pageBean);
		return NONE;
	}

	@Override
	public String findByQ() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	// endregion actions
	// region fields and private methods

	private static final long serialVersionUID = 1L;
	@Autowired
	private IFunctionService functionService;

	public void setFunctionService(IFunctionService functionService) {
		this.functionService = functionService;
	}
	// endregion fields and private methods
}
