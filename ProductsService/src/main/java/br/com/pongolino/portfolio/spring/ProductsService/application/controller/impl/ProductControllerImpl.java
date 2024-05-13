package br.com.pongolino.portfolio.spring.ProductsService.application.controller.impl;


import br.com.pongolino.portfolio.spring.ProductsService.application.controller.ProductController;
import br.com.pongolino.portfolio.spring.ProductsService.application.dto.CreateProductRequest;
import br.com.pongolino.portfolio.spring.ProductsService.application.dto.UpdateProductRequest;
import br.com.pongolino.portfolio.spring.ProductsService.application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;
    @Override
    public Mono<ServerResponse> findProductById(final Long id) {
        return productService.getProductById(id).flatMap(response -> {
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(response);
        }).switchIfEmpty(ServerResponse.notFound().build());
    }

    @Override
    public Mono<ServerResponse> createProduct(final CreateProductRequest request, final UriComponentsBuilder uriComponentsBuilder) {
        return productService.save(request.toProduct()).flatMap(response -> {
            URI createdProductResponse = uriComponentsBuilder.path("/{id}").buildAndExpand(response.getId()).toUri();
            return ServerResponse.created(createdProductResponse).build();
        });
    }

    @Override
    public Mono<ServerResponse> updateProduct(final UpdateProductRequest request) {
        return productService.save(request.toProduct()).flatMap(response -> {
            return ServerResponse.noContent().build();
        }).switchIfEmpty(ServerResponse.notFound().build());
    }

    @Override
    public Mono<ServerResponse> deleteProduct(final Long id) {
        return productService.getProductById(id)
                .flatMap(productService::delete)
                .flatMap(response -> ServerResponse.noContent().build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
