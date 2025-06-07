package org.cm.doan2.controller;

import org.cm.doan2.dto.CommentRequest;
import org.cm.doan2.dto.CommentResponse;
import org.cm.doan2.model.Article;
import org.cm.doan2.model.Comment;
import org.cm.doan2.model.User;
import org.cm.doan2.service.ArticleService;
import org.cm.doan2.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class ApiController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;


    @PostMapping("/addComment")
    public ResponseEntity<?> addComment(@RequestBody CommentRequest req) {
        Article article = articleService.getArticleById(req.getArticleId());
        if (article == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bài viết không tồn tại");
        }

        CommentResponse commentResponse = commentService.addComment(req);
        if (commentResponse != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(commentResponse);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi thêm comment");
    }

}
