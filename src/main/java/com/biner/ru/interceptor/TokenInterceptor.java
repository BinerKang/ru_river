package com.biner.ru.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.biner.ru.common.Constants;
import com.biner.ru.common.MapResult;
import com.biner.ru.common.ParamThreadLocal;
import com.biner.ru.common.RedisKeyConstants;
import com.biner.ru.util.CodeMsg;
import com.biner.ru.util.RedisClient;
import com.biner.ru.util.ResponseUtil;  
  
public class TokenInterceptor implements HandlerInterceptor {  

	private static long TOKEN_TIME = Constants.TOKEN_EXPIRE_DAYS * 24 * 60 * 60 * 1000L;

	@Override  
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {  
    	
    	
	    	String token = ParamThreadLocal.get().get("token");
	    	
	    	if(StringUtils.isNotEmpty(token)){
	    		// 从redis里查询有无该token
	        	String tokenKey = RedisKeyConstants.getTokenRedisKey(token);
	        	String value = RedisClient.getString(tokenKey);
	        	if (value == null) {
	        		String msg = "你的账户在另一台设备上登录，如非本人操作，你的密码可能泄露，请尽快修改密码！";
	        		String[] tokens = token.split("_");
	        		if (tokens.length == 1) {
	        			msg = "非法的token";
	        		} else {
	        			try {
							long time = Long.parseLong(tokens[1]) + TOKEN_TIME;
							if (time < System.currentTimeMillis()) {
								msg = "您长时间未登录，请重新登录";
							}
						} catch (Exception e) {
							msg = "非法的token";
						}
	        		}
	        		ResponseUtil.outputJSONResponseEncrypt(request, response, new MapResult(CodeMsg.TOKEN_EXPIRED, msg));
	    			return false;
	        	} else {
	        		return true;
	        	}
	    	}
	    	
	    	ResponseUtil.outputJSONResponseEncrypt(request, response, new MapResult(CodeMsg.FAIL, "token为空"));
	    	return false;
    }  
      
    
    @Override  
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {  
    
    }  
  
   
    @Override  
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {  
          
    }  
}  