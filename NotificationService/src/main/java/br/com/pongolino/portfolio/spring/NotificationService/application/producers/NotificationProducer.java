package br.com.pongolino.portfolio.spring.NotificationService.application.producers;

import br.com.pongolino.portfolio.spring.NotificationService.application.dto.NotificationCreationRequest;
import br.com.pongolino.portfolio.spring.NotificationService.infrastructure.kafka.NotificationConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationProducer {
    private final KafkaTemplate<Integer, String> kafkaTemplate;
    private final NotificationConfiguration notificationConfiguration;
    private final ObjectMapper objectMapper;


    public void send(final NotificationCreationRequest payload) throws JsonProcessingException {
        final String jsonPayload = objectMapper.writeValueAsString(payload);
        final Integer partition = null;
        final Integer key = 1;

        var result = kafkaTemplate.send(createNotificationPayload(partition, key, jsonPayload));
        result.whenComplete((success, exception) -> {
            if (exception != null) {
                log.error("Failed to send message to Kafka. Payload={}, cause={}", jsonPayload, exception.getMessage());
            } else if (success != null) {
                final RecordMetadata recordMetadata = success.getRecordMetadata();
                log.info("Successfully sent message to Kafka! Partition={}, Offset={}, Payload={}",
                        recordMetadata.partition(), recordMetadata.offset(), jsonPayload);
            }
        });
    }

    private ProducerRecord<Integer, String> createNotificationPayload(final Integer partition, final Integer key, final String jsonPayload) {
        return new ProducerRecord<>("notification",
                partition, key, jsonPayload);
    }
}


