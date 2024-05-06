package br.com.pongolino.portfolio.spring.ProductsService.infraestructure.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient getRedissonClient(@Value("${redis.server.address}") final String serverProperty,
                                            @Value("${redis.server.port}") final String serverPort) {
        final Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://%s:%s".formatted(serverProperty, serverPort));


        return Redisson.create(config);
    }
}
