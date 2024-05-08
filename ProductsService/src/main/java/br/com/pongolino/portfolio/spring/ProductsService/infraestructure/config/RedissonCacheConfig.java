package br.com.pongolino.portfolio.spring.ProductsService.infraestructure.config;

import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@EnableCaching
public class RedissonCacheConfig {

    @Bean
    public CacheManager cacheManager(final RedissonClient redisClient) {
        return new RedissonSpringCacheManager(redisClient);
    }
}
