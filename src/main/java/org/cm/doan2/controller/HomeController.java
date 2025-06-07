package org.cm.doan2.controller;

import jakarta.servlet.http.HttpSession;
import org.cm.doan2.model.Article;
import org.cm.doan2.model.Category;
import org.cm.doan2.service.ArticleService;
import org.cm.doan2.service.CategoryService;
import org.cm.doan2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    ArticleService articleService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model) {
        List<Article> featuredArticles = articleService.featuredArticles();
        List<Article> articlesNormal = articleService.articlesNormal();
        List<Category> categoriesActicles = categoryService.findAllWithArticles().stream().limit(3).toList();
        Article featuredArticle = featuredArticles.isEmpty() ? null : featuredArticles.get(0);
        List<Article> subFeaturedArticles = articleService.featuredArticles()
                .stream()
                .skip(1)
                .limit(4)
                .toList();
        model.addAttribute("subFeaturedArticles", subFeaturedArticles);
        model.addAttribute("featuredArticle", featuredArticle);
        model.addAttribute("otherArticles", articlesNormal);
        model.addAttribute("categoriesArticles", categoriesActicles);
        return "home";
    }
    @GetMapping("/danhmuc/{slug}")
    public String getArticlesByCategory(@PathVariable String slug, Model model) {
        List<Article> articles = articleService.getArticlesByCategory(slug);
        String nameCate = "";
        if (!articles.isEmpty()) {
            nameCate = articles.get(0).getCategory().getName();
        }
        model.addAttribute("articles", articles);
        model.addAttribute("nameCate", nameCate);
        return "category";
    }
    @GetMapping("/tintuc/{slug}")
    public String tintuc(@PathVariable String slug, Model model) {
        Article article = articleService.getArticleBySlug(slug);
        List<Article> articlesRelation = articleService.getArticleRelation(article.getCategory().getId() , article.getId());
        model.addAttribute("article", article);
        model.addAttribute("articleRelation", articlesRelation);
        return "detail";
    }
    @GetMapping("/tintuc/timkiem")
    public String search(@RequestParam String keyword, Model model) {
        List<Article> result = articleService.serachArticle(keyword);

        model.addAttribute("result", result);
        model.addAttribute("keyword", keyword);

        if (result.isEmpty()) {
            model.addAttribute("message", "Không có bài viết nào!");
        }

        return "timkiem";
    }
}
