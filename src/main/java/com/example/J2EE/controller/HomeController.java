package com.example.J2EE.controller;

import com.example.J2EE.entity.Doctor;
import com.example.J2EE.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final DoctorService doctorService;

    @GetMapping({"/", "/home"})
    public String home(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Doctor> doctorPage = doctorService.getDoctors(page, 5);
        model.addAttribute("doctorPage", doctorPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", doctorPage.getTotalPages());
        return "home";
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        List<Doctor> doctors = doctorService.searchByName(keyword);
        model.addAttribute("doctors", doctors);
        model.addAttribute("keyword", keyword);
        return "search";
    }
}
