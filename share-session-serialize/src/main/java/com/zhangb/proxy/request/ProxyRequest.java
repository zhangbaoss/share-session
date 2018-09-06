package com.zhangb.proxy.request;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import com.zhangb.comm.SessionConstant;
import com.zhangb.proxy.session.ProxySession;
import com.zhangb.redis.JedisCache;

public class ProxyRequest extends HttpServletRequestWrapper {

	private JedisCache cache = new JedisCache();
	
	public ProxyRequest(HttpServletRequest request) {
		super(request);
	}

	@Override
	public HttpSession getSession() {
		return this.getSession(true);
	}
	
	@Override
	public HttpSession getSession(boolean create) {
		
		ProxySession session = null;
		
		String sessionId = null;
		
		//获取客户端传递过来的cookie值
		Cookie[] cookies = this.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (SessionConstant.JSESSIONID.equals(cookie.getName().toUpperCase())) {
					sessionId = cookie.getValue();
				}
			}
		}
		
		if (sessionId != null) {
			session = cache.get(sessionId);
		}
		
		if (session == null) {
			session = new ProxySession(super.getSession(create));
		} else {
			//延长session过期时间
			cache.expire(session.getId(), SessionConstant.SESSION_BROKEN_TIME);
		}
		return session;
	}
}
