package org.cm.doan2.service.impl;

import com.github.slugify.Slugify;
import org.cm.doan2.model.Category;
import org.cm.doan2.repository.CategoryRepository;
import org.cm.doan2.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<Category> findAllWithArticles() {
        List<Category> categories = categoryRepository.findAllWithArticles();
        return categories;
    }

    @Override
    public List<Category> getAllCategoriesStatus() {
        List<Category> categories = categoryRepository.findByStatusTrue();
        return categories;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateCategory(Category category) {
        Optional<Category> existing = categoryRepository.findById(category.getId());
        if (existing.isPresent()) {
            categoryRepository.save(category);
            return true;
        }
        return false;
    }


    @Override
    public boolean createCategory(Category category) {
        try{
            Slugify slugify = new Slugify();
            category.setSlug(slugify.slugify(category.getName()));
            categoryRepository.save(category);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean deleteCategory(long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        category.setStatus(false);
        categoryRepository.save(category);
        return true;
    }
}
