package com.itheima.crm.domain;

public class Customer {
	private Integer id;
	private String name;
	private String station;
	private String telephone;
	private String address;
	private Integer decidedZoneId;
	
	public Customer(Integer id, String name, String station, String telephone,
			String address, Integer decidedZoneId) {
		super();
		this.id = id;
		this.name = name;
		this.station = station;
		this.telephone = telephone;
		this.address = address;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
				+ ", telephone=" + telephone + ", address=" + address
				+ ", decidedZoneId=" + decidedZoneId + "]";
	}
}
