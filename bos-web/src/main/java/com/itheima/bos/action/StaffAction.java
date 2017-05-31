package com.itheima.bos.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itheima.bos.action.base.BaseAction;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.IStaffService;
import com.itheima.bos.service.impl.StaffService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.bos.utils.JackSonUtils;
import com.itheima.bos.utils.PageBean;

@Controller
@Scope("prototype")
@Namespace("/staff")
@ParentPackage("basicStruts")
public class StaffAction extends BaseAction<Staff> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Resource
    public IStaffService staffService;

    private static final String telRegex = "^1[34578][0-9]{9}$";

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

    private List<String> validateModel() {
        List<String> list = new ArrayList<>();
        if (StringUtils.isBlank(model.getName()))
            list.add("用户名不能为空");
        if (StringUtils.isBlank(model.getTelephone())
                || !Pattern.matches(telRegex, model.getTelephone()))
            list.add("手机号码输入不正确");
        if (StringUtils.isBlank(model.getStation()))
            list.add("单位不能为空");
        if (StringUtils.isBlank(model.getStandard()))
            list.add("取派标准不能为空");
        return list;
    }

    private Integer page;
    private Integer rows;

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    // 混入类，用于过滤掉Staff中的定区数据，避免冗余字段及递归
    @JsonIgnoreProperties("decidedZones")
    abstract class StaffMixIn {
    }

    private boolean searchMode = false;
    public void setSearchMode(boolean searchMode) {
        this.searchMode = searchMode;
    }

    // easyui pageBean ajax
    @Action("pageQuery")
    public String pageQuery() throws IOException {
        PageBean<Staff> pageBean = new PageBean<Staff>(Staff.class, page, rows);
        if(searchMode){
            DetachedCriteria criteria = pageBean.getDetachedCriteria();
            //添加查询条件
            if(StringUtils.isNotBlank(model.getId())){
                criteria.add(Restrictions.like("id", "%"+model.getId()+"%"));
            }
            if(StringUtils.isNotBlank(model.getName())){
                criteria.add(Restrictions.like("name", "%"+model.getName()+"%"));
            }
            if(StringUtils.isNotBlank(model.getTelephone())){
                criteria.add(Restrictions.like("telephone", "%"+model.getTelephone()+"%"));
            }
            if(StringUtils.isNotBlank(model.getStation())){
                criteria.add(Restrictions.like("station", "%"+model.getStation()+"%"));
            }
            if(StringUtils.isNotBlank(model.getStandard())){
                criteria.add(Restrictions.like("standard", "%"+model.getStandard()+"%"));
            }
            if(StringUtils.isNotBlank(model.getHaspda())){
                criteria.add(Restrictions.eq("haspda", model.getHaspda()));
            }
            if(StringUtils.isNotBlank(model.getDeltag())){
                criteria.add(Restrictions.eq("deltag", model.getDeltag()));
            }
        }
        
        //查询
        staffService.pageQuery(pageBean);
        
        BOSUtils.getResponse().setContentType("text/json;charset=UTF-8");
        String json = JackSonUtils.init(pageBean.getClass())
                .setIncludeProperties("total", "rows")
                .addMixIn(Staff.class, StaffMixIn.class).SerializeObj(pageBean);
        // System.out.println(json);
        BOSUtils.getResponse().getWriter().write(json);
        return NONE;
    }

    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }

    @Action("batchDelete")
    public String batchDelete() throws IOException {
        batchDeleteOrRestore(ids, StaffService.Operation.DELETE);
        return NONE;
    }

    @Action("batchRestore")
    public String batchRestore() throws IOException {
        batchDeleteOrRestore(ids, StaffService.Operation.RESTORE);
        return NONE;
    }

    private void batchDeleteOrRestore(String ids, StaffService.Operation op)
            throws IOException {
        String flag = "0";
        try {
            if (StringUtils.isNotBlank(ids)) {
                String[] idArray = ids.split(",");
                staffService.batchDeleteOrRestore(idArray, op);
            }
            flag = "1";
        } catch (Exception e) {
            e.printStackTrace();
        }
        BOSUtils.getResponse().getWriter().write(flag);
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
}
