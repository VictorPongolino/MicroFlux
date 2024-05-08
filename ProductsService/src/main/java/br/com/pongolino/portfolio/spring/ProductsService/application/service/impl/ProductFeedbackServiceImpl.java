package br.com.pongolino.portfolio.spring.ProductsService.application.service.impl;

import br.com.pongolino.portfolio.spring.ProductsService.application.dao.ProductFeedbackDao;
import br.com.pongolino.portfolio.spring.ProductsService.application.entity.ProductFeedback;
import br.com.pongolino.portfolio.spring.ProductsService.application.service.ProductFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductFeedbackServiceImpl implements ProductFeedbackService {

    private final ProductFeedbackDao productFeedbackDao;

    @Override
    public Mono<ProductFeedback> save(final ProductFeedback productFeedback) {
        return productFeedbackDao.save(productFeedback);
    }
}
