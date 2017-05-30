package com.itheima.bos.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Subarea entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bc_subarea")
public class Subarea implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3565156593599962728L;

    // Fields

    private String id;
    private DecidedZone decidedZone;
    private Region region;
    private String addresskey;
    private String startnum;
    private String endnum;
    private String single;
    private String position;

    // Constructors

    /** default constructor */
    public Subarea() {
    }

    /** full constructor */
    public Subarea(DecidedZone decidedZone, Region region, String addresskey,
            String startnum, String endnum, String single, String position) {
        this.decidedZone = decidedZone;
        this.region = region;
        this.addresskey = addresskey;
        this.startnum = startnum;
        this.endnum = endnum;
        this.single = single;
        this.position = position;
    }

    // Property accessors
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 32)
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decidedzone_id")
    public DecidedZone getDecidedZone() {
        return this.decidedZone;
    }

    public void setDecidedZone(DecidedZone decidedZone) {
        this.decidedZone = decidedZone;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Column(name = "addresskey", length = 100)
    public String getAddresskey() {
        return this.addresskey;
    }

    public void setAddresskey(String addresskey) {
        this.addresskey = addresskey;
    }

    @Column(name = "startnum", length = 30)
    public String getStartnum() {
        return this.startnum;
    }

    public void setStartnum(String startnum) {
        this.startnum = startnum;
    }

    @Column(name = "endnum", length = 30)
    public String getEndnum() {
        return this.endnum;
    }

    public void setEndnum(String endnum) {
        this.endnum = endnum;
    }

    @Column(name = "single", length = 1)
    public String getSingle() {
        return this.single;
    }

    public void setSingle(String single) {
        this.single = single;
    }

    @Column(name = "position")
    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}