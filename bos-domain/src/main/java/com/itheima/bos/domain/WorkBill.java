package com.itheima.bos.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 工单
 * WorkBill entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "qp_workbill")
public class WorkBill implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    // Fields
    private Integer id;
    private NoticeBill noticeBill;
    private Staff staff;
    private String type = TYPE_1;
    private String pickstate = PICKSTATE_NO;
    private Timestamp buildtime = new Timestamp(System.currentTimeMillis());
    private Integer attachbilltimes = 0;
    private String remark;

    public static final String TYPE_1="新单";
    public static final String TYPE_2="追单";
    public static final String TYPE_3="改单";
    public static final String TYPE_4="删单";
    
    public static final String PICKSTATE_NO="未取件";
    public static final String PICKSTATE_RUNNING="取件中";
    public static final String PICKSTATE_YES="已取件";
    
    
    // Constructors

    /** default constructor */
    public WorkBill() {
    }

    /** minimal constructor */
    public WorkBill(Timestamp buildtime) {
        this.buildtime = buildtime;
    }

    /** full constructor */
    public WorkBill(NoticeBill noticeBill, Staff staff, String type,
            String pickstate, Timestamp buildtime, Integer attachbilltimes,
            String remark) {
        this.noticeBill = noticeBill;
        this.staff = staff;
        this.type = type;
        this.pickstate = pickstate;
        this.buildtime = buildtime;
        this.attachbilltimes = attachbilltimes;
        this.remark = remark;
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
    @JoinColumn(name = "noticebill_id")
    public NoticeBill getNoticeBill() {
        return this.noticeBill;
    }

    public void setNoticeBill(NoticeBill noticeBill) {
        this.noticeBill = noticeBill;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    public Staff getStaff() {
        return this.staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    /**
     * 工单类型：新、追、改、删
     * 默认值：新单
     * @return
     */
    @Column(name = "type", length = 20)
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 取件状态： 未取件、取件中、已取件
     * 默认值：未取件
     * @return
     */
    @Column(name = "pickstate", length = 20)
    public String getPickstate() {
        return this.pickstate;
    }

    public void setPickstate(String pickstate) {
        this.pickstate = pickstate;
    }

    @Column(name = "buildtime", nullable = false, length = 19)
    public Timestamp getBuildtime() {
        return this.buildtime;
    }

    public void setBuildtime(Timestamp buildtime) {
        this.buildtime = buildtime;
    }

    @Column(name = "attachbilltimes")
    public Integer getAttachbilltimes() {
        return this.attachbilltimes;
    }

    public void setAttachbilltimes(Integer attachbilltimes) {
        this.attachbilltimes = attachbilltimes;
    }

    @Column(name = "remark")
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}