package com.zhangb.redis;

import com.zhangb.serialize.SerializeUtil;

import redis.clients.jedis.Jedis;

public class JedisCache {
	
	public void set(Object key, Object value) {
		try (Jedis jedis = getJedis()) {
			jedis.set(objToBytes(key), objToBytes(value));
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Object key) {
		try (Jedis jedis = getJedis()) {
			return (T) bytesToObj(jedis.get(objToBytes(key)));
		}
	}
	
	/**
	 * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。单位(秒)
	 */
	public Long expire(Object key, int seconds) {
		try (Jedis jedis = getJedis();) {
			return jedis.expire(objToBytes(key), seconds);
		}
	}
	
	private Jedis getJedis() {
		return RedisConfiguration.getJedis();
	}
	
	private byte[] objToBytes(Object value) {
		return SerializeUtil.objToBytes(value);
	}
	
	private Object bytesToObj(byte[] value) {
		return SerializeUtil.bytesToObj(value);
	}
}
