package com.example.demo.service;

import com.example.demo.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category get(String id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElse(null);
    }

    public void add(Category newCategory) {
        categoryRepository.save(newCategory);
    }

    public void update(Category editCategory) {
        Category found = get(editCategory.getId());
        if (found != null) {
            found.setName(editCategory.getName());
            categoryRepository.save(found);
        }
    }

    public void delete(String id) {
        categoryRepository.deleteById(id);
    }
}
