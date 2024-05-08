package br.com.pongolino.portfolio.spring.ProductsService.application.service.impl;

import br.com.pongolino.portfolio.spring.ProductsService.application.entity.Product;
import br.com.pongolino.portfolio.spring.ProductsService.application.repository.ProductRepository;
import br.com.pongolino.portfolio.spring.ProductsService.application.service.ProductService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final RedissonReactiveClient redissonReactiveClient;
    private RMapCacheReactive<Long, Product> productRedisCache;

    @PostConstruct
    public void startUp() {
        productRedisCache = redissonReactiveClient.getMapCache("products", new TypedJsonJacksonCodec(Long.class, Product.class));
    }

    @Override
    public Mono<Product> getProductById(final Long id) {
        return productRedisCache.get(id).switchIfEmpty(
                productRepository.findById(id).flatMap(product -> {
                    System.out.println("Fetching out-of-cache the product of id " + id);
                    return productRedisCache.fastPut(product.getId(), product, 60, TimeUnit.SECONDS).thenReturn(product);
                })
        );
    }

    @Override
    public Mono<Product> save(final Product product) {
        return productRepository.save(product).flatMap(newProduct -> {
            System.out.println("Updating cache for product id " + newProduct.getId());
            return productRedisCache.replace(product.getId(), product);
        });
    }

    @Override
    public Mono<Void> delete(final Product product) {
        return productRepository.delete(product).doFinally((signalType) -> {
            System.out.println("Deleted product cache with id " + product.getId());
            productRedisCache.remove(product.getId());
        });
    }

//    @Scheduled(fixedRate = 60 * 60 * 2 * 1000)
//    @CacheEvict(value = "product", allEntries = true)
//    public void evictCache() {
//        System.out.println("Evicting all cache data for product!");
//    }
}
