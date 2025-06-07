package org.cm.doan2.service.impl;

import com.github.slugify.Slugify;
import jakarta.servlet.http.HttpSession;
import org.cm.doan2.common.SessionUser;
import org.cm.doan2.model.Article;
import org.cm.doan2.model.User;
import org.cm.doan2.repository.ArticleRepository;
import org.cm.doan2.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImp implements ArticleService {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    private HttpSession httpSession;

    @Override
    public List<Article> featuredArticles() {
        List<Article> articles = articleRepository.findByIsFeaturedTrueAndStatusTrueOrderByCreatedAtDesc();
        return articles;
    }

    @Override
    public List<Article> articlesNormal() {
        List<Article> articles = articleRepository.findByIsFeaturedFalseAndStatusTrueOrderByCreatedAtDesc();
        return articles;
    }

    @Override
    public List<Article> getArticlesByCategory(String slug) {
       return articleRepository.findByCategorySlugWithCategory(slug);
    }

    @Override
    public Article getArticleBySlug(String slug) {
        return articleRepository.findArticleBySlug(slug);
    }

    @Override
    public List<Article> serachArticle(String keyword) {
        return articleRepository.searchByKeyword(keyword);
    }

    @Override
    public boolean addArticle(Article article) {
        try{
            articleRepository.save(article);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public List<Article> getArticleRelation(long cateid, long articleid) {
        return articleRepository.findRelatedArticles(cateid , articleid);
    }

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public Article getArticleById(long id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        return optionalArticle.orElse(null);
    }

    @Override
    public boolean updateArticle(Article article) {
        if (article.getId() == null) {
            return false;
        }
        Slugify slugify = new Slugify();

        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User author = new User();
        author.setId(sessionUser.getId());

        String slug = slugify.slugify(article.getTitle());
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());
        article.setSlug(slug);
        article.setAuthor(author);
        Optional<Article> optionalArticle = articleRepository.findById(article.getId());
        if (optionalArticle.isPresent()) {
            Article existingArticle = optionalArticle.get();
            existingArticle.setTitle(article.getTitle());
            existingArticle.setDescription(article.getDescription());
            existingArticle.setContent(article.getContent());
            existingArticle.setImage(article.getImage());
            existingArticle.setCategory(article.getCategory());
            existingArticle.setStatus(article.isStatus());
            existingArticle.setFeatured(article.isFeatured());
            articleRepository.save(existingArticle);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteArticle(long id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isPresent()) {
            optionalArticle.get().setStatus(false);
            articleRepository.save(optionalArticle.get());
        }
        return true;
    }

}
