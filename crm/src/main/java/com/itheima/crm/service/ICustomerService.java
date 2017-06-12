package com.itheima.crm.service;

import java.io.Serializable;
import java.util.List;

import javax.jws.WebService;

import com.itheima.crm.domain.Customer;

@WebService
public interface ICustomerService {
	String sayHello();
	List<Customer> findAll();
	List<Customer> findListNotAssociated();
	List<Customer> findListAssociatedToZone(Integer decidedZoneId);
	void associateCustomersToDecidedZone(Integer decidedZoneId, Integer[] customersIds);
	Customer findCustomerById(Integer id);
	Customer findCustomerByTelephone(String telephone);
	Integer findDecidedZoneIdByAddress(String address);
}
