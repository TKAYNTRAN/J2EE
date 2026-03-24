package com.example.J2EE.controller;

import com.example.J2EE.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final PatientService patientService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (patientService.existsByUsername(username)) {
            model.addAttribute("error", "Username đã tồn tại!");
            return "register";
        }
        if (patientService.existsByEmail(email)) {
            model.addAttribute("error", "Email đã tồn tại!");
            return "register";
        }

        patientService.register(username, password, email);
        redirectAttributes.addFlashAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
        return "redirect:/login";
    }
}
