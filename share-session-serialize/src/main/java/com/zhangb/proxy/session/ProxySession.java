package com.zhangb.proxy.session;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import com.zhangb.comm.SessionConstant;
import com.zhangb.redis.JedisCache;

@SuppressWarnings("deprecation")
public class ProxySession implements HttpSession, Serializable {

	private static final long serialVersionUID = 1L;

	private HttpSession session;
	
	private Map<String, Object> attrs = new HashMap<String, Object>();
	
	private JedisCache cache = new JedisCache();
	
	public ProxySession() {
		super();
	}

	public ProxySession(HttpSession session) {
		super();
		this.session = session;
	}

	@Override
	public long getCreationTime() {
		return session.getCreationTime();
	}

	@Override
	public String getId() {
		return session.getId();
	}

	@Override
	public long getLastAccessedTime() {
		return session.getLastAccessedTime();
	}

	@Override
	public ServletContext getServletContext() {
		return session.getServletContext();
	}

	@Override
	public void setMaxInactiveInterval(int interval) {
		session.setMaxInactiveInterval(interval);
	}

	@Override
	public int getMaxInactiveInterval() {
		return session.getMaxInactiveInterval();
	}

	@Override
	public HttpSessionContext getSessionContext() {
		return session.getSessionContext();
	}

	@Override
	public Object getAttribute(String name) {
		ProxySession session = cache.get(getId());
		if (session != null) {
			attrs = session.attrs;
		}
		return attrs.get(name);
	}

	@Override
	public Object getValue(String name) {
		return session.getValue(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return session.getAttributeNames();
	}

	@Override
	public String[] getValueNames() {
		return session.getValueNames();
	}

	@Override
	public void setAttribute(String name, Object value) {
		attrs.put(name, value);
		cache.set(getId(), this);
		//设置过期时间
		cache.expire(getId(), SessionConstant.SESSION_BROKEN_TIME);
	}

	@Override
	public void putValue(String name, Object value) {
		session.putValue(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		session.removeAttribute(name);
	}

	@Override
	public void removeValue(String name) {
		session.removeValue(name);
	}

	@Override
	public void invalidate() {
		session.invalidate();
	}

	@Override
	public boolean isNew() {
		return session.isNew();
	}

}
