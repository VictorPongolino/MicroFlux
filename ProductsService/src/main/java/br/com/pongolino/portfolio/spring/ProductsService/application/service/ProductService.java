package br.com.pongolino.portfolio.spring.ProductsService.application.service;

import br.com.pongolino.portfolio.spring.ProductsService.application.entity.Product;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public interface ProductService {
    Mono<Product> getProductById(Long id);
    @Transactional
    Mono<Product> save(Product product);
    @Transactional
    Mono<Void> delete(Product product);
}
