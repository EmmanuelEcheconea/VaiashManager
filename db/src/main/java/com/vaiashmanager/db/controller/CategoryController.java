package com.vaiashmanager.db.controller;

import com.vaiashmanager.db.dto.CategoryFiltersRq;
import com.vaiashmanager.db.entity.Category;
import com.vaiashmanager.db.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    private CategoryService categoryService;
    @Autowired
    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("")
    public List<Category> retrieveAllCategory() {
        List<Category> response = this.categoryService.retrieveAllCategoria();
        return response;
    }

    @PostMapping("")
    public Category createCategory(@RequestBody final Category category) {
        return this.categoryService.createCategoria(category);
    }

    @PutMapping("{idCategory}")
    public Category updateCategory(@PathVariable("idCategory") final Long idCategory, @RequestBody final Category category) {
        return this.categoryService.updateCategoria(idCategory, category);
    }

    @DeleteMapping("{idCategory}")
    public void deleteCategory(@PathVariable("idCategory") final Long idCategory) {
        this.categoryService.deleteCategoria(idCategory);
    }

    @GetMapping("/category")
    public Page<Category> listCategory(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return categoryService.getCategorias(pageable);
    }

    @PostMapping("/filters")
    public List<Category> filterCategory(@RequestBody CategoryFiltersRq categoryFiltersRq) {
        return this.categoryService.filters(categoryFiltersRq);
    }
}
