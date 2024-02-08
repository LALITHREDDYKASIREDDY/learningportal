package com.example.LearningPortal.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.LearningPortal.model.Category;
import com.example.LearningPortal.service.CategoryService;

/**
 * Controller class responsible for handling HTTP requests related to categories.
 * Exposes endpoints for CRUD operations on categories.
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {

	// Logger for logging
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	CategoryService categoryService;

	// Endpoint to retrieve all categories
	// Only admins are allowed to access this endpoint
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping()
	public ArrayList<Category> getCategories() {
		logger.info("Fetching all categories");
		return categoryService.getCategories();
	}

	// Endpoint to retrieve a category by ID
	// Only admins are allowed to access this endpoint
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/{categoryId}")
	public Category getCategoryById(@PathVariable("categoryId") int id) {
		logger.info("Fetching category by ID: {}", id);
		return categoryService.getCategoryById(id);
	}

	// Endpoint to add a new category
	// Only admins are allowed to access this endpoint
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping
	public Category addCategory(@RequestBody Category category) {
		logger.info("Adding new category: {}", category);
		return categoryService.addCategory(category);
	}

	// Endpoint to update a category by ID
	// Only admins are allowed to access this endpoint
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/{categoryId}")
	public Category updateCategoryById(@PathVariable("categoryId") int id, @RequestBody Category category) {
		logger.info("Updating category with ID {}: {}", id, category);
		return categoryService.updateCategory(category, id);
	}

	// Endpoint to delete a category by ID
	// Only admins are allowed to access this endpoint
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/{categoryId}")
	public void deleteCategoryById(@PathVariable("categoryId") int id) {
		logger.info("Deleting category with ID: {}", id);
		categoryService.deleteCategory(id);
	}
}