package com.biner.ru.model;

import java.io.Serializable;

/**
 * 
 * @description  对应客户表
 * @date 2019-05-24
 * @author KangBinbin
 *
 */
public class Customer extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String telphone;
	
	private String name;
	// 性别(F:女,M:男)
	private String gender;
	// 小区名
	private String estate;
	// 门牌
	private String address;
	// 渲染图链接
	private String url;
	// 阶段
	private Long phase;
	// 客户信息
	private String info;
	
	private Integer pageSize;
    
    private Integer pageNo;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEstate() {
		return estate;
	}
	public void setEstate(String estate) {
		this.estate = estate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getPhase() {
		return phase;
	}
	public void setPhase(Long phase) {
		this.phase = phase;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
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
	
}