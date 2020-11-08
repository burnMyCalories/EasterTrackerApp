/*
reference: https://www.jianshu.com/p/52b0805f1950
 */
package com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class ConfigUtils {
    private static Logger logger = LoggerFactory.getLogger(ConfigUtils.class);

    public static boolean redisSwitch ;
    public static int maxIdle ;
    public static boolean testOnBorrow;
    public static boolean testOnReturn;
    public static String ip;
    public static int port;
    public static String key;

    public static int fail_count = 0 ;

    static {
        Properties props = new Properties();
        try {
            props.load(JedisUtils.class.getResourceAsStream("/redis.properties"));

            redisSwitch = Boolean.valueOf(props.getProperty("redis.switch"));
            maxIdle = Integer.valueOf(props.getProperty("jedis.pool.maxIdle"));
            testOnBorrow = Boolean.valueOf(props.getProperty("jedis.pool.testOnBorrow"));
            testOnReturn = Boolean.valueOf(props.getProperty("jedis.pool.testOnReturn"));
            ip = props.getProperty("redis.ip") ;
            port = Integer.valueOf(props.getProperty("redis.port"));
            key = props.getProperty("redis.key") ;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void setSwitch(boolean redisSwitch){
        if(true == ConfigUtils.redisSwitch && false == redisSwitch){
            //switch : open --> close
            logger.info("switch : open --> close");
            JedisUtils.closeJedisPool();
        }else if(false == ConfigUtils.redisSwitch && true == redisSwitch){
            //switch : close --> open
            logger.info("switch : close --> open");
            JedisUtils.getInstance();
        }

        ConfigUtils.redisSwitch = redisSwitch ;
    }


    public static void setFailCount(){
        if(redisSwitch){
            fail_count += 1;


            if(fail_count > 10){
                logger.info(" setSwitch(false) ");
                setSwitch(false);
            }
        }
    }

    public static void setSucc(){
        if(fail_count > 0 ){
            fail_count = 0 ;
        }

        if(!redisSwitch){
            setSwitch(true);
        }
    }
}