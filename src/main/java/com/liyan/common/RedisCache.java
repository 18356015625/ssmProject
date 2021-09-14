package com.liyan.common;

import javax.annotation.Resource;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisCache {
	
	@Resource
	private JedisPool jedisPool;

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
	
	/**
	 * 从redis查询数据
	 * @param redisKey
	 * @return
	 */
	public Object getDataFromRedis(String redisKey) {
		Jedis jedis = jedisPool.getResource();
		byte[] result = jedis.get(redisKey.getBytes());
		if(null == result) {
			return null;
		}
		//序列化
		return SerializeUtil.unSerialize(result);
	}
	
	/**
	 * 将数据缓存到redis中
	 * @param redisKey
	 * @param object
	 */
	public void setDataToRedis(String redisKey,Object object) {
		byte[] bytes = SerializeUtil.serialize(object);
		Jedis jedis = jedisPool.getResource();
		//判断是否存在key
		Boolean exists = jedis.exists(redisKey);
		byte[] success  = null;
		if(exists){
			//直接set值
			success = jedis.getSet(redisKey.getBytes(),bytes);
			jedis.expire(redisKey.getBytes(), 60*60*24);
		}else {
			success = jedis.set(redisKey.getBytes(), bytes).getBytes();
			jedis.expire(redisKey.getBytes(), 60*60*24);
		}
		if("OK".getBytes().equals(success)) {
			System.out.println("数据成功保存到redis...");
		}
	}
	
	/**
	 * 从redis中删除key
	 * @param redisKey
	 */
	public void removeKey(String redisKey) {
		Jedis jedis = jedisPool.getResource();
		jedis.del(redisKey);
	}
	
}
