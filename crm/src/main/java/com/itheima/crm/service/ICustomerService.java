package com.itheima.crm.service;

import java.util.List;

import javax.jws.WebService;

import com.itheima.crm.domain.Customer;

@WebService
public interface ICustomerService {
	String sayHello();
	List<Customer> findAll();
	List<Customer> findListNotAssociated();
	List<Customer> findListAssociatedToZone(Integer decidedZoneId);
}
