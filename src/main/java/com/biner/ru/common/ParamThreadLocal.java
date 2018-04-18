package com.biner.ru.common;

import java.util.Map;

public class ParamThreadLocal {

    //把构造函数私有，外面不能new，只能通过下面两个方法操作
    private ParamThreadLocal(){

    }

    private static final ThreadLocal<Map<String, String>> LOCAL = new ThreadLocal<Map<String, String>>(); 

    public static void set(Map<String, String> param){
        LOCAL.set(param);;
    }

    public static Map<String, String> get(){
        return LOCAL.get();
    }
    
    public static void remove() {
    	LOCAL.remove();
    }
}