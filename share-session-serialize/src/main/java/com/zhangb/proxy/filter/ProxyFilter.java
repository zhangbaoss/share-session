package com.zhangb.proxy.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.zhangb.proxy.request.ProxyRequest;
import com.zhangb.redis.RedisConfiguration;

public class ProxyFilter implements Filter {

	/**
	 * 初始化参数,在创建Filter时自动调用.当我们需要设置初始化参数是,可以写到该方法中.
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		RedisConfiguration.initRedisConfiguration();
	}

	/**
	 * 拦截到要执行的请求时,doFilter就会执行.这里面写我们对请求和响应的处理
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		ProxyRequest proxyRequest = new ProxyRequest(req);
		chain.doFilter(proxyRequest, response);
	}

	/**
	 * 在销毁Filter时自动调用
	 */
	@Override
	public void destroy() {
		
	}

}
