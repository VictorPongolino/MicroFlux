package br.com.pongolino.portfolio.spring.ProductsService.application.service;

import br.com.pongolino.portfolio.spring.ProductsService.application.entity.ProductFeedback;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public interface ProductFeedbackService {

    @Transactional
    Mono<ProductFeedback> save(ProductFeedback productFeedback);
}
