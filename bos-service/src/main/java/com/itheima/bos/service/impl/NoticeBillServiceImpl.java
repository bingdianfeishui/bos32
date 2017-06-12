package com.itheima.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IDecidedZoneDao;
import com.itheima.bos.dao.INoticeBillDao;
import com.itheima.bos.dao.IWorkBillDao;
import com.itheima.bos.domain.DecidedZone;
import com.itheima.bos.domain.NoticeBill;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.domain.User;
import com.itheima.bos.domain.WorkBill;
import com.itheima.bos.service.INoticeBillService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.crm.service.ICustomerService;

@Service
@Transactional
public class NoticeBillServiceImpl implements INoticeBillService {
    @Autowired
    private INoticeBillDao noticeBillDao;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IDecidedZoneDao decidedZoneDao;
    @Autowired
    private IWorkBillDao wordBillDao;
    
    @Override
    public void save(NoticeBill model) {
        User user = BOSUtils.getLoginUser();
        model.setUser(user);
        noticeBillDao.save(model);

        String pickaddress = model.getPickaddress();//获取取件地址
        Integer dzId = customerService
                .findDecidedZoneIdByAddress(pickaddress);   //crm远程服务获取定区id
        if (dzId != null && dzId > 0) {
            //查询到了定区id，则根据id查定区
            DecidedZone decidedZone = decidedZoneDao.findById(dzId);
            if(decidedZone!=null){
              //若查到定区，则获取取派员
              Staff staff = decidedZone.getStaff();
              model.setStaff(staff);//业务通知单关联取派员
              model.setOrdertype(NoticeBill.ORDERTYPE_AUTO);//自动分单
              
              //产生新工单
              WorkBill workBill= new WorkBill();
              workBill.setNoticeBill(model);
              workBill.setStaff(staff);
              workBill.setRemark(model.getRemark());
              
              //保存工单
              wordBillDao.save(workBill);
            }
        }
    }

}
