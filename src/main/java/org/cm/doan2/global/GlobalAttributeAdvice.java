package org.cm.doan2.global;


import org.cm.doan2.model.Category;
import org.cm.doan2.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalAttributeAdvice {

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public List<Category> populateCategories() {
        return categoryService.getAllCategoriesStatus();
    }
}
