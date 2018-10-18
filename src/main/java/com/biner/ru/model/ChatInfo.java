package com.biner.ru.model;

import java.io.Serializable;
import java.util.Collection;

/**
 * 
 * @description IP信息
 * @date 2018-10-18
 * @author KangBinbin
 *
 */
public class ChatInfo implements Serializable {
	
	public ChatInfo() {
		super();
	}
	
	public ChatInfo(Collection<IpInfo> members, String msg, IpInfo from, int type) {
		this.members = members;
		this.msg = msg;
		this.from = from;
		this.type = type;
	}
	
	private static final long serialVersionUID = 1L;
	
	public static final int MSG = 0;
	
	public static final int JOIN = 1;
	
	public static final int LEAVE = 2;
	
	// 聊天室成员
    private Collection<IpInfo> members;
    // 发送的消息
    private String msg;
    // 来自谁
    private IpInfo from;
   
    private int type;

	public Collection<IpInfo> getMembers() {
		return members;
	}

	public void setMembers(Collection<IpInfo> members) {
		this.members = members;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public IpInfo getFrom() {
		return from;
	}

	public void setFrom(IpInfo from) {
		this.from = from;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
    
}