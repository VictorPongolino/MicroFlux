package br.com.pongolino.demo.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(final ServerHttpSecurity serverHttpSecurity) {
        return serverHttpSecurity
                .authorizeExchange(exchanges -> {
                    exchanges.pathMatchers("/api/demo").hasRole("DEMO");
                    exchanges.pathMatchers("/api/admin").hasRole("ADMIN");
                    exchanges.anyExchange().authenticated();
                }).oauth2ResourceServer(securityOAuth2Specs -> {
                    securityOAuth2Specs.jwt(jwtSpec -> {
                        jwtSpec.jwtAuthenticationConverter(createJwtGrantAuthorityAdapter());
                    });
                })
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
    }

    private Converter<Jwt, Mono<AbstractAuthenticationToken>> createJwtGrantAuthorityAdapter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new JwtAuthRolesConverter());

        return new ReactiveJwtAuthenticationConverterAdapter(converter);
    }
}


