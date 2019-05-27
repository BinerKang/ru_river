package com.biner.ru.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.biner.ru.common.Constants;
import com.biner.ru.common.MapResult;
import com.biner.ru.common.ParamThreadLocal;
import com.biner.ru.common.RedisKeyConstants;
import com.biner.ru.util.CodeMsg;
import com.biner.ru.util.RedisClient;
import com.biner.ru.util.ResponseUtil;  
  
public class SessionInterceptor implements HandlerInterceptor {  

	@Override  
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {  
    	
    	String sessionId = request.getSession().getId();
    	
		// 从redis里查询有无该token
    	String tokenKey = RedisKeyConstants.getTokenRedisKey(sessionId);
    	String value = RedisClient.getString(tokenKey);
    	if (value == null) {
    		String msg = "session失效";
    		ResponseUtil.outputJSONResponseEncrypt(request, response, new MapResult(CodeMsg.TOKEN_EXPIRED, msg));
			return false;
    	} else {// 更新失效时间
    		ParamThreadLocal.get().put("userInfo", value);
    		RedisClient.setExpire(tokenKey, Constants.WEB_EXPIRE_MINS * 60);
    		return true;
    	}
    }  
      
    @Override  
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {  
    
    }  
  
   
    @Override  
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {  
          
    }  
}  