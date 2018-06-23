package com.apimicroservices.verificationpic.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class PicRedisDao implements PicDao {
    /**
     * redis 键值存储前缀
     */
    private static final String keyPrefix = "ver_pic_key_";
    private static final String configPrefix = "ver_pic_conf_";
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Override
    public void save(String key, String value , long timeout) {
        stringRedisTemplate.opsForValue().set(keyPrefix + key,value,timeout, TimeUnit.SECONDS);
    }


    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(keyPrefix + key);
    }

    @Override
    public String getConfig(String key) {
        return stringRedisTemplate.opsForValue().get(configPrefix + key);
    }

    @Override
    public void setConfig(String key, String value) {
        stringRedisTemplate.opsForValue().set(configPrefix + key,value);
    }
}
