package com.orlov.restaurantservice.Config;

import org.redisson.Redisson;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

//
//    @Bean(destroyMethod="shutdown")
//    RedissonClient redisson() throws IOException {
//        Config config = Config.fromYAML(new ClassPathResource("redisson.yaml").getInputStream());
//        return Redisson.create(config);
//    }


//    @Bean
//    CacheManager cacheManager(RedissonClient redissonClient) throws IOException {
//
//        Map<String, CacheConfig> config = new HashMap<String, CacheConfig>();
//
//        // define local cache settings for "testMap" cache.
//        // ttl = 48 minutes and maxIdleTime = 24 minutes for local cache entries
//        LocalCachedMapOptions options = LocalCachedMapOptions.defaults()
//                .evictionPolicy(EvictionPolicy.LFU)
//                .timeToLive(48, TimeUnit.MINUTES)
//                .maxIdle(24, TimeUnit.MINUTES);
//                .cacheSize(1000);
//
//        // create "testMap" Redis cache with ttl = 24 minutes and maxIdleTime = 12 minutes
//        LocalCachedCacheConfig cfg = new LocalCachedCacheConfig(24*60*1000, 12*60*1000, options);
//        // Max size of map stored in Redis
//        cfg.setMaxSize(2000);
//        config.put("testMap", cfg);
//        return new RedissonSpringLocalCachedCacheManager(redissonClient, config);
//
//    }
}
