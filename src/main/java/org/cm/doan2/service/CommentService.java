package org.cm.doan2.service;

import org.cm.doan2.dto.CommentRequest;
import org.cm.doan2.dto.CommentResponse;
import org.cm.doan2.model.Comment;

public interface CommentService {
    CommentResponse addComment(CommentRequest req);
}
