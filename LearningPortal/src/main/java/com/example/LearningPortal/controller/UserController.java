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

import com.example.LearningPortal.model.AuthRequest;
import com.example.LearningPortal.model.User;
import com.example.LearningPortal.service.UserService;

@RestController
public class UserController {
	// Logger for logging events within the controller
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	// Unprotected endpoint
	@GetMapping("/some")
	public String get() {
		return "no protection";
	}

	// Retrieves all users
	@GetMapping("/userslist")
	public ArrayList<User> getUsers() {
		logger.info("Fetching all users");
		return userService.getUsers();
	}

	// Retrieves a specific user by its ID
	@GetMapping("/userslist/{userId}")
	public User getUserById(@PathVariable("userId") int id) {
		logger.info("Fetching user with ID: {}", id);
		return userService.getUseById(id);
	}

	// Adds a new user (accessible only to admins) 
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/signin")
	public User addUser(@RequestBody User user) {
		logger.info("Adding new user");
		return userService.addUser(user);
	}

	// Generates a token for authentication
	@PostMapping("/generatetoken")
	public String generateToken(@RequestBody AuthRequest authRequest) {
		logger.info("Generating token");
		return userService.generateToken(authRequest);
	}

	// Updates an existing user (accessible only to admins)
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/users/{userId}")
	public User updateUserById(@PathVariable("userId") int id, @RequestBody User user) {
		logger.info("Updating user with ID: {}", id);
		return userService.updateUser(user, id);
	}

	// Deletes a user by its ID (accessible only to admins)
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/users/{userId}")
	public void deleteUserById(@PathVariable("userId") int id) {
		logger.info("Deleting user with ID: {}", id);
		userService.deleteUserById(id);
	}

	// Adds a favorite course for a user (accessible only to users)
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping("/users/{userId}/courses/{courseId}")
	public String addingFavourite(@PathVariable("userId") int userId, @PathVariable("courseId") int courseId) {
		logger.info("Adding favorite course for user with ID: {}, Course ID: {}", userId, courseId);
		return userService.addingFavCourse(courseId, userId);
	}
}
