package com.itheima.bos.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.action.base.BaseAction;
import com.itheima.bos.domain.DecidedZone;
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
    @Action("findListNotAssociated")
    public String findListNotAssociated() throws IOException {
    	List<Customer> list = customerService.findListNotAssociated();

        JacksonUtils.init(Customer.class).serializeObj(BOSUtils.getResponse(),
                list);

        return NONE;
    }
    
    @Action("findListHasAssociated")
    public String findListHasAssociated() throws IOException {
    	List<Customer> list = customerService.findListAssociatedToZone(model.getId());
    	
    	JacksonUtils.init(Customer.class).serializeObj(BOSUtils.getResponse(),
    			list);
    	
    	return NONE;
    }
    
    @Action("associateCustomers")
    public String associateCustomers() throws IOException {
//    	System.out.println(customerIds);
    	customerService.associateCustomersToDecidedZone(model.getId(), customerIds);
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
    public String pageQuery() throws IOException {
        // TODO Auto-generated method stub
        return null;
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
    
    private List<Integer> customerIds = new ArrayList<Integer>(10);
    public void setCustomerIds(List<Integer> customerIds) {
		this.customerIds = customerIds;
	}
    // endregion private fiedlds and methods
}
