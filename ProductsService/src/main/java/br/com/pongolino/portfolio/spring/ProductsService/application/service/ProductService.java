package br.com.pongolino.portfolio.spring.ProductsService.application.service;

import br.com.pongolino.portfolio.spring.ProductsService.application.entity.Product;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public interface ProductService {
    @CacheEvict(value = "product", key = "#id")
    Mono<Product> getProductById(Long id);
    @Transactional
    @CacheEvict(value = "product", key = "#product.id")
    Mono<Product> save(Product product);
}
