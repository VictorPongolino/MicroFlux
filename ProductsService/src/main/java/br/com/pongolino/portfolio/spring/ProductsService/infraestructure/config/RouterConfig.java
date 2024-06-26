package br.com.pongolino.portfolio.spring.ProductsService.infraestructure.config;

import br.com.pongolino.portfolio.spring.ProductsService.application.controller.ProductHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class RouterConfig {
    private final ProductHandler productHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .path("/api/products", builder -> {
                    builder
                            .POST(productHandler::createProduct)
                            .GET("/{id}", productHandler::findProductById)
                            .PUT("/{id}", productHandler::updateProduct)
                            .DELETE("/{id}", productHandler::deleteProduct);
                })
                .onError(Throwable.class, handleGenericException())
                .build();
    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> handleGenericException() {
        return (exception, serverRequest) -> {
            return ServerResponse.badRequest().build();
        };
    }
}
