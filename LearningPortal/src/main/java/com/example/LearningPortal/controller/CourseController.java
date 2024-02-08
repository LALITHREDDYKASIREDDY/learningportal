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
import org.springframework.web.bind.annotation.RestController;

import com.example.LearningPortal.model.Course;
import com.example.LearningPortal.service.CourseService;

/**
 * Controller class responsible for handling HTTP requests related to courses.
 * Exposes endpoints for CRUD operations on courses and enrolling users.
 */
@RestController
public class CourseController {

	// Logger for logging
	private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

	@Autowired
	CourseService courseService;

	// Endpoint to retrieve all courses
	@GetMapping("/getcourses")
	public ArrayList<Course> getCourses() {
		logger.info("Fetching all courses");
		return courseService.getCourses();
	}

	// Endpoint to retrieve a course by ID
	@GetMapping("/getcourses/{courseId}")
	public Course getCourseById(@PathVariable("courseId") int id) {
		logger.info("Fetching course by ID: {}", id);
		return courseService.getCourseById(id);
	}

	// Endpoint to add a new course
	// Only instructors are allowed to access this endpoint
	@PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
	@PostMapping("/courses")
	public Course addCourse(@RequestBody Course course) {
		logger.info("Adding new course: {}", course);
		return courseService.addCourse(course);
	}

	// Endpoint to update a course by ID
	// Only instructors are allowed to access this endpoint
	@PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
	@PutMapping("/courses/{courseId}")
	public Course updateCourseById(@PathVariable("courseId") int id, @RequestBody Course course) {
		logger.info("Updating course with ID {}: {}", id, course);
		return courseService.updateCourse(course, id);
	}

	// Endpoint to delete a course by ID
	// Only instructors are allowed to access this endpoint
	@PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
	@DeleteMapping("/courses/{courseId}")
	public void deleteCourseById(@PathVariable("courseId") int id) {
		logger.info("Deleting course with ID: {}", id);
		courseService.deleteCourse(id);
	}

	// Endpoint to enroll a user in a course
	// Only instructors and admins are allowed to access this endpoint
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_INSTRUCTOR')")
	@GetMapping("/courses/{courseId}/users/{userId}")
	public String enrollingUser(@PathVariable("courseId") int courseId, @PathVariable("userId") int userId) {
		logger.info("Enrolling user with ID {} in course with ID {}", userId, courseId);
		return courseService.enrollingStudent(userId, courseId);
	}
}