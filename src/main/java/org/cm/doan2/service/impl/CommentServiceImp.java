package org.cm.doan2.service.impl;

import org.cm.doan2.dto.CommentRequest;
import org.cm.doan2.dto.CommentResponse;
import org.cm.doan2.model.Article;
import org.cm.doan2.model.Comment;
import org.cm.doan2.model.User;
import org.cm.doan2.repository.CommentRepository;
import org.cm.doan2.repository.UserRepository;
import org.cm.doan2.service.ArticleService;
import org.cm.doan2.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImp implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserRepository userRepository;


    @Override
    public CommentResponse addComment(CommentRequest req) {
        try {
            Article article = articleService.getArticleById(req.getArticleId());
            User user = userRepository.findById(req.getAuthorId()).orElse(null);
            if (article == null || user == null) {
                return null;
            }

            Comment comment = new Comment();
            comment.setUser(user);
            comment.setContent(req.getContent());
            comment.setArticle(article);
            comment.setCreatedAt(LocalDateTime.now());

            commentRepository.save(comment);

            return new CommentResponse(
                    user.getUsername(),
                    comment.getContent(),
                    comment.getCreatedAt()
            );
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm bình luận: " + e.getMessage());
            return null;
        }
    }

}
