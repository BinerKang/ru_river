package com.biner.ru.model;

import java.io.Serializable;

/**
 * 
 * @description  对应客户联系表
 * @date 2019-05-25
 * @author KangBinbin
 *
 */
public class Contact extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String telphone;
	
	private String call;
	// 性别(F:女,M:男)
	private String gender;
	
	private String ip;
	
	private String contactedTime;
	
	private String info;
	
	private Integer pageSize;
    
    private Integer pageNo;
    
    private String keyword;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getCall() {
		return call;
	}
	public void setCall(String call) {
		this.call = call;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getContactedTime() {
		return contactedTime;
	}
	public void setContactedTime(String contactedTime) {
		this.contactedTime = contactedTime;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
}