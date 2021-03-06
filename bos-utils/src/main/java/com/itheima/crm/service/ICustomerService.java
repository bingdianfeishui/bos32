
package com.itheima.crm.service;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ICustomerService", targetNamespace = "http://service.crm.itheima.com/")
@XmlSeeAlso({
//    ObjectFactory.class
})
public interface ICustomerService {


    /**
     * 
     * @return
     *     returns java.util.List<com.itheima.crm.service.Customer>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findAll", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.service.FindAll")
    @ResponseWrapper(localName = "findAllResponse", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.service.FindAllResponse")
    public List<Customer> findAll();

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.Integer
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findDecidedZoneIdByAddress", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.service.FindDecidedZoneIdByAddress")
    @ResponseWrapper(localName = "findDecidedZoneIdByAddressResponse", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.service.FindDecidedZoneIdByAddressResponse")
    public Integer findDecidedZoneIdByAddress(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns com.itheima.crm.service.Customer
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findCustomerById", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.service.FindCustomerById")
    @ResponseWrapper(localName = "findCustomerByIdResponse", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.service.FindCustomerByIdResponse")
    public Customer findCustomerById(
        @WebParam(name = "arg0", targetNamespace = "")
        Integer arg0);

    /**
     * 
     * @return
     *     returns java.util.List<com.itheima.crm.service.Customer>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findListNotAssociated", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.service.FindListNotAssociated")
    @ResponseWrapper(localName = "findListNotAssociatedResponse", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.service.FindListNotAssociatedResponse")
    public List<Customer> findListNotAssociated();

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<com.itheima.crm.service.Customer>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findListAssociatedToZone", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.service.FindListAssociatedToZone")
    @ResponseWrapper(localName = "findListAssociatedToZoneResponse", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.service.FindListAssociatedToZoneResponse")
    public List<Customer> findListAssociatedToZone(
        @WebParam(name = "arg0", targetNamespace = "")
        Integer arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "associateCustomersToDecidedZone", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.service.AssociateCustomersToDecidedZone")
    @ResponseWrapper(localName = "associateCustomersToDecidedZoneResponse", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.service.AssociateCustomersToDecidedZoneResponse")
    public void associateCustomersToDecidedZone(
        @WebParam(name = "arg0", targetNamespace = "")
        Integer arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        List<Integer> arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns com.itheima.crm.service.Customer
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findCustomerByTelephone", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.service.FindCustomerByTelephone")
    @ResponseWrapper(localName = "findCustomerByTelephoneResponse", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.service.FindCustomerByTelephoneResponse")
    public Customer findCustomerByTelephone(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "sayHello", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.service.SayHello")
    @ResponseWrapper(localName = "sayHelloResponse", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.service.SayHelloResponse")
    public String sayHello();

}
