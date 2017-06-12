package com.itheima.bos.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itheima.bos.action.base.BaseAction;
import com.itheima.bos.domain.DecidedZone;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.IDecidedZoneService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.bos.utils.JacksonUtils;
import com.itheima.crm.service.Customer;
import com.itheima.crm.service.ICustomerService;

@Controller
@Scope("prototype")
@Namespace("/decidedZone")
@ParentPackage("basicStruts")
public class DecidedZoneAction extends BaseAction<DecidedZone> {

    // region actions
    @Action("listCustomersNotAssociated")
    public String listCustomersNotAssociated() throws IOException {
        List<Customer> list = customerService.findListNotAssociated();
        if (list != null && list.size() > 0) {
            JacksonUtils.init(Customer.class).serializeObj(
                    BOSUtils.getResponse(), list);
        }else{
            returnBlankJasonObj();
        }
        return NONE;
    }

    @Action("listCustomersAssociatedById")
    public String listCustomersAssociatedById() throws IOException {
        List<Customer> list = customerService.findListAssociatedToZone(model
                .getId());
        if (list != null && list.size() > 0) {
            JacksonUtils.init(Customer.class).serializeObj(
                    BOSUtils.getResponse(), list);
        }else{
            BOSUtils.getResponse().getWriter().write("[]");
        }
        return NONE;
    }

    @Action("associateCustomers")
    public String associateCustomers() throws IOException {
        // System.out.println(customerIds);
        customerService.associateCustomersToDecidedZone(model.getId(),
                customerIds);
        return NONE;
    }

    @Action("add")
    @Override
    public String add() throws IOException {
        decidedZoneService.save(model, subareaId);
        return NONE;
    }

    @Override
    public String delete() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Action("edit")
    @Override
    public String edit() throws IOException {
        decidedZoneService.update(model, subareaId);
        return NONE;
    }

    @Action("pageQuery")
    @Override
    public String pageQuery() throws IOException {
        if (searchMode) {
            DetachedCriteria criteria = pageBean.getDetachedCriteria();
            if (model.getId() != null) {
                criteria.add(Restrictions.eq("id", model.getId()));
            }
            if (model.getStaff() != null
                    && model.getStaff().getStation() != null) {
                criteria.createAlias("staff", "s");
                criteria.add(Restrictions.like("s.station", "%"
                        + model.getStaff().getStation() + "%"));
            }
        }

        decidedZoneService.pageQuery(pageBean);

        JacksonUtils.init(pageBean.getClass())
                .setIncludeProperties("total", "rows")
                .addMixIn(Staff.class, StaffMixIn.class)
                .addMixIn(DecidedZone.class, DecidedZoneMixIn.class)
                .serializeObj(BOSUtils.getResponse(), pageBean);
        return NONE;
    }

    @Override
    public String findByQ() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    // endregion actions

    // region private fiedlds and methods
    private static final long serialVersionUID = 1L;

    @Autowired
    private IDecidedZoneService decidedZoneService;

    @Autowired
    private ICustomerService customerService;

    @JsonIgnoreProperties("decidedZones")
    interface StaffMixIn {
    }

    @JsonIgnoreProperties("subareas")
    interface DecidedZoneMixIn {
    }

    private List<Integer> customerIds = new ArrayList<Integer>(0);

    public void setCustomerIds(List<Integer> customerIds) {
        this.customerIds = customerIds;
    }

    private Integer[] subareaId;
    public void setSubareaId(Integer[] subareaId) {
		this.subareaId = subareaId;
	}
    // endregion private fiedlds and methods
}
