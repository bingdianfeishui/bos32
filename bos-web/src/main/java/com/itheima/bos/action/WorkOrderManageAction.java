package com.itheima.bos.action;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.action.base.BaseAction;
import com.itheima.bos.domain.WorkOrderManage;
import com.itheima.bos.service.IWorkOrderManageService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.bos.utils.JacksonUtils;

@Controller
@Scope("prototype")
@Namespace("/workOrderManage")
@ParentPackage("basicStruts")
public class WorkOrderManageAction extends BaseAction<WorkOrderManage> {

    // retion actions

    @Action("pageQuery")
    public String pageQuery() throws IOException {
        workOrderManageService.pageQuery(pageBean);
        JacksonUtils.init(pageBean.getClass())
                .setIncludeProperties("total", "rows")
                .serializeObj(BOSUtils.getResponse(), pageBean);
        return null;
    }

    @Action("saveOrUpdate")
    public String saveOrUpdate() throws IOException {
        int res = 0;
        try {
            workOrderManageService.saveOrUpdate(model);
            res = 1;
        } catch (Exception e) {
            e.printStackTrace();
            res = 0;
        }
        BOSUtils.writeToResponse(String.valueOf(res));

        return NONE;
    }

    @Override
    public String add() throws IOException {
        // TODO Auto-generated method stub
        return null;
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
    public String findByQ() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    // endregion actions

    // region fields and methods
    private static final long serialVersionUID = 1L;

    @Resource
    private IWorkOrderManageService workOrderManageService;
    // endregion fields and methods
}
