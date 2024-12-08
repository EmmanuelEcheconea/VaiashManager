package com.vaiashmanager.db.controller;

import com.vaiashmanager.db.dto.request.CategoryRqDTO;
import com.vaiashmanager.db.dto.response.CategoryRsDTO;
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
        List<CategoryRsDTO> response = this.categoryService.retrieveAllCategory();
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<?> createCategory(@RequestBody final CategoryRqDTO category) {
        CategoryRsDTO response = this.categoryService.createCategory(category);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{idCategory}")
    public ResponseEntity<?> updateCategory(@PathVariable("idCategory") final Long idCategory,
                                            @RequestBody final CategoryRqDTO category) {
        CategoryRsDTO response = this.categoryService.updateCategory(idCategory, category);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{idCategory}")
    public void deleteCategory(@PathVariable("idCategory") final Long idCategory) {
        this.categoryService.deleteCategory(idCategory);
    }
}
