package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/products";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("form", new RegisterForm());
        model.addAttribute("roles", Role.values());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute("form") RegisterForm form,
                                 @RequestParam(value = "role", required = false) String role,
                                 Model model) {
        try {
            Role parsedRole = (role == null || role.isBlank()) ? Role.USER : Role.valueOf(role.trim().toUpperCase());
            // Old MVC register: treat username as email for consistency
            userService.register(form.getUsername(), form.getPassword(), parsedRole);
            return "redirect:/login?registered";
        } catch (Exception e) {
            model.addAttribute("roles", Role.values());
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }

    @Getter
    @Setter
    public static class RegisterForm {
        private String username;
        private String password;
    }
}
