package com.vaiashmanager.db.controller;

import com.vaiashmanager.db.entity.Category;
import com.vaiashmanager.db.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    private final CategoryService categoryService;
    @Autowired
    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("")
    public ResponseEntity<?> retrieveAllCategory() {
        List<Category> response = this.categoryService.retrieveAllCategoria();
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<?> createCategory(@RequestBody final Category category) {
        Category response = this.categoryService.createCategoria(category);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{idCategory}")
    public ResponseEntity<?> updateCategory(@PathVariable("idCategory") final Long idCategory, @RequestBody final Category category) {
        Category response = this.categoryService.updateCategoria(idCategory, category);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{idCategory}")
    public void deleteCategory(@PathVariable("idCategory") final Long idCategory) {
        this.categoryService.deleteCategoria(idCategory);
    }
}
