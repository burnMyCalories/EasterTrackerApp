/*
reference: https://www.jianshu.com/p/52b0805f1950
 */
package com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class JedisUtils {

    private static Logger logger = LoggerFactory.getLogger(JedisUtils.class);

    private static JedisPool JEDISPOOL;

    public static void getInstance(){
        if(JEDISPOOL == null){
            logger.info(" JedisUtils getInstance ");
            try {
                JedisPoolConfig conf = new JedisPoolConfig();
                conf.setMaxIdle(ConfigUtils.maxIdle);
                conf.setTestOnBorrow(ConfigUtils.testOnBorrow);
                conf.setTestOnReturn(ConfigUtils.testOnReturn);
                JEDISPOOL = new JedisPool(conf, ConfigUtils.ip, ConfigUtils.port);
            } catch (Exception e) {
                logger.error("Load [jedis.properties] Error [" + e.getMessage() + "]", e);
            }
        }
    }


    public static Jedis getJedis() {
        try{
            return JEDISPOOL.getResource();
        }catch (Exception e) {
            return null;
        }
    }

    public static void recycleJedis(Jedis jedis) {
        if(jedis != null){
            jedis.close();
        }
    }

    public static void closeJedisPool(){
        if(JEDISPOOL != null){
            JEDISPOOL.close();
        }
    }


    public static void put(String id , Object key, Object value) {
        Jedis jedis = getJedis();
        try{
            logger.info(" redis put ... key = [" + key + "]");
            jedis.hset(id.toString().getBytes(), key.toString().getBytes(), SerializeUtils.serialize(value));
            ConfigUtils.setSucc();
        }catch(Exception e){
            ConfigUtils.setFailCount();
            logger.error("Redis Execute Error[" + e.getMessage() + "]" , e);
        }finally {
            recycleJedis(jedis);
        }
    }

    public static <T> T get(String id , Object key) {
        Jedis jedis = getJedis();
        try{
            T value = SerializeUtils.unserialize(jedis.hget(id.toString().getBytes(), key.toString().getBytes()));
            logger.info(" redis get ... key = [" + key + "] , value = [" + value + "]");

            ConfigUtils.setSucc();
            return value;
        }catch(Exception e){
            ConfigUtils.setFailCount();
            logger.error("Redis Execute Error[" + e.getMessage() + "]" , e);
        }finally {
            recycleJedis(jedis);
        }
        return null;
    }

    public static Long remove(String id , Object key) {
        Jedis jedis = getJedis();
        try{
            Long num = jedis.hdel(id.toString(), key.toString());
            ConfigUtils.setSucc();
            return num;
        }catch(Exception e){
            ConfigUtils.setFailCount();
            logger.error("Redis Execute Error[" + e.getMessage() + "]" , e);
        }finally {
            recycleJedis(jedis);
        }
        return 0L;
    }

    public static void removeAll(String id) {
        Jedis jedis = getJedis();
        try{
            jedis.del(id.toString());
            ConfigUtils.setSucc();
        }catch(Exception e){
            ConfigUtils.setFailCount();
            logger.error("Redis Execute Error[" + e.getMessage() + "]" , e);
        }finally {
            recycleJedis(jedis);
        }
    }

    public static int getSize(String id) {
        return 0;
    }
}
