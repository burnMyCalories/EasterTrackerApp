/*
reference: https://www.jianshu.com/p/52b0805f1950
 */
package com.util;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RedisCacheUtils implements Cache {

    private static Logger logger = LoggerFactory.getLogger(RedisCacheUtils.class);

    private String cacheId;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    public RedisCacheUtils(String cacheId) {
        if (cacheId == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.cacheId = ConfigUtils.key + "." + cacheId;
        logger.info("NTSRedisCache cacheId ========== " + cacheId);

        if(ConfigUtils.redisSwitch){
            JedisUtils.getInstance();
        }
    }

    @Override
    public String getId() {
        return cacheId;
    }

    @Override
    public void putObject(Object key, Object value) {
        logger.info("NTSRedisCache putObject = " + cacheId);

        if(ConfigUtils.redisSwitch){
            JedisUtils.put(cacheId, key, value);
        }
    }

    @Override
    public Object getObject(Object key) {
        logger.info("NTSRedisCache getObject = " + cacheId);

        if(ConfigUtils.redisSwitch){
            return JedisUtils.get(cacheId, key);
        }else{
            return null;
        }
    }

    @Override
    public Object removeObject(Object key) {
        logger.info("NTSRedisCache removeObject = " + cacheId);

        if(ConfigUtils.redisSwitch){
            return JedisUtils.remove(cacheId, key);
        }else{
            return null;
        }
    }

    @Override
    public void clear() {
        logger.info("NTSRedisCache clear = " + cacheId);

        if(ConfigUtils.redisSwitch){
            JedisUtils.removeAll(cacheId);
        }
    }

    @Override
    public int getSize() {
        logger.info("NTSRedisCache getSize = " + cacheId);

        if(ConfigUtils.redisSwitch){
            return JedisUtils.getSize(cacheId);
        }else{
            return -1;
        }
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

}
