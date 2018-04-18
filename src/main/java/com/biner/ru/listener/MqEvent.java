package com.biner.ru.listener;

import org.springframework.context.ApplicationEvent;

/**
 * @author KangBinbin
 * @date 2018年2月12日
 * @description 作为mq收到消息后监听器 事件
 */
public class MqEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private String msg;
	
	public MqEvent(Object source, String msg) {
		super(source);
		// TODO Auto-generated constructor stub
		this.setMsg(msg);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
