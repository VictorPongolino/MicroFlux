package br.com.pongolino.portfolio.spring.ProductsService.application.service;

import br.com.pongolino.portfolio.spring.ProductsService.application.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public interface ProductService {
    Mono<Product> getProductById(Long id);
    @Transactional
    Mono<Product> createProduct(Product product);
}
