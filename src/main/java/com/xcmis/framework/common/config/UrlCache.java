package com.xcmis.framework.common.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 功能
 *
 * @author
 * @see
 */

@Configuration
public class UrlCache {
    @Bean
    public Cache<String, Integer> getCache() {
        //return CacheBuilder.newBuilder().expireAfterWrite(2L, TimeUnit.SECONDS).build();// 缓存有效期为2秒
        return CacheBuilder.newBuilder().build();// 不设置缓存有效期
    }
}
