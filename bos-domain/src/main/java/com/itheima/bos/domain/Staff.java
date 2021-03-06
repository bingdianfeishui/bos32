package com.itheima.bos.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Staff entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bc_staff")
@NamedQuery(name = "staff.restoreOrDelete", query = "UPDATE Staff SET deltag=:op WHERE id in (:ids)")
public class Staff implements java.io.Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // Fields

    private Integer id = 0;
    private String name;
    private String telephone;
    private String haspda = "0";
    private String deltag = "0";
    private String station;
    private String standard;
    private Set<DecidedZone> decidedZones = new HashSet<DecidedZone>(0);

    // Constructors

    /** default constructor */
    public Staff() {
    }

    /** minimal constructor */
    public Staff(Integer id) {
        this.id = id;
    }

    /** full constructor */
    public Staff(Integer id, String name, String telephone, String haspda,
            String deltag, String station, String standard,
            Set<DecidedZone> decidedZones) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.haspda = haspda;
        this.deltag = deltag;
        this.station = station;
        this.standard = standard;
        this.decidedZones = decidedZones;
    }

    @Transient
    public String getDesc() {
        return this.name + "(" + this.telephone + " " + this.station + ")";
    }

    // Property accessors
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", length = 20)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "telephone", length = 20)
    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "haspda", length = 1)
    public String getHaspda() {
        return this.haspda;
    }

    public void setHaspda(String haspda) {
        this.haspda = haspda;
    }

    @Column(name = "deltag", length = 1)
    public String getDeltag() {
        return this.deltag;
    }

    public void setDeltag(String deltag) {
        this.deltag = deltag;
    }

    @Column(name = "station", length = 40)
    public String getStation() {
        return this.station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @Column(name = "standard", length = 100)
    public String getStandard() {
        return this.standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "staff")
    public Set<DecidedZone> getDecidedZones() {
        return this.decidedZones;
    }

    public void setDecidedZones(Set<DecidedZone> decidedZones) {
        this.decidedZones = decidedZones;
    }

}