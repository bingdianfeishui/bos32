package com.itheima.bos.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * WorkOrderManage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "qp_workordermanage")
public class WorkOrderManage implements java.io.Serializable {

    // Fields

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String arrivecity;
    private String product;
    private Integer num;
    private Double weight;
    private String floadreqr;
    private String prodtimelimit;
    private String prodtype;
    private String sendername;
    private String senderphone;
    private String senderaddr;
    private String receivername;
    private String receiverphone;
    private String receiveraddr;
    private Integer feeitemnum;
    private Double actlweit;
    private String vol;
    private String managerCheck;
    private Date updatetime;

    // Constructors

    /** default constructor */
    public WorkOrderManage() {
    }

    /** full constructor */
    public WorkOrderManage(String arrivecity, String product, Integer num,
            Double weight, String floadreqr, String prodtimelimit,
            String prodtype, String sendername, String senderphone,
            String senderaddr, String receivername, String receiverphone,
            String receiveraddr, Integer feeitemnum, Double actlweit,
            String vol, String managerCheck, Date updatetime) {
        this.arrivecity = arrivecity;
        this.product = product;
        this.num = num;
        this.weight = weight;
        this.floadreqr = floadreqr;
        this.prodtimelimit = prodtimelimit;
        this.prodtype = prodtype;
        this.sendername = sendername;
        this.senderphone = senderphone;
        this.senderaddr = senderaddr;
        this.receivername = receivername;
        this.receiverphone = receiverphone;
        this.receiveraddr = receiveraddr;
        this.feeitemnum = feeitemnum;
        this.actlweit = actlweit;
        this.vol = vol;
        this.managerCheck = managerCheck;
        this.updatetime = updatetime;
    }

    // Property accessors
    @Id
//    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Column(name = "floadreqr")
    public String getFloadreqr() {
        return this.floadreqr;
    }

    public void setFloadreqr(String floadreqr) {
        this.floadreqr = floadreqr;
    }

    @Column(name = "prodtimelimit", length = 40)
    public String getProdtimelimit() {
        return this.prodtimelimit;
    }

    public void setProdtimelimit(String prodtimelimit) {
        this.prodtimelimit = prodtimelimit;
    }

    @Column(name = "prodtype", length = 40)
    public String getProdtype() {
        return this.prodtype;
    }

    public void setProdtype(String prodtype) {
        this.prodtype = prodtype;
    }

    @Column(name = "sendername", length = 20)
    public String getSendername() {
        return this.sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    @Column(name = "senderphone", length = 20)
    public String getSenderphone() {
        return this.senderphone;
    }

    public void setSenderphone(String senderphone) {
        this.senderphone = senderphone;
    }

    @Column(name = "senderaddr", length = 200)
    public String getSenderaddr() {
        return this.senderaddr;
    }

    public void setSenderaddr(String senderaddr) {
        this.senderaddr = senderaddr;
    }

    @Column(name = "receivername", length = 20)
    public String getReceivername() {
        return this.receivername;
    }

    public void setReceivername(String receivername) {
        this.receivername = receivername;
    }

    @Column(name = "receiverphone", length = 20)
    public String getReceiverphone() {
        return this.receiverphone;
    }

    public void setReceiverphone(String receiverphone) {
        this.receiverphone = receiverphone;
    }

    @Column(name = "receiveraddr", length = 200)
    public String getReceiveraddr() {
        return this.receiveraddr;
    }

    public void setReceiveraddr(String receiveraddr) {
        this.receiveraddr = receiveraddr;
    }

    @Column(name = "feeitemnum")
    public Integer getFeeitemnum() {
        return this.feeitemnum;
    }

    public void setFeeitemnum(Integer feeitemnum) {
        this.feeitemnum = feeitemnum;
    }

    @Column(name = "actlweit", precision = 22, scale = 0)
    public Double getActlweit() {
        return this.actlweit;
    }

    public void setActlweit(Double actlweit) {
        this.actlweit = actlweit;
    }

    @Column(name = "vol", length = 20)
    public String getVol() {
        return this.vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }

    @Column(name = "managerCheck", length = 1)
    public String getManagerCheck() {
        return this.managerCheck;
    }

    public void setManagerCheck(String managerCheck) {
        this.managerCheck = managerCheck;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "updatetime", length = 10)
    public Date getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}