package com.zwh.website;

import com.zwh.website.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;
import java.util.Map;
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

    /**
     *  对 Hash 的操作
     */
    @Test
    public void testHash() {
        StringRedisSerializer srs = new StringRedisSerializer();
        redisTemplate.setKeySerializer(srs);
        redisTemplate.setHashKeySerializer(srs);
        redisTemplate.setHashValueSerializer(srs);
        redisTemplate.setValueSerializer(srs);
        HashOperations<String, String, String> ho = redisTemplate.opsForHash();
//        String key = "province";
//        ho.put(key, "beijing", "001");
//        String value = ho.get(key, "beijing");
//        Assertions.assertEquals("001", value);
//
//        ho.putIfAbsent(key, "beijing", "002");
//        value = ho.get(key, "beijing");
//        Assertions.assertEquals("001", value);
//
//        ho.putIfAbsent(key, "shanghai", "002");
//        value = ho.get(key, "shanghai");
//        Assertions.assertEquals("002", value);

        Map<String,String> map = ho.entries("aliyun-oss-config");
        for(Map.Entry<String,String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " ----> " + entry.getValue());
        }

        String val = ho.get("aliyun-oss-config", "endpoint");
        System.out.println(val);
    }

    /**
     * 对 List 的操作
     */
    @Test
    public void testList() {
        ListOperations<String, String> list = redisTemplate.opsForList();
        List<String> elements = list.range("list", 0,-1);
        elements.stream().forEach(e -> System.out.print(e + ", "));
        list.leftPush("list","it");
        list.leftPush("list","you");
        list.leftPush("list","know");
        String value = list.leftPop("list");
        Assertions.assertEquals("know", value);
        value = list.rightPop("list");
        Assertions.assertEquals("it", value);

        list.leftPush("list", "facial");
        list.leftPush("list", "tissues");
        list.leftPush("list", "mind");
        elements = list.range("list", 1,4);
        Assertions.assertEquals(4, elements.size());
        Assertions.assertArrayEquals(new String[]{"tissues","facial","know"}, elements.toArray());
    }
}
