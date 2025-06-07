package org.cm.doan2.controller;

import com.github.slugify.Slugify;
import jakarta.servlet.http.HttpSession;
import org.cm.doan2.common.SessionUser;
import org.cm.doan2.model.Article;
import org.cm.doan2.model.Category;
import org.cm.doan2.model.User;
import org.cm.doan2.service.ArticleService;
import org.cm.doan2.service.CategoryService;
import org.cm.doan2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    CategoryService categoryService;
    
    @Autowired
    ArticleService articleService;
    
    @Autowired
    UserService userService;

    public boolean isAdminLogin(HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        return sessionUser == null || !"ADMIN".equals(sessionUser.getRole());
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if (isAdminLogin(session)) {
            return "redirect:/dangnhap";
        }
        model.addAttribute("countBaiViet" , articleService.getAllArticles().size());
        model.addAttribute("countUser" , userService.getAllUsers().size());
        return "admin/dashboard";
    }

    @GetMapping("/baiviet/add")
    public String baiviet(Model model, HttpSession session) {
        if (isAdminLogin(session)) {
            return "redirect:/dangnhap";
        }
        model.addAttribute("article", new Article());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/addBaiViet";
    }

    @GetMapping("/baiviet/list")
    public String baivietList(Model model, HttpSession session) {
        if (isAdminLogin(session)) {
            return "redirect:/dangnhap";
        }
        List<Article> articles = articleService.getAllArticles();
        model.addAttribute("articles", articles);
        return "admin/danhsachbaiviet";
    }

    @PostMapping("/baiviet/add")
    public String addArticle(@ModelAttribute Article article,
                             HttpSession session, Model model,
                             RedirectAttributes redirectAttributes
    ) {
        if (isAdminLogin(session)) {
            return "redirect:/dangnhap";
        }
        if (articleService.addArticle(article)) {
            redirectAttributes.addFlashAttribute("message", "Thêm bài viết thành công!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Thêm bài viết thành công!");
        }
        return "redirect:/baiviet/list";
    }

    @GetMapping("/baiviet/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
        if (isAdminLogin(session)) {
            return "redirect:/dangnhap";
        }
        Article article = articleService.getArticleById(id);
        if (article == null) {
            return "redirect:/baiviet/list";
        }
        model.addAttribute("article", article);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/suabaiviet";
    }

    @PostMapping("/baiviet/edit/{id}")
    public String updateArticle(@PathVariable Long id,
                                @ModelAttribute("article") Article article,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        if (isAdminLogin(session)) {
            return "redirect:/dangnhap";
        }
        boolean isSuccess = articleService.updateArticle(article);
        if (isSuccess) {
            redirectAttributes.addFlashAttribute("message", "Cập nhật bài viết thành công!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Cập nhật thất bại!");
        }

        return "redirect:/baiviet/list";
    }
    @GetMapping("/baiviet/delete/{id}")
    public String deleteBaiViet(@PathVariable long id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (isAdminLogin(session)) {
            return "redirect:/dangnhap";
        }
        boolean isSuccess = articleService.deleteArticle(id);
        if (isSuccess) {
            redirectAttributes.addFlashAttribute("message", "Xóa bài viết thành công!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Xóa thất bại!");
        }
        return "redirect:/baiviet/list";
    }
    @GetMapping("/danhmuc/list")
    public String danhMucList(Model model, HttpSession session) {
        if (isAdminLogin(session)) {
            return "redirect:/dangnhap";
        }
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "admin/danhmuc";
    }

    @GetMapping("/danhmuc/edit/{id}")
    public String suaDanhMuc(@PathVariable long id, Model model, HttpSession session) {
        if (isAdminLogin(session)) {
            return "redirect:/dangnhap";
        }
        Category cate = categoryService.getCategoryById(id);
        model.addAttribute("category", cate);
        return "admin/suadanhmuc";
    }

    @PostMapping("/danhmuc/edit/{id}")
    public String suaDanhMucPost(
            @ModelAttribute("category") Category category,
            RedirectAttributes redirectAttributes, HttpSession session) {
        if (isAdminLogin(session)) {
            return "redirect:/dangnhap";
        }
        boolean isSuccess = categoryService.updateCategory(category);
        if (isSuccess) {
            redirectAttributes.addFlashAttribute("message", "Cập nhật danh mục thành công!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Cập nhật thất bại!");
        }
        return "redirect:/danhmuc/list";
    }

    @GetMapping("/danhmuc/add")
    public String danhMucAdd(Model model, HttpSession session) {
        if (isAdminLogin(session)) {
            return "redirect:/dangnhap";
        }
        model.addAttribute("category", new Category());
        return "admin/addDanhMuc";
    }

    @PostMapping("/danhmuc/add")
    public String danhMucAddPost(RedirectAttributes redirectAttributes, HttpSession session, @ModelAttribute("category") Category category) {
        if (isAdminLogin(session)) {
            return "redirect:/dangnhap";
        }
        boolean isSuccess = categoryService.createCategory(category);
        if (isSuccess) {
            redirectAttributes.addFlashAttribute("message", "Thêm danh mục thành công!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Cập nhật thất bại!");
        }
        return "redirect:/danhmuc/list";
    }

    @GetMapping("/danhmuc/delete/{id}")
    public String deleteDanhmuc(@PathVariable long id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (isAdminLogin(session)) {
            return "redirect:/dangnhap";
        }
        boolean isSuccess = categoryService.deleteCategory(id);
        if (isSuccess) {
            redirectAttributes.addFlashAttribute("message", "Xóa danh mục thành công!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Xóa thất bại!");
        }
        return "redirect:/danhmuc/list";
    }
}
