package com.biner.ru.model;

import java.io.Serializable;

/**
 * 
 * @description IP信息
 * @date 2018-09-28
 * @author KangBinbin
 *
 */
public class IpInfo implements Serializable {
	
	public IpInfo() {
		super();
	}
	
	public IpInfo(String ip, String country, String subdivision, String city, String postal) {
		this.ip = ip;
		this.country = country;
		this.subdivision = subdivision;
		this.city = city;
		this.postal = postal;
	}
	
	private static final long serialVersionUID = 1L;
	
    private String ip;
    
    private String country;
    
    private String subdivision;
   
    private String city;
    //邮编
    private String postal;
    
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getSubdivision() {
		return subdivision;
	}
	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostal() {
		return postal;
	}
	public void setPostal(String postal) {
		this.postal = postal;
	}

	@Override
	public String toString() {
		return "IpInfo [ip=" + ip + ", country=" + country + ", subdivision=" + subdivision + ", city=" + city
				+ ", postal=" + postal + "]";
	}
    
}