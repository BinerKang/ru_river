package com.biner.ru.model;

import java.io.Serializable;

/**
 * 
 * @description  对应用户信息表
 * @date 2017-12-21
 * @author KangBinbin
 *
 */
public class User extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	// 真实姓名
    private String userRealName;
    // 昵称
    private String userNickName;
    // 手机号
    private String telphone;
    // 密码
    private String password;
    // 头像
    private String userHeadPhoto;
    // 性别
    private String gender;
    //当前地址
    private String address;
    // 乘用车系统类型
    private String osTypePa;
    // 商用车系统类型
    private String osTypeBu;
    // 乘用车设备ID（推消息用）
    private String registrationIdPa;
    // 商用车设备ID（推消息用）
    private String registrationIdBu;
    // token
    private String token;
    // 身份证号
    private String idCardNumber;
    // 民族
    private String nation;
    // 身份证地址
    private String idCardAddress;
    // 签发机构
    private String signOrganization;
    // 身份证有效期限
    private String expiryDate;
    // 出生日期
    private String birth;
    // 核心系统乘用车用户id
    private String paUserId;
    // 核心系统商用车用户id
    private String buUserId;
    // app类型  pa--乘用车;bu--商用车
    private String appType;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserRealName() {
		return userRealName;
	}
	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserHeadPhoto() {
		return userHeadPhoto;
	}
	public void setUserHeadPhoto(String userHeadPhoto) {
		this.userHeadPhoto = userHeadPhoto;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getOsTypePa() {
		return osTypePa;
	}
	public void setOsTypePa(String osTypePa) {
		this.osTypePa = osTypePa;
	}
	public String getOsTypeBu() {
		return osTypeBu;
	}
	public void setOsTypeBu(String osTypeBu) {
		this.osTypeBu = osTypeBu;
	}
	public String getRegistrationIdPa() {
		return registrationIdPa;
	}
	public void setRegistrationIdPa(String registrationIdPa) {
		this.registrationIdPa = registrationIdPa;
	}
	public String getRegistrationIdBu() {
		return registrationIdBu;
	}
	public void setRegistrationIdBu(String registrationIdBu) {
		this.registrationIdBu = registrationIdBu;
	}
	public String getIdCardNumber() {
		return idCardNumber;
	}
	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getIdCardAddress() {
		return idCardAddress;
	}
	public void setIdCardAddress(String idCardAddress) {
		this.idCardAddress = idCardAddress;
	}
	public String getSignOrganization() {
		return signOrganization;
	}
	public void setSignOrganization(String signOrganization) {
		this.signOrganization = signOrganization;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getPaUserId() {
		return paUserId;
	}
	public void setPaUserId(String paUserId) {
		this.paUserId = paUserId;
	}
	public String getBuUserId() {
		return buUserId;
	}
	public void setBuUserId(String buUserId) {
		this.buUserId = buUserId;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userRealName=" + userRealName + ", userNickName=" + userNickName + ", telphone="
				+ telphone + ", password=" + password + ", userHeadPhoto=" + userHeadPhoto + ", gender=" + gender
				+ ", address=" + address + ", osTypePa=" + osTypePa + ", osTypeBu=" + osTypeBu + ", registrationIdPa="
				+ registrationIdPa + ", registrationIdBu=" + registrationIdBu + ", token=" + token + ", idCardNumber="
				+ idCardNumber + ", nation=" + nation + ", idCardAddress=" + idCardAddress + ", signOrganization="
				+ signOrganization + ", expiryDate=" + expiryDate + ", birth=" + birth + ", paUserId=" + paUserId
				+ ", buUserId=" + buUserId + "]";
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	
}