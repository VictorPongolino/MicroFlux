package br.com.pongolino.portfolio.spring.ProductsService.application.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


public interface ProductHandler {

    @ApiOperation(value = "Fetch a product by its correspondent ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product successfully fetched"),
            @ApiResponse(code = 404, message = "Product not found"),
    })
    Mono<ServerResponse> findProductById(ServerRequest request);
    @ApiOperation(value = "Create product")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Product successfully created"),
    })
    Mono<ServerResponse> createProduct(ServerRequest request);

    @ApiOperation(value = "Update product")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Product successfully update"),
            @ApiResponse(code = 404, message = "Product not found"),
    })
    Mono<ServerResponse> updateProduct(ServerRequest request);

    @ApiOperation(value = "Delete product")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Product successfully deleted"),
            @ApiResponse(code = 404, message = "Product not found"),
    })
    Mono<ServerResponse> deleteProduct(ServerRequest request);
}
