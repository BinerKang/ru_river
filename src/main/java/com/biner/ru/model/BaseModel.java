package com.biner.ru.model;

public class BaseModel {

	private Long createdBy;
	private String createdTime;
	private Long upTimedBy;
	private String upTimedTime;
	
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public Long getUpTimedBy() {
		return upTimedBy;
	}
	public void setUpTimedBy(Long upTimedBy) {
		this.upTimedBy = upTimedBy;
	}
	public String getUpTimedTime() {
		return upTimedTime;
	}
	public void setUpTimedTime(String upTimedTime) {
		this.upTimedTime = upTimedTime;
	}
	
}
