package br.com.pongolino.portfolio.spring.ProductsService.application.controller;

import br.com.pongolino.portfolio.spring.ProductsService.application.dto.CreateProductRequest;
import br.com.pongolino.portfolio.spring.ProductsService.application.entity.Product;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RequestMapping("/api/products")
public interface ProductController {

    @GetMapping("/{id}")
    @ApiOperation(value = "Fetch a product by its correspondent ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product successfully fetched"),
            @ApiResponse(code = 404, message = "Product not found"),
    })
    Mono<Product> findProductById(@PathVariable("id") Long id);
    @PostMapping
    @ApiOperation(value = "Create product")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Product successfully created"),
    })
    Mono<Product> createProduct(@RequestBody CreateProductRequest request);
}
