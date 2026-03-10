package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "category/categories";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("category", new Category());
        return "category/create";
    }

    @PostMapping("/create")
    public String create(@Valid Category newCategory, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("category", newCategory);
            return "category/create";
        }
        categoryService.add(newCategory);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Category category = categoryService.get(id);
        if (category == null) {
            return "error/404";
        }
        model.addAttribute("category", category);
        return "category/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid Category editCategory, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("category", editCategory);
            return "category/edit";
        }
        categoryService.update(editCategory);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        categoryService.delete(id);
        return "redirect:/categories";
    }
}
