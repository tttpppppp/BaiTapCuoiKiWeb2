package org.cm.doan2.service;


import org.cm.doan2.model.Article;

import java.util.List;

public interface ArticleService {
    List<Article> featuredArticles();
    List<Article> articlesNormal();
    List<Article> getArticlesByCategory(String slug);
    Article getArticleBySlug(String slug);
    List<Article> serachArticle(String keyword);
    boolean addArticle(Article article);
    List<Article> getArticleRelation(long cateid , long articleid);
    List<Article> getAllArticles();
    Article getArticleById(long id);
    boolean updateArticle(Article article);
    boolean deleteArticle(long id);
}
