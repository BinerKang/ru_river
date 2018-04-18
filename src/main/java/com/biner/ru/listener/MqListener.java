package com.biner.ru.listener;

import org.apache.log4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author KangBinbin
 * @date 2018年2月12日
 * @description 作为mq监听器
 */
@Component
public class MqListener {

	private Logger logger = Logger.getLogger(MqListener.class);
	
	/**
	 * 异步处理业务
	 * @author KangBinbin
	 * @date 2018年2月12日
	 * @description  
	 */
	@EventListener
	@Async
	public void listener2(MqEvent event) {
		
	}
	
	
}
