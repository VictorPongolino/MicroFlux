package br.com.pongolino.portfolio.spring.ProductsService.application.entity;

import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Data @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "product_feedback")
public class ProductFeedback {
    private Long id;
    @Column(value = "product_id")
    private Long productId;
    private String title;
    private String content;
    private Integer score;
    @Column(value = "created_at")
    private LocalDateTime createdAt;
    @Column(value = "updated_at")
    private LocalDateTime updatedAt;
    @Column(value = "is_visible")
    private boolean visible = false;

    public ProductFeedback(Long productId, String title, String content, Integer score, LocalDateTime createdAt, LocalDateTime updatedAt, boolean visible, List<FeedbackCommentary> commentaries) {
        this.productId = productId;
        this.title = title;
        this.content = content;
        this.score = score;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.visible = visible;
        this.commentaries = commentaries;
    }

    @Transient
    private List<FeedbackCommentary> commentaries = new ArrayList<>();


}

