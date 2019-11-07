package com.zwh.website;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class RedisTemplateTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test1() {
        ValueOperations vo = redisTemplate.opsForValue();
        vo.set("domain", "zhangweihui.com.cn");
        String domain = (String) vo.get("domain");
        Assertions.assertEquals("zhangweihui.com.cn", domain);
    }

}
