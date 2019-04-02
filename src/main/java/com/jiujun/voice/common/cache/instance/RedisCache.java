package com.jiujun.voice.common.cache.instance;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jiujun.voice.common.cache.instance.iface.AspectCacheFace;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.serializer.ProtostuffSerializer;
import com.jiujun.voice.common.utils.PrintException;
import com.jiujun.voice.common.utils.StringUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/**
 * redis工具类
 * 
 * @author Coody
 *
 */
@Component
@SuppressWarnings("unchecked")
public class RedisCache implements AspectCacheFace {

	protected Integer cacheIndex = 0;

	@Resource
	private JedisPool jedisPool;

	/**
	 * 基础操作
	 */

	/**
	 * 查询缓存
	 * 
	 * @param key
	 *            缓存key
	 * @return
	 */
	@Override
	public <T> T getCache(String key) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			byte[] bytes = jedis.get(key.getBytes("ISO-8859-1"));
			if (StringUtil.isNullOrEmpty(bytes)) {
				return null;
			}
			return ProtostuffSerializer.unSerialize(bytes);
		} catch (Exception e) {
			delCache(key);
			PrintException.printException(e);
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 批量查询缓存
	 * 
	 * @param key
	 *            缓存key
	 * @return
	 */
	public Map<String, Object> getCacheBatch(String... keys) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Pipeline pip = jedis.pipelined();
			Map<String, Object> map = new HashMap<String, Object>(keys.length);
			Map<String,Response<byte[]>> responses=new HashMap<String,Response<byte[]>>(keys.length);
			for (String key : keys) {
				Response<byte[]> bytes = pip.get(key.getBytes("ISO-8859-1"));
				responses.put(key,bytes);
			}
			pip.syncAndReturnAll();
			for(String key:responses.keySet()){
				Response<byte[]> response=responses.get(key);
				if (StringUtil.isNullOrEmpty(response) || StringUtil.isNullOrEmpty(response.get())) {
					continue;
				}
				Object value = ProtostuffSerializer.unSerialize(response.get());
				map.put(key, value);
			}
			return map; 
		} catch (Exception e) {
			PrintException.printException(e);
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 设置缓存
	 * 
	 * @param key
	 *            缓存key
	 * @param value
	 *            缓存内容
	 * @param timeOut
	 *            缓存超时时间
	 */
	@Override
	public void setCache(String key, Object value, Integer timeOut) {

		if (StringUtil.isNullOrEmpty(key)) {
			return;
		}
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.setex(key.getBytes("ISO-8859-1"), timeOut, ProtostuffSerializer.serialize(value));
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 设置缓存
	 * 
	 * @param key
	 *            缓存key
	 * @param value
	 *            缓存内容
	 */
	@Override
	public void setCache(String key, Object value) {

		if (StringUtil.isNullOrEmpty(key)) {
			return;
		}
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key.getBytes("ISO-8859-1"), ProtostuffSerializer.serialize(value));
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 *            缓存key
	 */
	@Override
	public void delCache(String key) {

		if (StringUtil.isNullOrEmpty(key)) {
			return;
		}
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// jedis.setnx(b, a);
			jedis.del(key.getBytes("ISO-8859-1"));
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 模糊删除缓存
	 * 
	 * @param key
	 *            缓存key
	 */
	public void delCacheFuzz(String key) {

		if (StringUtil.isNullOrEmpty(key)) {
			return;
		}
		key = MessageFormat.format("{0}*", key);
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Set<String> keys = jedis.keys(key);
			for (String tmp : keys) {
				jedis.del(tmp);
			}
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 检查key是否存在
	 * 
	 * @param key
	 *            缓存key
	 * @param value
	 *            缓存内容
	 * @param timeOut
	 *            缓存超时时间
	 * @return
	 */
	@Override
	public Boolean contains(String key) {

		if (StringUtil.isNullOrEmpty(key)) {
			return false;
		}
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.exists(key);
		} catch (Exception e) {
			PrintException.printException(e);
			return false;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 基础操作 end
	 */

	/**
	 * LIST操作压队列尾
	 */
	public void lBeanPushTail(String key, Object... values) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			for (Object value : values) {
				jedis.rpush(key.getBytes("ISO-8859-1"), ProtostuffSerializer.serialize(value));
			}
		} catch (Exception e) {
			// delCache(key);
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * LIST操作 压队列头
	 */
	public void lBeanPushHead(String key, Object... values) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			for (Object value : values) {
				jedis.lpush(key.getBytes("ISO-8859-1"), ProtostuffSerializer.serialize(value));

			}
		} catch (Exception e) {
			// delCache(key);
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public <T> T lBeanProp(String key) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			byte[] bytes = jedis.lpop(key.getBytes("ISO-8859-1"));
			if (StringUtil.isNullOrEmpty(bytes)) {
				return null;
			}
			return ProtostuffSerializer.unSerialize(bytes);
		} catch (Exception e) {
			// delCache(key);
			PrintException.printException(e);
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public List<?> lBeanPropAll(String key) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			byte[] bytes = jedis.lpop(key.getBytes("ISO-8859-1"));
			if (StringUtil.isNullOrEmpty(bytes)) {
				return null;
			}
			List<Object> objs = new ArrayList<Object>();
			objs.add(ProtostuffSerializer.unSerialize(bytes));
			while (!StringUtil.isNullOrEmpty(bytes)) {
				bytes = jedis.lpop(key.getBytes("ISO-8859-1"));
				if (!StringUtil.isNullOrEmpty(bytes)) {
					objs.add(ProtostuffSerializer.unSerialize(bytes));
				}
			}
			return objs;
		} catch (Exception e) {
			// delCache(key);
			PrintException.printException(e);
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 获取指定范围的元素
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<?> lrange(String key, long start, long end) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			List<byte[]> bytes = jedis.lrange(key.getBytes("ISO-8859-1"), start, end);
			List<Object> objs = new ArrayList<Object>();
			if (!StringUtil.isNullOrEmpty(bytes)) {
				for (byte[] b : bytes) {
					objs.add(ProtostuffSerializer.unSerialize(b));
				}
			}
			return objs;
		} catch (Exception e) {
			// delCache(key);
			PrintException.printException(e);
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 移除队列，根据count的不同，分三种： a、count>0：从左到右，删除最多count个元素
	 * b、count<0：从右到左，删除最多count个元素 c、count=0：删除所有
	 * 
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 */
	public Long lrem(String key, int count, Object value) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Long ret = jedis.lrem(key.getBytes("ISO-8859-1"), count, ProtostuffSerializer.serialize(value));
			if (ret != null) {
				return ret;
			}
			return 0L;
		} catch (Exception e) {
			PrintException.printException(e);
			return 0L;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public long lBeanCount(String key) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.llen(key);
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return 0;
	}

	/**
	 * SET操作
	 */
	public void sBeanPush(String key, Object... values) {

		if (StringUtil.isNullOrEmpty(values)) {
			return;
		}
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			for (Object obj : values) {
				jedis.sadd(key.getBytes("ISO-8859-1"), ProtostuffSerializer.serialize(obj));
			}
		} catch (Exception e) {
			delCache(key);
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public <T> T sBeanPop(String key) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			byte[] bytes = jedis.spop(key.getBytes("UTF-8"));
			if (StringUtil.isNullOrEmpty(bytes)) {
				return null;
			}
			return ProtostuffSerializer.unSerialize(bytes);
		} catch (Exception e) {
			delCache(key);
			PrintException.printException(e);
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void sDel(String key, String... value) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.srem(key, value);
		} catch (Exception e) {
			delCache(key);
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public Long sCount(String key) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.scard(key);
		} catch (Exception e) {
			PrintException.printException(e);
			return 0L;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * SET操作
	 */

	/**
	 * MAP操作
	 */

	public Map<String, Object> hBeanGetMap(String key) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Map<byte[], byte[]> byteMap = jedis.hgetAll(key.getBytes("ISO-8859-1"));
			if (StringUtil.isNullOrEmpty(byteMap)) {
				return null;
			}
			Map<String, Object> map = new HashMap<String, Object>(8);
			for (byte[] tmp : byteMap.keySet()) {
				byte[] bytes = byteMap.get(tmp);
				Object value = null;
				if (!StringUtil.isNullOrEmpty(bytes)) {
					value = ProtostuffSerializer.unSerialize(bytes);
				}
				map.put(new String(tmp), value);
			}
			return map;
		} catch (Exception e) {
			delCache(key);
			PrintException.printException(e);
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public <T> T hBeanGet(String key, String fieldName) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			byte[] bytes = jedis.hget(key.getBytes("ISO-8859-1"), fieldName.getBytes("ISO-8859-1"));
			if (StringUtil.isNullOrEmpty(bytes)) {
				return null;
			}
			return ProtostuffSerializer.unSerialize(bytes);
		} catch (Exception e) {
			hDel(key, fieldName);
			PrintException.printException(e);
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public String hBeanGetString(String key, String fieldName) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.hget(key, fieldName);
		} catch (Exception e) {
			hDel(key, fieldName);
			PrintException.printException(e);
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void hBeanPush(String key, String fieldName, Object value) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.hset(key.getBytes("ISO-8859-1"), fieldName.getBytes("ISO-8859-1"),
					ProtostuffSerializer.serialize(value));
		} catch (Exception e) {
			hDel(key, fieldName);
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void hBeanPushString(String key, String fieldName, String value) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.hset(key, fieldName, value);
		} catch (Exception e) {
			hDel(key, fieldName);
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 批量插入
	 * 
	 * @param key
	 * @param fieldName
	 * @param value
	 */
	public void hmset(String key, Map<String, Object> map) {

		Jedis jedis = null;
		try {
			if (map != null && map.size() > 0) {
				Map<byte[], byte[]> hash = new HashMap<byte[], byte[]>(8);
				for (String field : map.keySet()) {
					byte[] k = field.toString().getBytes("ISO-8859-1");
					byte[] v = ProtostuffSerializer.serialize(map.get(field));
					hash.put(k, v);
				}
				jedis = jedisPool.getResource();
				jedis.hmset(key.getBytes("ISO-8859-1"), hash);
			}
		} catch (Exception e) {
			delCache(key);
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 批量获取
	 * 
	 * @param key
	 * @param fieldName
	 * @return
	 */
	public List<?> hmget(String key, Object... fieldName) {

		Jedis jedis = null;
		try {
			byte[][] obj = new byte[fieldName.length][];
			jedis = jedisPool.getResource();
			int i = 0;
			for (Object field : fieldName) {
				obj[i] = field.toString().getBytes("ISO-8859-1");
				i++;
			}
			List<byte[]> list = jedis.hmget(key.getBytes("ISO-8859-1"), obj);
			if (StringUtil.isNullOrEmpty(list)) {
				return null;
			}
			List<Object> ret = new ArrayList<Object>();
			boolean status = true;
			for (byte[] b : list) {
				try {
					Object data = ProtostuffSerializer.unSerialize(b);
					ret.add(data);
				} catch (Exception e) {
					status = false;
				}
			}
			if (!status) {
				LogUtil.logger.error("###hmget 反序列化存在失败对象，key=" + key);
			}
			return ret;
		} catch (Exception e) {
			delCache(key);
			PrintException.printException(e);
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public boolean hSetNx(String key, String fieldName, Object value) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			long status = jedis.hsetnx(key.getBytes("ISO-8859-1"), fieldName.getBytes("ISO-8859-1"),
					ProtostuffSerializer.serialize(value));
			if (status == 1) {
				return true;
			}
			return false;
		} catch (Exception e) {
			delCache(key);
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return false;
	}

	public <T> List<T> hBeanGetAll(String key) {

		try {
			Map<byte[], byte[]> maps = hBeanGetAll(key.getBytes("ISO-8859-1"));
			if (StringUtil.isNullOrEmpty(maps)) {
				return null;
			}
			List<Object> list = new ArrayList<Object>();
			for (byte[] tmp : maps.keySet()) {
				byte[] bytes = maps.get(tmp);
				if (StringUtil.isNullOrEmpty(bytes)) {
					continue;
				}
				Object value = ProtostuffSerializer.unSerialize(bytes);
				list.add(value);
			}
			return (List<T>) list;
		} catch (Exception e) {
			delCache(key);
			PrintException.printException(e);
			return null;
		}
	}

	private Map<byte[], byte[]> hBeanGetAll(byte[] key) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.hgetAll(key);
		} catch (Exception e) {
			PrintException.printException(e);
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public Long hCount(String key) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Long count = jedis.hlen(key.getBytes("ISO-8859-1"));
			if (count != null) {
				return count;
			}
		} catch (Exception e) {
			PrintException.printException(e);
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return 0L;
	}

	public void hDel(String key, String fieldName) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.hdel(key.getBytes("ISO-8859-1"), fieldName.getBytes("ISO-8859-1"));
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public Boolean hContains(String key, String fieldName) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.hexists(key.getBytes("ISO-8859-1"), fieldName.getBytes("ISO-8859-1"));
		} catch (Exception e) {
			PrintException.printException(e);
			return false;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	// s-set
	/**
	 * 有序set（添加成员）
	 * 
	 * @param key
	 *            有序的set的key
	 * @param score
	 *            排序值（默认从小到大排序）
	 * @param member
	 *            成员（使用userId）
	 * @return
	 * @author shao.xiang
	 * @data 2018年5月29日
	 */
	public long zadd(String key, double score, String member) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.zadd(key, score, member);
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return 0L;
	}

	/**
	 * 批量插入
	 * 
	 * @param key
	 * @param scoreMembers
	 * @return
	 */
	public long zadd(String key, Map<String, Double> scoreMembers) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.zadd(key, scoreMembers);
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return 0L;
	}

	/**
	 * 有序set（获取成员排名，按分数从小到大排名）
	 * 
	 * @param key
	 * @param member
	 * @return
	 * @author shao.xiang
	 * @data 2018年5月29日
	 */
	public long zrank(String key, String member) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.zrank(key, member);
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return 0L;
	}

	/**
	 * 有序set（获取成员排名，按分数从大到小排名）
	 * 
	 * @param key
	 * @param member
	 * @return
	 * @author shao.xiang
	 * @data 2018年5月29日
	 */
	public long zrevrank(String key, String member) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.zrevrank(key, member);
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return 0L;
	}

	/**
	 * 有序set（获取成员分数）
	 * 
	 * @param key
	 * @param member
	 * @return
	 * @author shao.xiang
	 * @data 2018年5月29日
	 */
	public long zscore(String key, String member) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Double zscore = jedis.zscore(key, member);
			if (zscore != null) {
				return Math.round(zscore);
			}
			return 0;
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return 0;
	}

	/**
	 * 是否存在某个成员
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public boolean zcontains(String key, String member) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Double zscore = jedis.zscore(key, member);
			if (zscore != null) {
				return true;
			}
			return false;
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return false;
	}

	/**
	 * 移除某个元素
	 * 
	 * @param key
	 * @param members
	 */
	public void zrem(String key, String... members) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.zrem(key, members);
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 有序set（返回集合中元素个数，时间复杂度O(1)）
	 * 
	 * @param key
	 * @return
	 * @author shao.xiang
	 * @data 2018年5月29日
	 */
	public long zcard(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.zcard(key);
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return 0L;
	}

	/**
	 * 有序set（返回指定下标区间的集合，指定start=0，end=-1时，返回所有）
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 * @author shao.xiang
	 * @data 2018年5月29日
	 */
	public Set<String> zrange(String key, long start, long end) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.zrange(key, start, end);
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return null;
	}

	/**
	 * 获取指定范围的成员
	 * 
	 * @param key
	 * @param max
	 * @return
	 */
	public Set<String> zrangeByScore(String key, double max) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.zrangeByScore(key, 0, max);
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return null;
	}

	/**
	 * 获取结果集，list
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> zlist(String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			List<String> ret = null;
			Set<String> sets = jedis.zrange(key, start, end);
			if (sets != null && sets.size() > 0) {
				ret = new ArrayList<String>();
				for (String userId : sets) {
					ret.add(userId);
				}
			}
			return ret;
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return null;
	}

	/**
	 * 有序set（为成员增加score，并返回新的score值，若key不存在，则会先插入再执行，score不能为负，因为指定了double，如果要为负，则score须为float）
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 * @author shao.xiang
	 * @data 2018年5月29日
	 */
	public Double zincrby(String key, double score, String member) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.zincrby(key, score, member);
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return 0D;
	}

	/**
	 * 设置过期时间
	 * 
	 * @param key
	 * @param seconds
	 *            秒
	 * @return
	 */
	public long expire(String key, int seconds) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.expire(key, seconds);
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return 0L;
	}

	/**
	 * 校验key是否存在
	 * 
	 * @param key
	 * @return true 存在且未过期
	 */
	public boolean exits(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			boolean flag = false;
			if (jedis.exists(key) && jedis.ttl(key) != -2) {
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return false;
	}

	public Long incr(String key, int seconds) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Long ret = jedis.incr(key);
			jedis.expire(key, seconds);
			return ret;
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return 0L;
	}

	public Long decr(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.decr(key);
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return 0L;
	}

	/**
	 * 字符串，非序列化
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.get(key);
		} catch (Exception e) {
			PrintException.printException(e);
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 字符串，非序列化
	 * 
	 * @param key
	 * @return
	 */
	public void setString(String key, String value, int timeOut) {
		if (StringUtil.isNullOrEmpty(key)) {
			return;
		}
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.setex(key, timeOut, value);
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 字符串，非序列化
	 * 
	 * @param key
	 * @return
	 */
	public void setString(String key, String value) {
		if (StringUtil.isNullOrEmpty(key)) {
			return;
		}
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value);
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 字符串，非序列化
	 * 
	 * @param key
	 * @return
	 */
	public boolean setNx(String key, String value) {
		if (StringUtil.isNullOrEmpty(key)) {
			return false;
		}
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			long status = jedis.setnx(key, value);
			if (status == 1) {
				return true;
			}
			return false;
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return false;
	}

	public boolean setNx(String key, String value, int seconds) {
		if (setNx(key, value)) {
			expire(key, seconds);
			return true;
		}
		return false;
	}

}
