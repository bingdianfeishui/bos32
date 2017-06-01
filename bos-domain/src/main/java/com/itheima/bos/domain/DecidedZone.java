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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * DecidedZone entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bc_decidedzone")
public class DecidedZone implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Fields

	private Integer id;
	private Staff staff;
	private String name;
	private Set<Subarea> subareas = new HashSet<Subarea>(0);

	// Constructors

	/** default constructor */
	public DecidedZone() {
	}

	/** minimal constructor */
	public DecidedZone(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public DecidedZone(Integer id, Staff staff, String name,
			Set<Subarea> subareas) {
		this.id = id;
		this.staff = staff;
		this.name = name;
		this.subareas = subareas;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "staff_id")
	public Staff getStaff() {
		return this.staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	@Column(name = "name", length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "decidedZone")
	public Set<Subarea> getSubareas() {
		return this.subareas;
	}

	public void setSubareas(Set<Subarea> subareas) {
		this.subareas = subareas;
	}

}