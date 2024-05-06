package br.com.pongolino.portfolio.spring.ProductsService.application.service.impl;

import br.com.pongolino.portfolio.spring.ProductsService.application.entity.Product;
import br.com.pongolino.portfolio.spring.ProductsService.application.repository.ProductRepository;
import br.com.pongolino.portfolio.spring.ProductsService.application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    @Override
    public Mono<Product> getProductById(final Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Mono<Product> createProduct(final Product product) {
        return productRepository.save(product);
    }
}
