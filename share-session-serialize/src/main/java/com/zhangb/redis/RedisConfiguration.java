package com.zhangb.redis;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisConfiguration {

	private static JedisPool jedisPool;


	/**
	 * 
	 * @Description: 初始化redis连接池
	 * @return void
	 * @date 2018年8月30日 上午11:27:10  
	 * @author zhangb
	 * @throws
	 */
	public static void initRedisConfiguration(){

		Configuration redisConfig;
		try {
			redisConfig = new PropertiesConfiguration("redis.properties");

			//redis配置
			String host = redisConfig.getString("redis.host");
			String password = redisConfig.getString("redis.password");
			int port = redisConfig.getInt("redis.port");
			int timeout = redisConfig.getInt("redis.timeout");
			int database = redisConfig.getInt("redis.database");
			String clientName = redisConfig.getString("redis.clientName");
			//redis连接池配置
			int maxTotal = redisConfig.getInt("redis.maxTotal");
			int maxIdle = redisConfig.getInt("redis.maxIdle");
			long maxWaitMillis = redisConfig.getLong("redis.maxWaitMillis");
			boolean testOnBorrow = redisConfig.getBoolean("redis.testOnBorrow");

			//池基本配置
			JedisPoolConfig config = new JedisPoolConfig();
			//控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
			//如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
			config.setMaxTotal(maxTotal);
			//控制一个pool最多有多少个状态为idle(空闲的)的jedis实例
			config.setMaxIdle(maxIdle);
			//表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
			config.setMaxWaitMillis(maxWaitMillis);
			//在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
			config.setTestOnBorrow(testOnBorrow);

			jedisPool = new JedisPool(config, host, port, timeout, password, database, clientName);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Description: 获取jedis连接
	 * @return Jedis
	 * @date 2018年8月30日 上午11:26:33  
	 * @author zhangb
	 * @throws
	 */
	public static Jedis getJedis() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
		} catch (Exception e) {
			if (JedisConnectionException.class.equals(e.getClass())) {
				System.err.println("#########请检查redis配置是否正确!!!!!!");
				System.exit(1);
			} else {
				e.printStackTrace();
			}
		}
		return jedis;
	}

}
