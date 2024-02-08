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
import com.example.LearningPortal.model.Section;
import com.example.LearningPortal.model.User;
import com.example.LearningPortal.repository.CategoryJpaRepository;
import com.example.LearningPortal.repository.CourseJpaRepository;
import com.example.LearningPortal.repository.CourseRepository;
import com.example.LearningPortal.repository.SectionJpaRepository;
import com.example.LearningPortal.repository.UserJpaRepository;

@Service
public class CourseService implements CourseRepository {

	private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

	@Autowired
	private CourseJpaRepository courseJpaRepository;
	@Autowired
	private CategoryJpaRepository categoryJpaRepository;
	@Autowired
	private UserJpaRepository userJpaRepository;
	@Autowired
	private SectionJpaRepository sectionJpaRepository;

	@Override
	public ArrayList<Course> getCourses() {
		logger.info("Retrieving all courses from the database"); // Log retrieval of all courses
		return (ArrayList<Course>) courseJpaRepository.findAll(); // Retrieve all courses from the database
	}

	@Override
	public Course getCourseById(int id) {
		logger.info("Retrieving course with ID: {}", id); // Log retrieval of course by ID
		try {
			return courseJpaRepository.findById(id).get(); // Retrieve course by ID
		} catch (Exception e) {
			logger.error("Course with ID {} not found", id); // Log error if course not found
			throw new ResponseStatusException(HttpStatus.NOT_FOUND); // Throw exception if course not found
		}
	}

	@Override
	public Course addCourse(Course course) {
		logger.info("Adding new course: {}", course.getTitle()); // Log addition of new course
		try {
			// Retrieve category by ID
			Category category = categoryJpaRepository.findById(course.getCategory().getCategoryId()).get();
			course.setCategory(category);

			// Retrieve author by ID
			User author = userJpaRepository.findById(course.getAuthor().getUserId()).get();
			course.setAuthor(author);

			// Retrieve sections by IDs
			List<Section> sections = course.getSections();
			List<Integer> sectionIds = new ArrayList<>();
			for (Section section : sections) {
				sectionIds.add(section.getSectionId());
			}
			course.setSections(sectionJpaRepository.findAllById(sectionIds));

			// Retrieve enrolled users by IDs
			List<User> enrolledUsers = course.getEnrolledUsers();
			List<Integer> enrolledUserIds = new ArrayList<>();
			for (User enrolledUser : enrolledUsers) {
				enrolledUserIds.add(enrolledUser.getUserId());
			}
			course.setEnrolledUsers(userJpaRepository.findAllById(enrolledUserIds));

			// Save course
			category.getCourses().add(course);
			categoryJpaRepository.save(category);
			courseJpaRepository.save(course);
			logger.info("Course added successfully");
			return course;
		} catch (Exception e) {
			logger.error("Error occurred while adding course: {}", e.getMessage()); // Log error if adding course fails
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR); // Throw exception for internal server error
		}
	}

	@Override
	public Course updateCourse(Course course, int id) {
		logger.info("Updating course with ID: {}", id); // Log update of course
		try {
			Course original = courseJpaRepository.findById(id).get();

			// Update course details
			if (course.getTitle() != null) {
				original.setTitle(course.getTitle());
			}
			if (course.getDescription() != null) {
				original.setDescription(course.getDescription());
			}
			if (course.getAuthor() != null) {
				int userId = course.getAuthor().getUserId();
				original.setAuthor(userJpaRepository.findById(userId).get());
			}
			if (course.getCategory() != null) {
				// Update category and its courses
				int categoryId = course.getCategory().getCategoryId();
				Category category = categoryJpaRepository.findById(categoryId).get();
				if (original.getCategory().getCategoryId() != categoryId) {
					Category originalCategory = original.getCategory();
					originalCategory.getCourses().remove(original);
					categoryJpaRepository.save(originalCategory);
				}
				original.setCategory(category);
				category.getCourses().add(course);
				categoryJpaRepository.save(category);
			}
			if (course.getSections() != null) {
				// Update sections
				List<Section> sections = course.getSections();
				List<Integer> sectionIds = new ArrayList<>();
				for (Section section : sections) {
					sectionIds.add(section.getSectionId());
				}
				original.setSections(sectionJpaRepository.findAllById(sectionIds));
			}
			if (course.getEnrolledUsers() != null) {
				// Update enrolled users
				List<User> enrolledUsers = course.getEnrolledUsers();
				List<Integer> enrolledUserIds = new ArrayList<>();
				for (User enrolledUser : enrolledUsers) {
					enrolledUserIds.add(enrolledUser.getUserId());
				}
				original.setEnrolledUsers(userJpaRepository.findAllById(enrolledUserIds));
			}
			courseJpaRepository.save(original); // Save updated course
			return original;
		} catch (Exception e) {
			logger.error("Error occurred while updating course with ID {}: {}", id, e.getMessage()); // Log error if updating course fails
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR); // Throw exception for internal server error
		}
	}

	@Override
	public void deleteCourse(int id) {
		logger.info("Deleting course with ID: {}", id); // Log deletion of course
		try {
			Course course = courseJpaRepository.findById(id).get();
			// Remove course from its category's courses list
			Category originalCategory = categoryJpaRepository.findById(course.getCategory().getCategoryId()).get();
			originalCategory.getCourses().remove(course);
			categoryJpaRepository.save(originalCategory);
			courseJpaRepository.deleteById(id); // Delete course
		} catch (Exception e) {
			logger.error("Error occurred while deleting course with ID {}: {}", id, e.getMessage()); // Log error if deleting course fails
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public String enrollingStudent(int studentId, int courseId) {
		logger.info("Enrolling student with ID {} in course with ID {}", studentId, courseId); // Log enrolling student in course
		try {
			User studentUser = userJpaRepository.findById(studentId).get();
			Course course = courseJpaRepository.findById(courseId).get();
			// Check if user role is ROLE_USER
			if (studentUser.getAccountType().equals("ROLE_USER")) {
				course.getEnrolledUsers().add(studentUser);
				courseJpaRepository.save(course);
				studentUser.getEnrolledCourses().add(course);
				userJpaRepository.save(studentUser);
				logger.info("Student enrolled successfully");
				return "User enrolled successfully";
			}
			logger.warn("Role type is different"); // Log warning if user role is different from ROLE_USER
			return "Role type is different";
		} catch (Exception e) {
			logger.error("Error occurred while enrolling student in course: {}", e.getMessage()); // Log error if enrolling student fails
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

}
