package com.zwh.website.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ZhangWeihui
 * @date 2019/11/18 17:00
 */
@Component
public class OSSUtils {

    @Autowired
    private RedisService redisService;

    private OSSConfig ossConfig;

    public OSSConfig getOSSConfig() {
        if(ossConfig==null){
            ossConfig = new OSSConfig();
            Map<String,Object> map = redisService.hashGetAll("aliyun-oss-config");
            ossConfig.setEndpoint((String) map.get("endpoint"));
            ossConfig.setAccessKeyID((String) map.get("accessKey"));
            ossConfig.setGetAccessKeySecret((String) map.get("accessKeySecret"));
        }
        return ossConfig;
    }

    @Getter
    @Setter
    public static class OSSConfig {
        private String endpoint;
        private String accessKeyID;
        private String getAccessKeySecret;
    }
}
