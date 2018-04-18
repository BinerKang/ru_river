package com.biner.ru.common;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

@Service
public class Customer {
	
	private Logger logger = Logger.getLogger(Customer.class);
	
    private final static String QUEUE_NAME = "queue";
    
    @Autowired
    private com.biner.ru.listener.MqPublisher publisher;
    
    public static void main(String[] args) throws IOException, TimeoutException {
    	Customer cu = new Customer();
    	cu.activeMQCustomer();
    }
    
    /**
     * @author KangBinbin
     * @date 2018年2月12日
     * @description  激活mq消费者
     */
    @PostConstruct
    public void activeMQCustomer() {
//    	Thread th = new Thread(new activeMQRunnable());
//    	th.start();
    }
    
    private class activeMQRunnable implements Runnable {

		@Override
		public void run() {
			try {
				logger.info("Active MQ Customer begin!:::HostIP:"+Constants.MQ_HOST+";Username:"+Constants.MQ_USERNAME+";Pass:"+Constants.MQ_PASSWORD+";Port:"+Constants.MQ_PORT);
				activeMQ(Constants.MQ_HOST, Constants.MQ_USERNAME, Constants.MQ_PASSWORD, Constants.MQ_PORT);
			} catch (Exception e) {
				logger.error("Active MQ Customer failed!!", e);
			}
		}
    }
    
    public void activeMQ(String host, String userName, String password, int port) throws IOException, TimeoutException {
    	// 创建连接工厂
    	ConnectionFactory factory = new ConnectionFactory();
    	//设置RabbitMQ地址
    	factory.setHost(host);
    	factory.setUsername(userName);
    	factory.setPassword(password);
    	factory.setPort(port);
    	
    	//创建一个新的连接
    	Connection connection = factory.newConnection();
    	//创建一个通道
    	Channel channel = connection.createChannel();
    	//声明要关注的队列
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		logger.info("Customer [" + QUEUE_NAME + "] Waiting Received messages");
    	
    	//DefaultConsumer类实现了Consumer接口，通过传入一个频道，
    	// 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
    	Consumer consumer = new DefaultConsumer(channel) {
    		@Override
    		public void handleDelivery(String consumerTag, Envelope envelope,
    				AMQP.BasicProperties properties, byte[] body)
    						throws IOException {
    			String message = new String(body, "UTF-8");
    			logger.info("Customer [" + QUEUE_NAME + "] Received '" + message + "'");
    			// 发布器发布事件供监听器处理
    			publisher.pushListener(message);
    		}
    	};
    	//自动回复队列应答 -- RabbitMQ中的消息确认机制
		channel.basicConsume(QUEUE_NAME, true, consumer);
		logger.info("Active MQ Customer Successed!!");
    }
}