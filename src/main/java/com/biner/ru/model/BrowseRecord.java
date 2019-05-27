package com.biner.ru.model;

import java.io.Serializable;

/**
 * 
 * @description  对应短信点击记录表
 * @date 2019-05-25
 * @author KangBinbin
 *
 */
public class BrowseRecord extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String telphone;
	
	private String ip;
	
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
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}