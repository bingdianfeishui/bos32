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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itheima.bos.action.base.BaseAction;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.IFunctionService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.bos.utils.JacksonUtils;

@Controller
@Scope("prototype")
@Namespace("/function")
@ParentPackage("basicStruts")
@Results({ @Result(name = "list", location = "/WEB-INF/pages/admin/function.jsp") })
public class FunctionAction extends BaseAction<Function> {
    // region actions
    @Action("findMenu")
    public String findMenu() {
        User user = BOSUtils.getLoginUser();
        List<Function> list = null;
        if (user.getUsername().equals(BOSUtils.ADMIN_USERNAME)) {
            list = functionService.findAllMenuFunction();
        } else {
            list = functionService.findMenuFuncitonByUser(user);
        }
        java2Json(list, new String[] { "roles", "children" });
        return NONE;
    }

    @Action("listAjax")
    public String listAjax() throws IOException {
        List<Function> list = functionService.findAll();

        // this.java2Json(list, new String[]{"parent","roles"});
        JacksonUtils.init(Function.class)
                // .setExceptProperties("roles", "parent")
                .setIncludeProperties("id", "name", "children", "text")
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
        // 由于Function中也有page属性，所以页面分页控件传入的page首先给写入model中
        int currentPage = Integer.parseInt(model.getPage());
        pageBean.setCurrentPage(currentPage); // 取出model中的page属性赋给pageBean

        functionService.pageQuery(pageBean);
        @JsonIgnoreProperties({ "parent", "children", "roles" })
        class FunctionMixin {
        }

        JacksonUtils.init(pageBean.getClass())
                .setIncludeProperties("rows", "total")
                .addMixIn(Function.class, FunctionMixin.class)
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
