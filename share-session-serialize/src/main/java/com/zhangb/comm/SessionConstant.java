package com.zhangb.comm;

public interface SessionConstant {

	/**请求的Cookie中是否带有sessionId*/
	String JSESSIONID = "JSESSIONID";
	
	/**session在redis中过期时间*/
	Integer SESSION_BROKEN_TIME = 30 * 60;
}
