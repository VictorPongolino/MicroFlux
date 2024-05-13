package br.com.pongolino.portfolio.spring.ProductsService.application.controller.impl;


import br.com.pongolino.portfolio.spring.ProductsService.application.controller.ProductHandler;
import br.com.pongolino.portfolio.spring.ProductsService.application.dto.CreateProductRequest;
import br.com.pongolino.portfolio.spring.ProductsService.application.dto.UpdateProductRequest;
import br.com.pongolino.portfolio.spring.ProductsService.application.entity.Product;
import br.com.pongolino.portfolio.spring.ProductsService.application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ProductHandlerImpl implements ProductHandler {

    private final ProductService productService;
    private final UriComponentsBuilder uriComponentsBuilder;
    @Override
    public Mono<ServerResponse> findProductById(final ServerRequest request) {
        final Long productId = Long.parseLong(request.pathVariable("id"));
        return productService.getProductById(productId).flatMap(response -> {
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(response);
        }).switchIfEmpty(ServerResponse.notFound().build());
    }

    @Override
    public Mono<ServerResponse> createProduct(final ServerRequest request) {
        return request.bodyToMono(CreateProductRequest.class).flatMap(productPayload -> {
            final Product product = productPayload.toProduct();

            return productService.save(product).flatMap(response -> {
                URI createdProductResponse = uriComponentsBuilder.path("/{id}").buildAndExpand(response.getId()).toUri();
                return ServerResponse.created(createdProductResponse).build();
            });
        });
    }

    @Override
    public Mono<ServerResponse> updateProduct(final ServerRequest request) {
        return request.bodyToMono(UpdateProductRequest.class).flatMap(productUpdatePayload -> {
            return productService.save(productUpdatePayload.toProduct()).flatMap(response -> {
                return ServerResponse.noContent().build();
            }).switchIfEmpty(ServerResponse.notFound().build());
        });
    }

    @Override
    public Mono<ServerResponse> deleteProduct(final ServerRequest request) {
        final Long productId = Long.parseLong(request.pathVariable("id"));
        return productService.getProductById(productId)
                .flatMap(productService::delete)
                .flatMap(response -> ServerResponse.noContent().build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
