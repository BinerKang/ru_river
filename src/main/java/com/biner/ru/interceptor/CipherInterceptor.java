package com.biner.ru.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.biner.ru.common.Constants;
import com.biner.ru.common.MapResult;
import com.biner.ru.common.ParamThreadLocal;
import com.biner.ru.util.CodeMsg;
import com.biner.ru.util.DES;
import com.biner.ru.util.ResponseUtil;

public class CipherInterceptor implements HandlerInterceptor {

	private Logger logger = Logger.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		Map<String, String> returnMap = new HashMap<String, String>();
		String value = request.getParameter("data");
		if (Constants.IS_ENCRYPT) {// 如果加密
			try {
				value = DES.decryptDES(value, Constants.DES_KEY);
				// 打印解密后的入参
				logger.info("Input param after decrypt : key[data]::value[" + value + "]");
				returnMap = JSON.parseObject(value, new TypeReference<Map<String, String>>() {});
				
			} catch (Exception e) {
				MapResult result=new MapResult();
		    	result.setCode(CodeMsg.CIPHER_WRONG);
		        result.setMsg("解密异常");
				ResponseUtil.outputJSONResponseEncrypt(request, response, result);
				return false;
			}
		} else {
			try {
				//logger.info(request.getRequestURI() + "" + value);
				returnMap = JSON.parseObject(value, new TypeReference<Map<String, String>>() {});
			} catch (Exception e) {
				MapResult result=new MapResult();
		    	result.setCode(CodeMsg.SERVER_EXCEPTION);
		        result.setMsg("参数解析异常");
				ResponseUtil.outputJSONResponseEncrypt(request, response, result);
				return false;
			}
		}
		// 打印处理后的参数
		if(returnMap == null) {
			returnMap = new HashMap<String, String>(); 
		}
		
		StringBuffer buff = new StringBuffer();
		for (Map.Entry<String, String> entry : returnMap.entrySet()){  
			String val = entry.getValue();
			if(val.length() >200) {
				val = val.substring(0, 200);
			}
			buff.append(" "+ entry.getKey()+"=" + val + " ");
		}  
		logger.info(request.getRequestURI() + "" + buff);
		
		ParamThreadLocal.set(returnMap);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 清空本地线程中的参数对象数据
		ParamThreadLocal.remove();
	}

}