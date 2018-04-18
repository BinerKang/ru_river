package com.biner.ru.common;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 消息生成者
 */
public class Producer {
	
	public final Logger logger = Logger.getLogger(getClass());
	
	private final static String QUEUE_NAME = "queue";
	
    public void sendMQ (String msg, String appType) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ相关信息
        factory.setHost(Constants.MQ_HOST);
		factory.setUsername(Constants.MQ_USERNAME);
		factory.setPassword(Constants.MQ_PASSWORD);
		factory.setPort(Constants.MQ_PORT);
        //创建一个新的连接
        Connection connection = factory.newConnection();
        //创建一个通道
        Channel channel = connection.createChannel();
        //  声明一个队列        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
      
        //发送消息到队列中
        // 发送type11
        logger.info("Send MSG begin:" + msg);
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes("UTF-8"));
        logger.info("Send MSG end:");
        
        //关闭通道和连接
        channel.close();
        connection.close();
    }
    
    
    public static void batchProductPa() throws IOException, TimeoutException {
    	Producer pr = new Producer();
    	String msg = getMsg("pa", "CUST130125198601155517", "123456", "11");
		pr.sendMQ(msg, "bu");
		msg = getMsg("pa", "CUST130125198601155517", "123456", "12");
		pr.sendMQ(msg, "bu");
		msg = getMsg("pa", "CUST130125198601155517", "123456", "13");
		pr.sendMQ(msg, "bu");
		msg = getMsg("pa", "CUST130125198601155517", "123456", "21");
		pr.sendMQ(msg, "bu");
		msg = getMsg("pa", "CUST130125198601155517", "123456", "22");
		pr.sendMQ(msg, "bu");
		msg = getMsg("pa", "CUST130125198601155517", "123456", "31");
		pr.sendMQ(msg, "bu");
		msg = getMsg("pa", "CUST130125198601155517", "123456", "32");
		pr.sendMQ(msg, "bu");
		msg = getMsg("pa", "CUST130125198601155517", "123456", "41");
		pr.sendMQ(msg, "bu");
		msg = getMsg("pa", "CUST130125198601155517", "123456", "42");
		pr.sendMQ(msg, "bu");
		msg = getMsg("pa", "CUST130125198601155517", "123456", "43");
		pr.sendMQ(msg, "bu");
		msg = getMsg("pa", "CUST130125198601155517", "123456", "51");
		pr.sendMQ(msg, "bu");
		msg = getMsg("pa", "CUST130125198601155517", "123456", "61");
		pr.sendMQ(msg, "bu");
		msg = getMsg("pa", "CUST130125198601155517", "123456", "61");
		pr.sendMQ(msg, "bu");
		msg = getMsg("pa", "CUST130125198601155517", "123456", "63");
		pr.sendMQ(msg, "bu");
		msg = getMsg("pa", "CUST130125198601155517", "123456", "64");
		pr.sendMQ(msg, "bu");
		msg = getMsg("pa", "CUST130125198601155517", "123456", "71");
		pr.sendMQ(msg, "bu");
		msg = getMsg("pa", "CUST130125198601155517", "123456", "81");
		pr.sendMQ(msg, "bu");
		msg = getMsg("pa", "CUST130125198601155517", "123456", "82");
		pr.sendMQ(msg, "bu");
		msg = getMsg("pa", "CUST130125198601155517", "123456", "91");
		pr.sendMQ(msg, "bu");
		msg = getMsg("pa", "CUST130125198601155517", "123456", "92");
		pr.sendMQ(msg, "bu");
		msg = getMsg("pa", "CUST130125198601155517", "123456", "93");
		pr.sendMQ(msg, "bu");
		msg = getMsg("pa", "CUST130125198601155517", "123456", "101");
		pr.sendMQ(msg, "bu");
    }
    
    public static void batchProductBU() throws IOException, TimeoutException {
    	Producer pr = new Producer();
    	String msg = getMsg("bu", "CUST130125198601155517", "123456", "11");
		pr.sendMQ(msg, "bu");
		msg = getMsg("bu", "CUST130125198601155517", "123456", "21");
		pr.sendMQ(msg, "bu");
		msg = getMsg("bu", "CUST130125198601155517", "123456", "31");
		pr.sendMQ(msg, "bu");
		msg = getMsg("bu", "CUST130125198601155517", "123456", "32");
		pr.sendMQ(msg, "bu");
		msg = getMsg("bu", "CUST130125198601155517", "123456", "33");
		pr.sendMQ(msg, "bu");
		msg = getMsg("bu", "CUST130125198601155517", "123456", "41");
		pr.sendMQ(msg, "bu");
		msg = getMsg("bu", "CUST130125198601155517", "123456", "42");
		pr.sendMQ(msg, "bu");
		msg = getMsg("bu", "CUST130125198601155517", "123456", "51");
		pr.sendMQ(msg, "bu");
		msg = getMsg("bu", "CUST130125198601155517", "123456", "52");
		pr.sendMQ(msg, "bu");
		msg = getMsg("bu", "CUST130125198601155517", "123456", "53");
		pr.sendMQ(msg, "bu");
		msg = getMsg("bu", "CUST130125198601155517", "123456", "54");
		pr.sendMQ(msg, "bu");
		msg = getMsg("bu", "CUST130125198601155517", "123456", "61");
		pr.sendMQ(msg, "bu");
		msg = getMsg("bu", "CUST130125198601155517", "123456", "62");
		pr.sendMQ(msg, "bu");
		msg = getMsg("bu", "CUST130125198601155517", "123456", "71");
		pr.sendMQ(msg, "bu");
		msg = getMsg("bu", "CUST130125198601155517", "123456", "72");
		pr.sendMQ(msg, "bu");
    }
    public static void main(String[] args) throws IOException, TimeoutException {
    	Producer pr = new Producer();
    	String msg = getMsg("pa", "CUST1805909452890", "123456", "42");
		pr.sendMQ(msg, "bu");
//		msg = getMsg("bu", "CUST1806710150255", "123456", "42");
//		pr.sendMQ(msg, "bu");
//    	batchProductBU();
//    	batchProductPa();
	}
    
    public static String getMsg(String source, String initCustId, String orderNo, String type) {
		JSONObject js = new JSONObject();
		js.put("source", source);
		js.put("initCustId", initCustId);
		js.put("orderNo", orderNo);
		js.put("type", type);
		if ("bu".equals(source)) {
			js.put("contractNo", "FAKENO000011");
			js.put("periods", "5");
			js.put("totalMoney", "456");
			js.put("reason", "长的太帅");
			js.put("periodsMoney", "300");
			js.put("overdueMoney", "32");
		}
		if ("bu".equals(source) && "11".equals(type)) {
			JSONArray list = new JSONArray();
			JSONObject ob1 = new JSONObject();
			ob1.put("count", "100");
			ob1.put("vehicleName", "擎天柱");
			list.add(ob1);
			JSONObject ob2 = new JSONObject();
			ob2.put("count", "30");
			ob2.put("vehicleName", "霸天虎");
			list.add(ob2);
			js.put("carList", list);
			js.put("firstMoney", "123");
			js.put("loanMoney", "234");
			js.put("months", "24");
			js.put("rate", "32%");
		}
		js.put("money", "300.08");
		js.put("payedMoney", "209.08");
		js.put("leftMoney", "100.02");
		js.put("buyCode", "DDBW180114511");
		js.put("date", "2017-02-28");
		js.put("payDate", "2017-03-15");
		js.put("bank", "中国农业银行");
		js.put("account", "666555444333222111");
		String msg = js.toJSONString();
		return msg;
    }
}