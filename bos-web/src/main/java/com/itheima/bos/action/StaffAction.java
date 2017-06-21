package com.itheima.bos.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itheima.bos.action.base.BaseAction;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.IStaffService;
import com.itheima.bos.service.impl.StaffServiceImpl;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.bos.utils.JacksonUtils;

@Controller
@Scope("prototype")
@Namespace("/staff")
@ParentPackage("basicStruts")
public class StaffAction extends BaseAction<Staff> {
    private static final long serialVersionUID = 1L;

    // region actions
    @Action(value = "add")
    public String add() throws IOException {
        List<String> list = validateModel();

        if (list.size() == 0) {
            try {
                staffService.save(model);
            } catch (Exception e) {
                e.printStackTrace();
                list.add("服务器忙，请稍后重试！");
            }
        }
        BOSUtils.getResponse().setContentType("text/html;charset=UTF-8");
        BOSUtils.getResponse().getWriter().print(list.toString());
        return NONE;
    }

    @Action("pageQuery")
    public String pageQuery() throws IOException {

        if (searchMode) {
            DetachedCriteria criteria = pageBean.getDetachedCriteria();
            // 添加查询条件
            if (model.getId() != null) {
                criteria.add(Restrictions.eq("id", model.getId()));
            }
            if (StringUtils.isNotBlank(model.getName())) {
                criteria.add(Restrictions.like("name", "%" + model.getName()
                        + "%"));
            }
            if (StringUtils.isNotBlank(model.getTelephone())) {
                criteria.add(Restrictions.like("telephone",
                        "%" + model.getTelephone() + "%"));
            }
            if (StringUtils.isNotBlank(model.getStation())) {
                criteria.add(Restrictions.like("station",
                        "%" + model.getStation() + "%"));
            }
            if (StringUtils.isNotBlank(model.getStandard())) {
                criteria.add(Restrictions.like("standard",
                        "%" + model.getStandard() + "%"));
            }
            if (StringUtils.isNotBlank(model.getHaspda())) {
                criteria.add(Restrictions.eq("haspda", model.getHaspda()));
            }
            if (StringUtils.isNotBlank(model.getDeltag())) {
                criteria.add(Restrictions.eq("deltag", model.getDeltag()));
            }
        }
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
        IStaffService bean = context.getBean(IStaffService.class);
        // 查询
        staffService.pageQuery(pageBean);

        BOSUtils.getResponse().setContentType("text/json;charset=UTF-8");
        String json = JacksonUtils.init(pageBean.getClass())
                .setIncludeProperties("total", "rows")
                .addMixIn(Staff.class, StaffMixIn.class).serializeObj(pageBean);
        // System.out.println(json);
        BOSUtils.getResponse().getWriter().write(json);
        return NONE;
    }

    @RequiresPermissions("staff-delete")
    @Action("batchDelete")
    public String batchDelete() throws IOException {
        batchDeleteOrRestore(ids, StaffServiceImpl.Operation.DELETE);
        return NONE;
    }

    @Action("batchRestore")
    public String batchRestore() throws IOException {
        batchDeleteOrRestore(ids, StaffServiceImpl.Operation.RESTORE);
        return NONE;
    }

    @Action("edit")
    public String edit() throws IOException {
        List<String> list = validateModel();

        if (list.size() == 0) {
            try {
                Staff staff = staffService.findById(model.getId());
                // 更新数据
                staff.setName(model.getName());
                staff.setTelephone(model.getTelephone());
                staff.setStation(model.getStation());
                staff.setStandard(model.getStandard());
                staff.setHaspda(model.getHaspda());
                staffService.update(staff);
            } catch (Exception e) {
                e.printStackTrace();
                list.add("服务器忙，请稍后重试！");
            }
        }
        BOSUtils.getResponse().setContentType("text/html;charset=UTF-8");
        BOSUtils.getResponse().getWriter().print(list.toString());
        return NONE;
    }

    @Override
    public String delete() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Action("findByQ")
    @Override
    public String findByQ() throws IOException {
        List<Staff> list = new ArrayList<>();
        if (StringUtils.isBlank(q)) {
            staffService.pageQuery(pageBean);
            list = pageBean.getRows();
        } else {
            list = staffService.findByQ(q);
        }

        JacksonUtils.init(Staff.class)
                .setIncludeProperties("id", "name", "telephone", "desc")
                .serializeObj(BOSUtils.getResponse(), list);

        return NONE;
    }

    // endregion actions

    // region private fiedlds and methods
    public IStaffService staffService;
    @Autowired(required=true)
    public void setStaffService(IStaffService staffService) {
        this.staffService = staffService;
    }
    private static final String TELEPHONE_REGEX = "^1[34578][0-9]{9}$";

    private List<String> validateModel() {
        List<String> list = new ArrayList<>();
        if (StringUtils.isBlank(model.getName()))
            list.add("用户名不能为空");
        if (StringUtils.isBlank(model.getTelephone())
                || !Pattern.matches(TELEPHONE_REGEX, model.getTelephone()))
            list.add("手机号码输入不正确");
        if (StringUtils.isBlank(model.getStation()))
            list.add("单位不能为空");
        if (StringUtils.isBlank(model.getStandard()))
            list.add("取派标准不能为空");
        return list;
    }

    /**
     * 混入类，用于过滤掉Staff中的定区数据，避免冗余字段及递归
     * 
     * @author Lee
     */
    @JsonIgnoreProperties("decidedZones")
    abstract class StaffMixIn {
    }

    private String ids;

    /**
     * 属性驱动，批量操作传入的ids字符串，多个id以逗号隔开
     * 
     * @param ids
     */
    public void setIds(String ids) {
        this.ids = ids;
    }

    /**
     * 批量删除或恢复的实际执行方法
     * 
     * @param ids
     * @param op
     * @throws IOException
     */
    private void batchDeleteOrRestore(String ids, StaffServiceImpl.Operation op)
            throws IOException {
        String flag = "0";
        try {
            if (StringUtils.isNotBlank(ids)) {
                String[] idArray = ids.split(",");
                List<Integer> list = new ArrayList<>();
                for (String str : idArray) {
                    Integer i = Integer.valueOf(str);
                    if (i != null)
                        list.add(i);
                }
                staffService.batchDeleteOrRestore(
                        list.toArray(new Integer[list.size()]), op);
            }
            flag = "1";
        } catch (Exception e) {
            e.printStackTrace();
        }
        BOSUtils.getResponse().getWriter().write(flag);
    }
    // endregion private fiedlds and methods
}
