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
import com.itheima.bos.domain.Role;
import com.itheima.bos.service.IRoleService;

@Controller
@Scope("prototype")
@Namespace("/role")
@ParentPackage("basicStruts")
@Results({@Result(name="list", location="/WEB-INF/pages/admin/role.jsp")})
public class RoleAction extends BaseAction<Role> {

    //region actions
    @Action("listAjax")
    public String listAjax(){
        List<Role> list = roleService.findAll();
        java2Json(list, new String[]{"users","functions"} );
        return NONE;
    }
    
    @Action("add")
    @Override
    public String add() throws IOException {
        String[] ids = functionIds.split(",");
        roleService.save(model, ids);
        return LIST;
    }

    @Override
    public String delete() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Action("edit")
    @Override
    public String edit() throws IOException {
    	roleService.saveOrUpdate(model);
        return NONE;
    }

    @Action("pageQuery")
    @Override
    public String pageQuery() throws IOException {
        roleService.pageQuery(pageBean);
        java2Json(pageBean, new String[]{"detachedCriteria","users","functions"});
        return NONE;
    }

    @Override
    public String findByQ() throws IOException {
        // TODO Auto-generated method stub
        return NONE;
    }
    //endregion actions
    
    //region fields and private methods
    private static final long serialVersionUID = 1L;
    @Autowired
    private IRoleService roleService;
    
    private String functionIds;
    public void setFunctionIds(String functionIds) {
        this.functionIds = functionIds;
    }
    //endregion fields and private methods
}
