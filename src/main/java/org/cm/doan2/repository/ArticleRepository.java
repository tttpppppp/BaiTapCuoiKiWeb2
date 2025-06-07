package org.cm.doan2.repository;

import org.cm.doan2.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByIsFeaturedTrueAndStatusTrueOrderByCreatedAtDesc();

    List<Article> findByIsFeaturedFalseAndStatusTrueOrderByCreatedAtDesc();

    @Query("SELECT a FROM Article a JOIN FETCH a.category c WHERE c.slug = :slug AND a.status = true")
    List<Article> findByCategorySlugWithCategory(@Param("slug") String slug);

    @Query("SELECT a FROM Article a JOIN FETCH a.category WHERE a.slug = :slug AND a.status = true")
    Article findArticleBySlug(@Param("slug") String slug);

    @Query("SELECT a FROM Article a WHERE a.status = true AND " +
            "(LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(a.content) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Article> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT a FROM Article a JOIN a.category c WHERE c.id = :categoryId " +
            "AND a.id <> :articleId AND a.status = true ORDER BY a.createdAt DESC")
    List<Article> findRelatedArticles(@Param("categoryId") long categoryId, @Param("articleId") long articleId);
}
