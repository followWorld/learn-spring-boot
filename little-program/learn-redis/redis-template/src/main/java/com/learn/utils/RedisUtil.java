package com.learn.utils;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 *
 * @author QLQ
 * 基于spring和redis的redisTemplate工具类
 * 针对所有的hash 都是以h开头的方法
 * 针对所有的Set 都是以s开头的方法                    不含通用方法
 * 针对所有的List 都是以l开头的方法
 * 针对所有的ZSet 都是以s开头的方法
 */
@Component
public class RedisUtil implements InitializingBean{

	@Autowired
	@Qualifier("redisTemplateString")
	private RedisTemplate<String, Object> redisTemplate;
	private ZSetOperations<String, Object> opsForZSet;
	private static final Logger log = LoggerFactory.getLogger("RedisUtil");

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    //=============================common============================
    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key,long time){
        try {
            if(time>0){
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
        	log.error("RedisUtil.expire出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return false;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key){
        try {
			return redisTemplate.getExpire(key,TimeUnit.SECONDS);
		}finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
        	log.error("RedisUtil.hasKey出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return false;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }
    /**
	 * 模糊获取key
	 *
	 * @param key 键 不能为null
	 * @return Set<String>
	 */
	public Set<String> getKeys(String key) {
		try {
			return redisTemplate.keys(key);
		}finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
	}

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String ... key){
        try {
			if(key!=null&&key.length>0){
			    if(key.length==1){
			        redisTemplate.delete(key[0]);
			    }else{
			        redisTemplate.delete(CollectionUtils.arrayToList(key));
			    }
			}
		}finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    //============================String=============================
    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
    public Object get(String key){
        try {
			return key==null?null:redisTemplate.opsForValue().get(key);
		}finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key,String value) {
         try {
        	if(key == null){
        		return false;
        	}
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
        	log.error("RedisUtil.set出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return false;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}

    }

    /**
     * 普通缓存放入并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key,String value,long time){
        try {
            if(time>0){
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            }else{
                set(key, value);
            }
            return true;
        } catch (Exception e) {
        	log.error("RedisUtil.set出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return false;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 递增
     * @param key 键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta){
    	try {
    		if(delta<0){
                throw new RuntimeException("递增因子必须大于0");
            }
            return redisTemplate.opsForValue().increment(key, delta);
		} finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 递减
     * @param key 键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta){
        try {
			if(delta<0){
			    throw new RuntimeException("递减因子必须大于0");
			}
			return redisTemplate.opsForValue().increment(key, -delta);
		} finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    //================================Map=================================
    /**
     * HashGet
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key,String item){
        try {
			return redisTemplate.opsForHash().get(key, item);
		} finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 获取hashKey对应的所有键值
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object,Object> hmget(String key){
        try {
			return redisTemplate.opsForHash().entries(key);
		}finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * HashSet
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String,Object> map){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
        	log.error("RedisUtil.hmset出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return false;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * HashSet 并设置时间
     * @param key 键
     * @param map 对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String,Object> map, long time){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if(time>0){
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
        	log.error("RedisUtil.hmset出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return false;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }
    public boolean hmsetString(String key, Map<String,String> map, long time){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if(time>0){
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
        	log.error("RedisUtil.hmsetString出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return false;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key,String item,Object value) {
         try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
        	log.error("RedisUtil.hset出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return false;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key,String item,Object value,long time) {
         try {
            redisTemplate.opsForHash().put(key, item, value);
            if(time>0){
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
        	log.error("RedisUtil.hset出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return false;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 删除hash表中的值
     * @param key 键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item){
        try {
			redisTemplate.opsForHash().delete(key,item);
		}finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 判断hash表中是否有该项的值
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item){
        try {
			return redisTemplate.opsForHash().hasKey(key, item);
		}finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @param key 键
     * @param item 项
     * @param by 要增加几(大于0)
     * @return
     */
    public double hincr(String key, String item,double by){
        try {
			return redisTemplate.opsForHash().increment(key, item, by);
		}finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * hash递减
     * @param key 键
     * @param item 项
     * @param by 要减少记(小于0)
     * @return
     */
    public double hdecr(String key, String item,double by){
        try {
			return redisTemplate.opsForHash().increment(key, item,-by);
		}finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    //============================set=============================
    /**
     * 根据key获取Set中的所有值
     * @param key 键
     * @return
     */
    public Set<Object> sGet(String key){
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
        	log.error("RedisUtil.sGet出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return null;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 根据value从一个set中查询,是否存在
     * @param key 键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key,Object value){
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
        	log.error("RedisUtil.sHasKey出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return false;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 将数据放入set缓存
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object...values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
        	log.error("RedisUtil.sSet出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return 0;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 将set数据放入缓存
     * @param key 键
     * @param time 时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key,long time,Object...values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if(time>0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
        	log.error("RedisUtil.sSetAndTime出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return 0;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 获取set缓存的长度
     * @param key 键
     * @return
     */
    public long sGetSetSize(String key){
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
        	log.error("RedisUtil.sGetSetSize出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return 0;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 移除值为value的
     * @param key 键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object ...values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
        	log.error("RedisUtil.setRemove出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return 0;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }
    //===============================list=================================

    /**
     * 获取list缓存的内容
     * @param key 键
     * @param start 开始
     * @param end 结束  0 到 -1代表所有值
     * @return
     */
    public List<Object> lGet(String key,long start, long end){
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
        	log.error("RedisUtil.lGet出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return null;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 获取list缓存的长度
     * @param key 键
     * @return
     */
    public long lGetListSize(String key){
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
        	log.error("RedisUtil.lGetListSize出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return 0;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 通过索引 获取list中的值
     * @param key 键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key,long index){
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
        	log.error("RedisUtil.lGetIndex出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return null;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
        	log.error("RedisUtil.lSet出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return false;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
        	log.error("RedisUtil.lSet出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return false;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
        	log.error("RedisUtil.lSet出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return false;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
        	log.error("RedisUtil.lSet出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return false;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 根据索引修改list中的某条数据
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index,Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
        	log.error("RedisUtil.lUpdateIndex出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return false;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 移除N个值为value
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key,long count,Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
        	log.error("RedisUtil.lRemove出现异常：{}" , ExceptionUtils.getStackTrace(e));
            return 0;
        }finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
    }

    /**
     * 添加 一个元素
     * @param key 键
     * @param value 集合元素
     * @param score 分數
     * @return 成功失敗
     */
	public boolean zAdd(String key, Object value, double score) {

		try {
			if (key == null) {
				return false;
			}
			return opsForZSet.add(key, value, score);
		} catch (Exception e) {
			log.error("RedisUtil.zAdd出现异常：{}" , ExceptionUtils.getStackTrace(e));
			return false;
		}finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}

	}

	/**
	 * 查询一个元素的score
	 *
	 * @param key
	 *            键
	 * @param o
	 *            集合元素
	 * @return score
	 */
	public Double zScore(String key, Object o) {

		try {
			if (key == null) {
				return 0.0;
			}
			return opsForZSet.score(key, o);
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}

	}

	/**
	 * 移除N个元素
	 *
	 * @param key
	 *            键
	 * @param values
	 *            集合元素
	 * @return 移除元素的个数
	 */
	public long zRemove(String key, Object... values) {
		try {
			if (key == null) {
				return 0;
			}
			return opsForZSet.remove(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
	}

	/**
	 * 移除索引范围的元素
	 *
	 * @param start
	 *            元素索引开始值（包含）
	 * @param end
	 *            元素索引结束值（包含） -1代表最后一个元素
	 * @return 移除元素的个数
	 */
	public long zRemoveRange(String key, long start, long end) {
		try {
			if (key == null) {
				return 0;
			}
			return opsForZSet.removeRange(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
	}

	/**
	 * 通过score范围删除指定范围的元素
	 *
	 * @param min
	 *            score的开始值（包含）
	 * @param max
	 *            score的结束值（包含） +inf=正无穷 -inf=负无穷
	 * @return 移除元素的个数
	 */
	public long zRemoveRangeByScore(String key, double min, double max) {
		try {
			if (key == null) {
				return 0;
			}
			return opsForZSet.removeRangeByScore(key, min, max);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
	}

	/**
	 * 通过增量增加排序集中的元素的分数
	 *
	 * @param key
	 *            键
	 * @param value
	 *            集合元素
	 * @param delta
	 *            步长 正数为增加 负数为减少
	 * @return 增加后的score值
	 */
	public double zIncrementScore(String key, Object value, double delta) {
		try {
			if (key == null) {
				return 0;
			}
			return opsForZSet.incrementScore(key, value, delta);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
	}

	/**
	 * 通过索引范围查指定范围的元素
	 *
	 * @param key
	 *            键
	 * @param start
	 *            元素索引开始值（包含）
	 * @param end
	 *            元素索引结束值（包含） -1代表最后一个元素
	 * @return 范围内元素的集合
	 */
	public Set<Object> zRange(String key, long start, long end) {
		try {
			if (key == null) {
				return null;
			}
			return opsForZSet.range(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
	}

	/**
	 * 通过score范围查指定范围的元素
	 *
	 * @param key
	 *            键
	 * @param min
	 *            score的开始值（包含）
	 * @param max
	 *            score的结束值（包含） +inf=正无穷 -inf=负无穷
	 * @return 范围内元素的集合
	 */
	public Set<Object> zRangeByScore(String key, double min, double max) {
		try {
			if (key == null) {
				return null;
			}
			return opsForZSet.rangeByScore(key, min, max);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
	}

	/**
	 * 通过score范围查指定范围的元素
	 *
	 * @param key
	 *            键
	 * @param min
	 *            score的开始值（包含）
	 * @param max
	 *            score的结束值（包含） +inf=正无穷 -inf=负无穷
	 * @param offset
	 *            范围内的第几个开始
	 * @param count
	 *            个数
	 * @return 元素的集合
	 */
	public Set<Object> zRangeByScoreLimit(String key, double min, double max, long offset, long count) {
		try {
			if (key == null) {
				return null;
			}
			return opsForZSet.rangeByScore(key, min, max, offset, count);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
	}

	public Object blpop(String key, long timeOut, TimeUnit unit) {
		 try {
			return redisTemplate.opsForList().leftPop(key, timeOut, unit);
		}finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
	}

	public Object brpop(String key, long timeOut, TimeUnit unit) {
		 try {
			return redisTemplate.opsForList().rightPop(key, timeOut, unit);
		}finally {
        	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
	}
	public Object blpush(String key, Object value) {
		 try {
			return redisTemplate.opsForList().leftPush(key, value);
		}finally {
       	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
	}

	public Object brpush(String key, Object value) {
		 try {
			return redisTemplate.opsForList().rightPush(key, value);
		}finally {
       	RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
		}
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		opsForZSet = redisTemplate.opsForZSet();
	}
}
