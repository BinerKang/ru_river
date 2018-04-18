package com.biner.ru.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.biner.ru.common.Constants;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

public class RedisClient {
	
	private static volatile JedisPool pool = null;
	
	private static synchronized JedisPool getJedisPool() {
		if (pool == null) {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxIdle(Constants.maxIdle);
			config.setMaxTotal(Constants.maxTotal);
			config.setMinIdle(Constants.minIdle);
			config.setTestOnBorrow(Constants.testOnBorrow);
			JedisPool newPool = new JedisPool(config, Constants.jedisIp, Constants.jedisPort, Constants.jedisTimeout, Constants.jedisPassword);
			pool = newPool;
		}
		return pool;
	}

	private static Jedis getJedis() {
		if (Constants.usePool) {
			return getJedisPool().getResource();
		} else {
			Jedis p = new Jedis(Constants.jedisIp, Constants.jedisPort);
			p.auth(Constants.jedisPassword);
			return p;
		}
	}

	/**
	 * 私有方法:关闭/释放Jedis连接
	 * @param key 键
	 * @param value 值
	 * @throws JedisException
	 */
	@SuppressWarnings("deprecation")
	private static void closeJedis(Jedis p) {
		if (p != null) {
			if (Constants.usePool) {
				getJedisPool().returnResourceObject(p);
			} else {
				p.disconnect();
			}
		}
	}

	/**
	 * 公共方法：存String值，有过期时间
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 数据有效期 -1是永久有效
	 * @throws JedisException
	 */
	public static void setString(String key, String value, int cacheSeconds) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key, value);
			if (cacheSeconds >= 0) {
				jedis.expire(key, cacheSeconds);
			}
		} catch (JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
	}

	/**
	 * 公共方法：存String值，无过期时间
	 * @param key 键
	 * @param value 值
	 * @throws JedisException
	 */
	public static void setString(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key, value);
		} catch (JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
	}
	
	/**
	 * 公共方法：存String值，无过期时间
	 * @param key 键
	 * @param value 值
	 * @throws JedisException
	 */
	public static void setString(byte[] key, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key, value);
		} catch (JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
	}

	/**
	 * 公共方法：取String值
	 * @param key 键
	 * @throws JedisException
	 */
	public static String getString(String key) {
		Jedis jedis = null;
		String result = null;
		try {
			jedis = getJedis();
			result = jedis.get(key);
		} catch (JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}

	/**
	 * 公共方法：取String值
	 * @param key 键
	 * @throws JedisException
	 */
	public static byte[] getString(byte[] key) {
		Jedis jedis = null;
		byte[] result = null;
		try {
			jedis = getJedis();
			result = jedis.get(key);
		} catch (JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 公共方法：获取Hash值
	 * @param key fields
	 * @return List<String>
	 * @throws JedisException
	 */
	public static List<String> hmget(String key, String... fields) {

		Jedis jedis = null;
		List<String> result = null;
		try {
			jedis = getJedis();
			result = jedis.hmget(key, fields);
		} catch (JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 公共方法：存Hash值
	 * @param key hash
	 * @return List<String>
	 * @throws JedisException
	 */
	public static String hmset(String key, Map<String,String> hash) {

		Jedis jedis = null;
		String result = null;
		try {
			jedis = getJedis();
			result = jedis.hmset(key, hash);
		} catch (JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 公共方法：通过key， field 存值
	 * @param key hash
	 * @return Long
	 * @throws JedisException
	 */
	public static Long hset(String key, String field, String value) {

		Jedis jedis = null;
		Long result = null;
		try {
			jedis = getJedis();
			result = jedis.hset(key, field, value);
		} catch (JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 公共方法：通过key， field 获取 value
	 * @param key hash
	 * @return String
	 * @throws JedisException
	 */
	public static String hget(String key, String field) {

		Jedis jedis = null;
		String result = null;
		try {
			jedis = getJedis();
			result = jedis.hget(key, field);
		} catch (JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 公共方法：通过key， field 删除field
	 * @param key hash
	 * @return String
	 * @throws JedisException
	 */
	public static Long hdel(String key, String field) {

		Jedis jedis = null;
		Long result = null;
		try {
			jedis = getJedis();
			result = jedis.hdel(key, field);
		} catch (JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 公共方法：通过key 获取hash
	 * @param key
	 * @return Map<String, String>
	 * @throws JedisException
	 */
	public static Map<String, String> hgetAll(String key) {

		Jedis jedis = null;
		Map<String, String> result = null;
		try {
			jedis = getJedis();
			result = jedis.hgetAll(key);
		} catch (JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 公共方法：增加key里field的value
	 * @param key
	 * @return Long
	 * @throws JedisException
	 */
	public static Long hincrBy (String key, String field, int value) {

		Jedis jedis = null;
		Long result = null;
		try {
			jedis = getJedis();
			result = jedis.hincrBy(key, field, value);
		} catch (JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 公共方法：给redis中某个key设置过期时间
	 * @param key key值
	 * @param cacheSeconds 过期时间
	 * @throws JedisException
	 */
	public static void setExpire(String key, int cacheSeconds) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			if (cacheSeconds >= 0) {
				jedis.expire(key, cacheSeconds);
			}
		} catch (JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
	}

	/**
	 * 公共方法：返回redis中某个list队列头长度。
	 * <p>注意:list中的元素是可以重复的</p>
	 * @param key key
	 * @throws JedisException
	 */
	public static int llen(String key) {
		Jedis jedis = null;
		int length = 0;
		try {
			jedis = getJedis();
			length = jedis.llen(key).intValue();
		} catch (JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return length;
	}
	
	/**
	 * 公共方法：给redis中某个list队列头增加一个元素。
	 * <p>注意:list中的元素是可以重复的</p>
	 * @param set 集合
	 * @param key key
	 * @param data 插入的值
	 * @throws JedisException
	 */
	public static void lpush(String key, String data) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.lpush(key, data);
		} catch (JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
	}
	
	/**
	 * 公共方法：给redis中某个list队列尾增加一个元素。
	 * <p>注意:list中的元素是可以重复的</p>
	 * @param set 集合
	 * @param key key
	 * @param data 插入的值
	 * @throws JedisException
	 */
	public static void rpush(String key, String data) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.rpush(key, data);
		} catch (JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
	}

	/**
	 * 公共方法：给redis中某个list队列头出列一个元素。
	 * @param key key
	 * @throws JedisException
	 */
	public static String lpop(String key) {
		Jedis jedis = null;
		String str = null;
		try {
			jedis = getJedis();
			str = jedis.lpop(key);
		} catch (JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return str;
	}
	
	/**
	 * 公共方法：给redis中某个list队列尾出列一个元素。
	 * @param key key
	 * @throws JedisException
	 */
	public static String rpop(String key) {
		Jedis jedis = null;
		String str = null;
		try {
			jedis = getJedis();
			str = jedis.rpop(key);
		} catch (JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return str;
	}

	/**
	 * 公共方法：获取取redis中某个list中连续的N个元素
	 * <p>注意：该方法仅为查询，不会对list中的数据造成任何影响</p>
	 * @param key key
	 * @param srartindex 开始索引(0为第一个)
	 * @param endindex 开始索引(-1为最后一个)
	 * @throws JedisException
	 */
	public static List<String> lrange(String key, int srartindex,
			int endindex) {
		Jedis jedis = null;
		List<String> result = null;
		try {
			jedis = getJedis();
			result = jedis.lrange(key, srartindex, endindex);
		} catch (JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}

	/**
	 * 公共方法：删除List中count个和value一样值的元素
	 * @param key key
	 * @param count 总数  0代表删除所有
	 * @param value 值
	 * @throws JedisException
	 */
	public static Long lrem(String key, int count, String value) {
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = getJedis();
			result = jedis.lrem(key, count, value);
		} catch (JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 公共方法：根据key删除值
	 * @param key key
	 * @return Long
	 * @throws JedisException
	 */
	public static Long del(String key) {

		Jedis jedis = null;
		Long result = null;
		try {
			jedis = getJedis();
			result = jedis.del(key);
		} catch (JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 公共方法：根据key删除值
	 * @param key key
	 * @return Long
	 * @throws JedisException
	 */
	public static Long del(byte[] key) {
 
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = getJedis();
			result = jedis.del(key);
		} catch (JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 获取能匹配某一种模式的key的set集合
	 * @param pattern
	 * @return
	 */
	public static Set<String> getKeys(String pattern) {
		
		Jedis jedis = null;
		Set<String> result = null;
		
		try {
			jedis = getJedis();
			result = jedis.keys(pattern);
		} catch(JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return result;
		
	}
	/**
	 * set集合 添加一个元素到set中 redis中set值不重复
	 * @param key
	 * @param value
	 * @return
	 */
	public static int sadd(String key,String value){
		Jedis jedis = null;
		int addcount;
		try {
			jedis = getJedis();
			addcount = jedis.sadd(key, value).intValue();
		} catch(JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return addcount;
	}
	/**
	 * redis中指定key的set长度
	 * @param key
	 * @param value
	 * @return
	 */
	public static int scard(String key){
		Jedis jedis = null;
		int setSize;
		try {
			jedis = getJedis();
			setSize = jedis.scard(key).intValue();
		} catch(JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return setSize;
	}
	
	/**
	 * 判断参数中指定成员是否已经存在于与Key相关联的Set集合中
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean sismember(String key, String value){
		Jedis jedis = null;
		boolean exist;
		try {
			jedis = getJedis();
			exist = jedis.sismember(key, value);
		} catch(JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return exist;
	}
	
	/**
	 * 获取所有加入的value
	 * @param key
	 * @return
	 */
	public static Set<String> smembers(String key) {
		Jedis jedis = null;
		Set<String> result = null;
		try {
			jedis = getJedis();
			result = jedis.smembers(key);
		} catch(JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * value自增
	 * @param key
	 * @param value
	 * @return
	 */
	public static int incr(String key){
		Jedis jedis = null;
		int result;
		try {
			jedis = getJedis();
			result = new Long(jedis.incr(key)).intValue();
		} catch(JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * Redis中是否存在key
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean exists(String key){
		Jedis jedis = null;
		boolean exists;
		try {
			jedis = getJedis();
			exists = jedis.exists(key);
		} catch(JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return exists;
	}
	
	/**
	 * Redis Set的并集SUNION
	 * @param key
	 * @param value
	 * @return
	 */
	public static Set<String> sunion(String... keys){
		Jedis jedis = null;
		Set<String> unionSet;
		try {
			jedis = getJedis();
			unionSet = jedis.sunion(keys);
		} catch(JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return unionSet;
	}
	
	/**
	 * Redis Set的交集 SINTER
	 * @param key
	 * @param value
	 * @return
	 */
	public static Set<String> sinter(String... keys){
		Jedis jedis = null;
		Set<String> interSet;
		try {
			jedis = getJedis();
			interSet = jedis.sinter(keys);
		} catch(JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return interSet;
	}
	
	/**
	 * Redis 向有序集合添加一个或者多个成员 或者更新已存在人的分数 zadd
	 * @param key
	 * @param value
	 * @return
	 */
	public static long zadd(String key,double score, String member){
		Jedis jedis = null;
		long count;
		try {
			jedis = getJedis();
			count = jedis.zadd(key, score, member);
		} catch(JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return count;
	}
	
	
	/**
	 * Redis 向有序集合成员的数量 zcard
	 * @param key
	 * @param value
	 * @return
	 */
	public static long zcard(String key){
		Jedis jedis = null;
		long count;
		try {
			jedis = getJedis();
			count = jedis.zcard(key);
		} catch(JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return count;
	}
	
	/**
	 * Redis 向有序集合成员的数量 zcard
	 * @param key
	 * @param value
	 * @return
	 */
	public static Set<String> zrange(String key,long start,long end){
		Jedis jedis = null;
		Set<String> set;
		try {
			jedis = getJedis();
			set = jedis.zrange(key, start, end);
		} catch(JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return set;
	}
	
	
	/**
	 * Redis 向有序集合成员的数量 zcard
	 * @param key
	 * @param value
	 * @return
	 */
	public static long zrevrank(String key,String member){
		Jedis jedis = null;
		long index;
		try {
			jedis = getJedis();
			index = jedis.zrevrank(key, member);
		} catch(JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return index;
	}
	
	
	/**
	 * Redis 向有序集合成员的数量 zcard
	 * @param key
	 * @param value
	 * @return
	 */
	public static double zscore(String key,String member){
		Jedis jedis = null;
		double score;
		try {
			jedis = getJedis();
			score = jedis.zscore(key, member);
		} catch(JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return score;
	}
	
	/**
	 * Redis 向有序集合成员的数量 zcard
	 * @param key
	 * @param value
	 * @return
	 */
	public static Set<String> zrangeByScore(String key,double min,double max){
		Jedis jedis = null;
		Set<String> set;
		try {
			jedis = getJedis();
			set = jedis.zrangeByScore(key, min, max);
		} catch(JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return set;
	}
	
	/**
	 * 
	 * @author KangBinbin
	 * @date 2018年1月9日
	 * @description  获取key的剩余时效时间
	 *
	 */
	public static Long ttl(String key) {
		Jedis jedis = null;
		Long time = null;
		try {
			jedis = getJedis();
			time = jedis.ttl(key);
		} catch (JedisException e) {
			throw new JedisException(e);
		} finally {
			closeJedis(jedis);
		}
		return time;
	}
	
	
	/**
	 * 
	* @Title: lock 
	* @Description: redis 分布式锁
	* @param @param jedis
	* @param @param key
	* @param @param value
	* @param @param seconds
	* @param @return    
	* @author Shawn
	* @return Boolean     
	* @throws
	 */
   public static Boolean lock(String key, String value, int seconds) {
	   Jedis jedis = getJedis();
	   //NX:SET IF NOT EXIST，即当key不存在时，我们进行set操作；若key已经存在，则不做任何操作；
	   //EX:加一个过期的设置，具体时间由第五个参数决定。
        String result = jedis.set(key, value, "NX", "EX", seconds);
        return (result != null) && ("OK".equals(result));
    }
   
   
   
   /**
    * 
   * @Title: unlock 
   * @Description: redis解锁
   * @param @param jedis
   * @param @param key
   * @param @param value
   * @param @return    
   * @author Shawn
   * @return Boolean     
   * @throws
    */
   public static Boolean unlock(String key, String value) {
	   Jedis jedis = getJedis();
	   if (value.equals(jedis.get(key))) {
           return jedis.del(key) == 1;
       }
       return false;
   }

   public static void expire(String cacheKey, int expire) {
	   Jedis jedis = getJedis();
	   jedis.expire(cacheKey, expire);
   }
   
   
   
	
}
