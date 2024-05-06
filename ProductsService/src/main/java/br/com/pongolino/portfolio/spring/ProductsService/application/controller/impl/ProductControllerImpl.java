package br.com.pongolino.portfolio.spring.ProductsService.application.controller.impl;


import br.com.pongolino.portfolio.spring.ProductsService.application.controller.ProductController;
import br.com.pongolino.portfolio.spring.ProductsService.application.dto.CreateProductRequest;
import br.com.pongolino.portfolio.spring.ProductsService.application.entity.Product;
import br.com.pongolino.portfolio.spring.ProductsService.application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;
    @Override
    public Mono<Product> findProductById(final Long id) {
        return productService.getProductById(id);
    }

    @Override
    public Mono<Product> createProduct(final CreateProductRequest request) {
        return productService.createProduct(request.toProduct());
    }
}
