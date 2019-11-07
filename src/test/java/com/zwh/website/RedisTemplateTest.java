package com.zwh.website;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class RedisTemplateTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testString() {
        ValueOperations vo = redisTemplate.opsForValue();
        vo.set("domain", "zhangweihui.com.cn");
        String domain = (String) vo.get("domain");
        Assertions.assertEquals("zhangweihui.com.cn", domain);
    }

    @Test
    public void testObject() {
        User user = new User("walkman", "123qwe");
        String key = "user::"+user.getName();
        redisTemplate.opsForValue().set(key, user);
        User user1 = (User)redisTemplate.opsForValue().get(key);
        Assertions.assertEquals(user, user1);
    }

    @Test
    public void testExpire() throws InterruptedException {
        User user=new User("ityouknow@126.com", "expire");
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        String key = "user::" + user.getName();
        operations.set(key, user,100, TimeUnit.MILLISECONDS);
        Thread.sleep(1000);
        boolean exists = redisTemplate.hasKey("expire");
        Assertions.assertFalse(exists);
    }

    @Test
    public void testDelete() {
        ValueOperations<String, User> operations=redisTemplate.opsForValue();
        redisTemplate.opsForValue().set("deletekey", "ityouknow");
        boolean flag = redisTemplate.delete("deletekey");
        Assertions.assertTrue(flag);
        boolean exists=redisTemplate.hasKey("deletekey");
        Assertions.assertFalse(exists);
    }
}
