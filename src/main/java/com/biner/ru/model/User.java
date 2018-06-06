package com.biner.ru.model;

import java.io.Serializable;

/**
 * 
 * @description  对应用户信息表
 * @date 2018-06-04
 * @author KangBinbin
 *
 */
public class User extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	// 真实姓名
    private String username;
    // 密码
    private String password;
    // 邮箱
    private String mail;
    // 是否验证
    private Boolean validate;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public Boolean getValidate() {
		return validate;
	}
	public void setValidate(Boolean validate) {
		this.validate = validate;
	}
	
}