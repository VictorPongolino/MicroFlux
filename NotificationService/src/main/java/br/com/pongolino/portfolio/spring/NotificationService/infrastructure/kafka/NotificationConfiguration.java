package br.com.pongolino.portfolio.spring.NotificationService.infrastructure.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "kafka")
public class NotificationConfiguration {
    private String topic;
}
