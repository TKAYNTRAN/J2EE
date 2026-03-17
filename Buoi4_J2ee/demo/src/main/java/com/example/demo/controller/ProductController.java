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

    // Keep legacy route but redirect to the required endpoint.
    @GetMapping("/create")
    public String Create() {
        return "redirect:/products/add";
    }

    // Per assignment: GET /products/add shows create form
    @GetMapping("/add")
    public String Add(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    // Per assignment: POST /products/add creates product
    @PostMapping("/add")
    public String Add(@Valid Product newProduct, BindingResult result,
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

    // Per assignment: GET /products/edit?id=...
    @GetMapping("/edit")
    public String Edit(@RequestParam("id") String id, Model model) {
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

    // Per assignment: GET /products/delete?id=...
    @GetMapping("/delete")
    public String delete(@RequestParam("id") String id) {
        productService.delete(id);
        return "redirect:/products";
    }
}
