package com.zwh.website.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * @author ZhangWeihui
 * @date 2019/11/7 16:37
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    public KeyGenerator keyGenerator() {
        return (Object target, Method method, Object... params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName()).append("#");
            sb.append(method.getName()).append("[");
            for (int i=0; i<params.length; i++) {
                sb.append(params[i].toString());
                if(i!=params.length-1){
                    sb.append(",");
                }
            }
            sb.append("]");
            return sb.toString();
        };
    }
}
