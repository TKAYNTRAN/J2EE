package com.example.demo.controller;

import org.springframework.ui.Model;
import javax.validation.Valid;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public String Index(Model model) {
        model.addAttribute("listproduct", productService.getAll());
        return "product/products";
    }

    @GetMapping("/create")
    public String Create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    @PostMapping("/create")
    public String Create(@Valid Product newProduct, BindingResult result,
                         @RequestParam(value = "category.id", required = false) String categoryId,
                         @RequestParam(value = "imageProduct", required = false) MultipartFile imageProduct,
                         Model model) {

        if (result.hasErrors()) {
            model.addAttribute("product", newProduct);
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }

        if (imageProduct != null && !imageProduct.isEmpty()) {
            productService.updateImage(newProduct, imageProduct); // Xử lý ảnh
        }
        if (categoryId != null && !categoryId.isEmpty()) {
            Category selectedCategory = categoryService.get(categoryId);
            newProduct.setCategory(selectedCategory);
        }
        productService.add(newProduct);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String Edit(@PathVariable String id, Model model) {
        Product find = productService.get(id);
        if (find == null) {
            return "error/404"; // Trang lỗi tùy chỉnh
        }
        model.addAttribute("product", find);
        model.addAttribute("categories", categoryService.getAll());
        return "product/edit";
    }

    @PostMapping("/edit")
    public String Edit(@Valid Product editProduct,
                       BindingResult result,
                       @RequestParam(value = "imageProduct", required = false) MultipartFile imageProduct,
                       @RequestParam(value = "category.id", required = false) String categoryId,
                       Model model) {

        if (result.hasErrors()) {
            model.addAttribute("product", editProduct);
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }

        if (imageProduct != null && !imageProduct.isEmpty()) {
            productService.updateImage(editProduct, imageProduct); // Cập nhật ảnh nếu có
        }

        if (categoryId != null && !categoryId.isEmpty()) {
            Category selectedCategory = categoryService.get(categoryId);
            editProduct.setCategory(selectedCategory);
        }

        productService.update(editProduct); // Cập nhật sản phẩm
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        productService.delete(id);
        return "redirect:/products";
    }
}