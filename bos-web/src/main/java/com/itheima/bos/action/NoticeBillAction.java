package com.itheima.bos.action;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itheima.bos.action.base.BaseAction;
import com.itheima.bos.domain.DecidedZone;
import com.itheima.bos.domain.NoticeBill;
import com.itheima.bos.service.INoticeBillService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.bos.utils.JacksonUtils;
import com.itheima.crm.service.Customer;
import com.itheima.crm.service.ICustomerService;

@Controller
@Scope("prototype")
@Namespace("/noticeBill")
@ParentPackage("basicStruts")
public class NoticeBillAction extends BaseAction<NoticeBill> {

    // region actions
    @Action("findCustomerByTelephone")
    public String findCustomerByTelephone() throws IOException {
        String telephone = model.getTelephone();
        Customer customer = customerService.findCustomerByTelephone(telephone);
        JacksonUtils.init(Customer.class).serializeObj(BOSUtils.getResponse(),
                customer);
        return NONE;
    }

    /**
     * 保存一个业务通知单，并尝试自动分单
     */
    @Action("add")
    @Override
    public String add() throws IOException {
        int res = noticeBillService.save(model);
        BOSUtils.writeToResponse(String.valueOf(res));
        return null;
    }

    @Action("listNotAssigned")
    public String listNotAssigned() throws IOException {
        List<NoticeBill> list = noticeBillService.findNotAssigned();
        @JsonIgnoreProperties({"user","staff","workBills"})
        abstract class NoticeBillMixIn {
            @JsonProperty("noticeBillId")
            public abstract Integer getId();
        }
        JacksonUtils.init(NoticeBill.class)
                // .setIncludeProperties("id", "customerName", "pickaddress",
                // "ordertype")
                .addMixIn(NoticeBillMixIn.class)
                .serializeObj(BOSUtils.getResponse(), list);
        return NONE;
    }

    @Action("manualAssignment")
    public String manualAssignment() throws IOException {
        String res = "0";
        try {
            noticeBillService.manualAssignment(noticeBillId, decidedZone);
            res = "1";
        } catch (Exception e) {
            e.printStackTrace();
        }
        BOSUtils.writeToResponse(res);
        return NONE;
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

    // region private fields, methods

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private INoticeBillService noticeBillService;

    private DecidedZone decidedZone;

    public void setDecidedZone(DecidedZone decidedZone) {
        this.decidedZone = decidedZone;
    }

    private Integer[] noticeBillId;

    public void setNoticeBillId(Integer[] noticeBillId) {
        this.noticeBillId = noticeBillId;
    }
    // endregion

}
