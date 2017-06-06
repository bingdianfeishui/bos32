package com.itheima.crm.domain;

public class Customer {
	private Integer id;
	private String name;
	private String station;
	private String telephone;
	private String location;
	private Integer decidedZoneId;
	
	public Customer(Integer id, String name, String station, String telephone,
			String location, Integer decidedZoneId) {
		super();
		this.id = id;
		this.name = name;
		this.station = station;
		this.telephone = telephone;
		this.location = location;
		this.decidedZoneId = decidedZoneId;
	}
	public Customer() {
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getDecidedZoneId() {
		return decidedZoneId;
	}
	public void setDecidedZoneId(Integer decidedZoneId) {
		this.decidedZoneId = decidedZoneId;
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", station=" + station
				+ ", telephone=" + telephone + ", location=" + location
				+ ", decidedZoneId=" + decidedZoneId + "]";
	}
}
