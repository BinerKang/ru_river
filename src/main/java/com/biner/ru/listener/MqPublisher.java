package com.biner.ru.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author KangBinbin
 * @date 2018年2月12日
 * @description 作为mq监听器发布事件
 */
@Component
public class MqPublisher {
	
	@Autowired
    private ApplicationContext applicationContext;

    // 事件发布方法
    public void pushListener(String msg) {
        applicationContext.publishEvent(new MqEvent(this, msg));
    }

}
