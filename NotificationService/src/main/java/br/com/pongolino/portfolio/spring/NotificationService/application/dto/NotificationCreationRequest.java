package br.com.pongolino.portfolio.spring.NotificationService.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NotificationCreationRequest(
        @JsonProperty("device_type") String deviceType,
        @JsonProperty("payload") String payload) {
}
