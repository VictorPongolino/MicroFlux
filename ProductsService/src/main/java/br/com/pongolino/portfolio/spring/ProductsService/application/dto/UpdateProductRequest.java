package br.com.pongolino.portfolio.spring.ProductsService.application.dto;

import br.com.pongolino.portfolio.spring.ProductsService.application.entity.Product;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UpdateProductRequest {

    private String name;
    private String description;
    private int quantity;
    private BigDecimal price;

    public UpdateProductRequest(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("quantity") int quantity,
            @JsonProperty("price") BigDecimal price) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public Product toProduct() {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        return new Product(name, description, price, quantity, createdAt, updatedAt);
    }
}
