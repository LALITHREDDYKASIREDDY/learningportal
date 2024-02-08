package com.example.LearningPortal.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; // Import logger-related classes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.LearningPortal.model.Category;
import com.example.LearningPortal.model.Course;
import com.example.LearningPortal.repository.CategoryJpaRepository;
import com.example.LearningPortal.repository.CategoryRepository;
import com.example.LearningPortal.repository.CourseJpaRepository;

@Service
public class CategoryService implements CategoryRepository {

	private static final Logger logger = LoggerFactory.getLogger(CategoryService.class); // Define logger

	@Autowired
	private CategoryJpaRepository categoryJpaRepository;

	@Autowired
	private CourseJpaRepository courseJpaRepository;

	@Override
	public ArrayList<Category> getCategories() {
		logger.info("Retrieving all categories from the database"); // Log retrieval of categories
		return (ArrayList<Category>) categoryJpaRepository.findAll(); // Retrieve all categories from the database
	}

	@Override
	public Category getCategoryById(int id) {
		logger.info("Retrieving category with ID: {}", id); // Log retrieval of category by ID
		try {
			return categoryJpaRepository.findById(id).get(); // Retrieve category by ID
		} catch (Exception e) {
			logger.error("Category with ID {} not found", id); // Log error if category not found
			throw new ResponseStatusException(HttpStatus.NOT_FOUND); // Throw exception if category not found
		}
	}

	@Override
	public Category addCategory(Category category) {
		logger.info("Adding new category: {}", category.getCategoryName()); // Log addition of new category
		try {
			List<Integer> coursesIds = new ArrayList<>();
			for (Course course : category.getCourses()) {
				coursesIds.add(course.getCourseId());
			}
			category.setCourses(courseJpaRepository.findAllById(coursesIds));
			categoryJpaRepository.save(category);
			return category;
		} catch (Exception e) {
			logger.error("Error occurred while adding category: {}", e.getMessage()); // Log error if adding category fails
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR); // Throw exception for internal server error
		}
	}

	@Override
	public Category updateCategory(Category category, int id) {
		logger.info("Updating category with ID: {}", id); // Log update of category
		try {
			Category original = categoryJpaRepository.findById(id).get(); // Retrieve original category
			if (category.getCategoryName() != null) {
				original.setCategoryName(category.getCategoryName());
			}
			if (category.getCourses() != null) {
				List<Integer> coursesIds = new ArrayList<>();
				for (Course course : category.getCourses()) {
					coursesIds.add(course.getCourseId());
				}
				original.setCourses(courseJpaRepository.findAllById(coursesIds));
			}
			categoryJpaRepository.save(original); // Save updated category
			return original;
		} catch (Exception e) {
			logger.error("Error occurred while updating category with ID {}: {}", id, e.getMessage()); // Log error if updating category fails
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR); // Throw exception for internal server error
		}
	}

	@Override
	public void deleteCategory(int id) {
		logger.info("Deleting category with ID: {}", id); // Log deletion of category
		try {
			Category category = categoryJpaRepository.findById(id).get(); // Retrieve category to delete
			categoryJpaRepository.deleteById(id); // Delete category
		} catch (Exception e) {
			logger.error("Error occurred while deleting category with ID {}: {}", id, e.getMessage()); // Log error if deleting category fails
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR); // Throw exception for internal server error
		}
	}

}
