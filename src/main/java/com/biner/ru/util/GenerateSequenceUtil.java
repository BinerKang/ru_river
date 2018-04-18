package com.biner.ru.util;

import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;

public class GenerateSequenceUtil {
	private static final Logger logger = Logger.getLogger(GenerateSequenceUtil.class);

	public static synchronized String generatePrimaryKey() {
		String id = UUID.randomUUID().toString().replace("-", "");
		logger.info("本次生成的主键：" + id);
		return id;
	}
	
	public static synchronized String generateUniqueKey() {// TODO 没满32位补0
		String num = System.currentTimeMillis() + "" + new Random().nextInt(100);
		String curTime = DateUtil.format("yyyyMMdd");
		String key = curTime + String.format("%024d", Long.parseLong(num));
		System.out.println("key:" + key);
		logger.info("本次生成的唯一键：" + key);
		return key;
	}
	
	public static synchronized String generateUniqueKey(int length) {// TODO 没满32位补0
		String num = "" + new Random().nextInt(100000);
		String curTime = DateUtil.format("yyyyMMddHHmmss");
		String key = curTime + String.format("%0 " + (length - 13) + "d", Long.parseLong(num));
		logger.info("本次生成的唯一键：" + key);
		return key.replace(" ", "");
	}
	
}
