package com.biner.ru.util;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

import com.biner.ru.common.Constants;
import com.sun.mail.util.MailSSLSocketFactory;

@Component
public class EmailUtils {
	
	private final String FROM = Constants.ADMINSTRATOR_MAIL;
	
	private final String HOST_QQ = "smtp.qq.com";
	
	private final String PASSWORD_QQ = System.getenv("mail.password");
	
	public void sendEmail(String to, String title, String content) {
		// 获取系统属性
		Properties properties = System.getProperties();
		// 设置邮件服务器
		properties.setProperty("mail.smtp.host", HOST_QQ);
		properties.put("mail.smtp.auth", "true");
	    try{
	    	// 设置SSL加密
	    	MailSSLSocketFactory sf = new MailSSLSocketFactory();
	    	sf.setTrustAllHosts(true);
	    	properties.put("mail.smtp.ssl.enable", "true");
	    	properties.put("mail.smtp.ssl.socketFactory", sf);
	    	// 获取默认的 Session 对象。
	    	Session session = Session.getDefaultInstance(properties, new Authenticator(){
	    		public PasswordAuthentication getPasswordAuthentication() {
	    			return new PasswordAuthentication(FROM, PASSWORD_QQ); //发件人邮件用户名、密码
	    		}
	    	});
	    	// 创建默认的 MimeMessage 对象。
	    	MimeMessage message = new MimeMessage(session);
 
	    	// Set From: 头部头字段
	    	message.setFrom(new InternetAddress(FROM));
 
	    	// Set To: 头部头字段
	    	message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
 
	    	// Set Subject: 头字段
	    	message.setSubject("This is the Subject Line!");
 
	    	// 发送 HTML 消息, 可以插入html标签
	    	message.setContent(content, "text/html" );
 
	    	// 发送消息
	    	Transport.send(message);
	    	System.out.println("Sent message successfully....");
	    }catch (MessagingException mex) {
	    	mex.printStackTrace();
	    } catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		EmailUtils email = new EmailUtils();
		String content = "<h1>This is actual message</h1>";
		String title = "Test java send email";
		String to = "kangbain@qq.com";  
		email.sendEmail(to, title, content);
	}
}
