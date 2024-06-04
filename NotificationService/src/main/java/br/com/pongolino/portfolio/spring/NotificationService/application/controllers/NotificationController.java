package br.com.pongolino.portfolio.spring.NotificationService.application.controllers;

import br.com.pongolino.portfolio.spring.NotificationService.application.dto.NotificationCreationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/notifications")
public interface NotificationController {
    @PostMapping
    ResponseEntity<?> sendNotification(@RequestBody NotificationCreationRequest creationRequest) throws JsonProcessingException;
}
