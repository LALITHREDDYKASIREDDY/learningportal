package com.example.LearningPortal.repository;

import java.util.ArrayList;

import com.example.LearningPortal.model.Category;

public interface CategoryRepository {
	ArrayList<Category> getCategories();

	Category getCategoryById(int id);

	Category addCategory(Category category);

	Category updateCategory(Category category, int id);

	void deleteCategory(int id);

}
