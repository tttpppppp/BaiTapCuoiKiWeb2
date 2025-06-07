package org.cm.doan2.dto;

import lombok.Data;

@Data
public class CommentRequest {
    private Long articleId;
    private Long authorId;
    private String content;
}
