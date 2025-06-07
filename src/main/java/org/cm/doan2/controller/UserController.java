package org.cm.doan2.controller;

import jakarta.servlet.http.HttpSession;
import org.cm.doan2.common.SessionUser;
import org.cm.doan2.dto.UserDTO;
import org.cm.doan2.model.User;
import org.cm.doan2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/dangnhap")
    public String login() {
        return "login";
    }
    @PostMapping("/dangnhap")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {
        User user = userService.checkLogin(email, password);
        if (user != null) {
            SessionUser sessionUser = new SessionUser();
            sessionUser.setEmail(user.getEmail());
            sessionUser.setRole(user.getRole().getName());
            sessionUser.setId(user.getId());
            session.setAttribute("user", sessionUser);
            return "redirect:/";
        }
        model.addAttribute("error", "Email hoặc mật khẩu không đúng");
        return "login";
    }

    @GetMapping("/dangki")
    public String register(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register";
    }
    @GetMapping("/dangxuat")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/dangnhap";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") UserDTO userDTO , Model model , RedirectAttributes redirectAttributes) {
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            model.addAttribute("error", "Mật khẩu không khớp");
            return "signup";
        }
        boolean isSuccess = userService.registerUser(userDTO);
        if (isSuccess) {
            redirectAttributes.addFlashAttribute("message", "Tạo tài khoản thành công!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Cập nhật thất bại!");
        }
        return "redirect:/dangnhap";
    }
}
