package org.cm.doan2.service;

import org.cm.doan2.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllWithArticles();
    List<Category> getAllCategoriesStatus();
    List<Category> getAllCategories();
    Category getCategoryById(long id);
    boolean updateCategory(Category category);
    boolean createCategory(Category category);
    boolean deleteCategory(long id);
}
