package com.zwh.website.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author ZhangWeihui
 * @date 2019/11/12 16:49
 */
@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void put(String key, Object value) {
        ValueOperations vo = redisTemplate.opsForValue();
        vo.set(key, value);
    }

    public Object get(String key) {
        ValueOperations vo = redisTemplate.opsForValue();
        return vo.get(key);
    }

    public void hashPut(String key, Map<String,Object> entries) {
        HashOperations<String,String,Object> ho = redisTemplate.opsForHash();
        for(Map.Entry<String,Object> entry : entries.entrySet()) {
            ho.put(key, entry.getKey(), entry.getValue());
        }
    }

    public Map<String,Object> hashGetAll(String key) {
        HashOperations<String,String,Object> ho = redisTemplate.opsForHash();
        return ho.entries(key);
    }

    public Object hashGet(String key, String entryKey) {
        HashOperations<String,String,Object> ho = redisTemplate.opsForHash();
        return ho.get(key, entryKey);
    }
}
