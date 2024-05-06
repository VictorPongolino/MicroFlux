package br.com.pongolino.portfolio.spring.ProductsService.application.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "feedback_commentary")
@NoArgsConstructor @AllArgsConstructor @Data
public class FeedbackCommentary {
    @Id
    private Long id;
    private String message;
    @Column(value = "is_product_owner")
    private boolean isProductOwner;
    @Column(value = "is_visible")
    private boolean isVisible;
    @Column(value = "created_at")
    private LocalDateTime createdAt;
    @Column(value = "updated_at")
    private LocalDateTime updatedAt;
}
