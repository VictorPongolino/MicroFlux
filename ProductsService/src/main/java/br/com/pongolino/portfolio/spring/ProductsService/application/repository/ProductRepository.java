package br.com.pongolino.portfolio.spring.ProductsService.application.repository;

import br.com.pongolino.portfolio.spring.ProductsService.application.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
}
