package com.itheima.bos.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 业务通知单
 * NoticeBill entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "qp_noticebill")
public class NoticeBill implements java.io.Serializable {

    // Fields

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private User user;
    private Staff staff;
    private Integer customerId;
    private String customerName;
    private String delegater;
    private String telephone;
    private String pickaddress;
    private String arrivecity;
    private String product;
    private Date pickdate;
    private Integer num;
    private Double weight;
    private String volume;
    private String remark;
    private String ordertype = ORDERTYPE_MAN;
    private Set<WorkBill> workBills = new HashSet<WorkBill>(0);

    public static final String ORDERTYPE_AUTO="自动分单";
    public static final String ORDERTYPE_MAN="人工分单";
    
    // Constructors

    /** default constructor */
    public NoticeBill() {
    }

    /** full constructor */
    public NoticeBill(User user, Staff staff, Integer customerId,
            String customerName, String delegater, String telephone,
            String pickaddress, String arrivecity, String product,
            Date pickdate, Integer num, Double weight, String volume,
            String remark, String ordertype, Set<WorkBill> workBills) {
        this.user = user;
        this.staff = staff;
        this.customerId = customerId;
        this.customerName = customerName;
        this.delegater = delegater;
        this.telephone = telephone;
        this.pickaddress = pickaddress;
        this.arrivecity = arrivecity;
        this.product = product;
        this.pickdate = pickdate;
        this.num = num;
        this.weight = weight;
        this.volume = volume;
        this.remark = remark;
        this.ordertype = ordertype;
        this.workBills = workBills;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    public Staff getStaff() {
        return this.staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    @Column(name = "customer_id")
    public Integer getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @Column(name = "customer_name", length = 20)
    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Column(name = "delegater", length = 20)
    public String getDelegater() {
        return this.delegater;
    }

    public void setDelegater(String delegater) {
        this.delegater = delegater;
    }

    @Column(name = "telephone", length = 20)
    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "pickaddress", length = 200)
    public String getPickaddress() {
        return this.pickaddress;
    }

    public void setPickaddress(String pickaddress) {
        this.pickaddress = pickaddress;
    }

    @Column(name = "arrivecity", length = 20)
    public String getArrivecity() {
        return this.arrivecity;
    }

    public void setArrivecity(String arrivecity) {
        this.arrivecity = arrivecity;
    }

    @Column(name = "product", length = 20)
    public String getProduct() {
        return this.product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "pickdate", length = 10)
    public Date getPickdate() {
        return this.pickdate;
    }

    public void setPickdate(Date pickdate) {
        this.pickdate = pickdate;
    }

    @Column(name = "num")
    public Integer getNum() {
        return this.num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Column(name = "weight", precision = 22, scale = 0)
    public Double getWeight() {
        return this.weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Column(name = "volume", length = 20)
    public String getVolume() {
        return this.volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    @Column(name = "remark")
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 分单类型：自动分单、人工分单
     * @return
     */
    @Column(name = "ordertype", length = 20)
    public String getOrdertype() {
        return this.ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "noticeBill")
    public Set<WorkBill> getWorkBills() {
        return this.workBills;
    }

    public void setWorkBills(Set<WorkBill> workBills) {
        this.workBills = workBills;
    }

}