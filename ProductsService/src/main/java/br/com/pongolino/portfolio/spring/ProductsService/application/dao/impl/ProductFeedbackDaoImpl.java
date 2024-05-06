package br.com.pongolino.portfolio.spring.ProductsService.application.dao.impl;

import br.com.pongolino.portfolio.spring.ProductsService.application.dao.ProductFeedbackDao;
import br.com.pongolino.portfolio.spring.ProductsService.application.entity.ProductFeedback;
import io.r2dbc.spi.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductFeedbackDaoImpl implements ProductFeedbackDao {
    private final ConnectionFactory connectionFactory;

    /*
        This class I was interested in practicing usage of raw DB calls. Other versions would include
        usage of ReactiveCrudRepository.
     */

    @Override
    public Mono<ProductFeedback> save(final ProductFeedback productFeedback) {
        return createConnectionWithTransaction().flatMap(connection -> {
            return checkProductFeedbackExistsById(connection, productFeedback.getId()).flatMap(result -> {
                return updateProductFeedback(connection, productFeedback).then(Mono.just(productFeedback));
            }).switchIfEmpty(
                createProductFeedbackInsertionStatement(connection, productFeedback).flatMap(result -> {
                    return Mono.from(result.map((row, metadata) -> createProductFeedbackFromDatabaseResult(row)));
                })
            );
        });
    }

    private Mono<?> checkProductFeedbackExistsById(final Connection connection, final Long id) {
        final String sql = "SELECT 1 FROM product_feedback WHERE id = $1";
        return Mono.from(connection.createStatement(sql).bind("$1", id).execute());
    }

    private Mono<?> updateProductFeedback(final Connection connection, final ProductFeedback productFeedback) {
        return Mono.from(connection.createStatement(
                        "UPDATE product_feedback " +
                                "SET product_id = $1, title = $2, content = $3, score = $4, created_at = $5, " +
                                "updated_at = $6, is_visible = $7 WHERE id = $8")
                .bind("$1", productFeedback.getProductId())
                .bind("$2", productFeedback.getTitle())
                .bind("$3", productFeedback.getContent())
                .bind("$4", productFeedback.getScore())
                .bind("$5", productFeedback.getCreatedAt())
                .bind("$6", productFeedback.getUpdatedAt())
                .bind("$7", productFeedback.isVisible())
                .bind("$8", productFeedback.getId())
                .execute());
    }

    private Mono<Connection> createConnectionWithTransaction() {
        return Mono.from(connectionFactory.create()).flatMap(connection -> {
            var connectionMono = Mono.just(connection).doFinally(r -> connection.close());
            return createTransaction(connection).then(connectionMono);
        });
    }

    private Mono<Void> createTransaction(final Connection connection) {
        return Mono.from(connection.beginTransaction())
                .doOnError(e -> Mono.from(connection.rollbackTransaction()).then(Mono.error(e)))
                .delayUntil(r -> connection.commitTransaction());
    }

    private Mono<? extends Result> createProductFeedbackInsertionStatement(final Connection c, final ProductFeedback productFeedback) {
        return Mono.from(c.createStatement(
                        "INSERT INTO product_feedback (product_id, title, content, score, created_at, updated_at, is_visible) " +
                                "VALUES ($1, $2, $3, $4, $5, $6, $7)")
                .bind("$1", productFeedback.getProductId())
                .bind("$2", productFeedback.getTitle())
                .bind("$3", productFeedback.getContent())
                .bind("$4", productFeedback.getScore())
                .bind("$5", productFeedback.getCreatedAt())
                .bind("$6", productFeedback.getUpdatedAt())
                .bind("$7", productFeedback.isVisible())
                .execute());
    }

    private ProductFeedback createProductFeedbackFromDatabaseResult(final Row row) {
        return ProductFeedback.builder()
                .id(row.get("id", Long.class))
                .productId(row.get("productId", Long.class))
                .title(row.get("title", String.class))
                .score(row.get("score", Integer.class))
                .content(row.get("content", String.class))
                .visible(row.get("is_visible", Boolean.class))
                .createdAt(row.get("created_at", LocalDateTime.class))
                .updatedAt(row.get("updated_at", LocalDateTime.class))
                .build();
    }
}
