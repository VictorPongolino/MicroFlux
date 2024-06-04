package br.com.pongolino.portfolio.spring.NotificationService.application.controllers.impl;

import br.com.pongolino.portfolio.spring.NotificationService.application.controllers.NotificationController;
import br.com.pongolino.portfolio.spring.NotificationService.application.dto.NotificationCreationRequest;
import br.com.pongolino.portfolio.spring.NotificationService.application.producers.NotificationProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationControllerImpl implements NotificationController {
    private final NotificationProducer notificationProducer;

    @Override
    public ResponseEntity<NotificationCreationRequest> sendNotification(final NotificationCreationRequest creationRequest) throws JsonProcessingException {
        notificationProducer.send(creationRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(creationRequest);
    }
}

