package com.biner.ru.common;

import java.io.Serializable;

import org.apache.commons.collections.MapUtils;

import com.biner.ru.util.CodeMsg;

public class MapResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private int code = CodeMsg.SUCCESS;
	private String msg = "操作成功";
	private Object data = MapUtils.EMPTY_MAP;
	
	public MapResult(int code, String msg, Object data){
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public MapResult(int code, String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public MapResult(Object data){
		this.msg = "查询成功";
		this.data = data;
	}
	
	public MapResult(int code){
		this.code = code;
		this.msg = "操作失败";
	}
	
	public MapResult(String msg){
		this.code = 0;
		this.msg = msg;
	}
	
	public MapResult(){
		
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "MapResult [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}
	
}
