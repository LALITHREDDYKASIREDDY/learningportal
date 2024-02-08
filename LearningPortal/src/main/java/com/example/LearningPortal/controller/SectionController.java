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

import com.example.LearningPortal.model.Section;
import com.example.LearningPortal.service.SectionService;

/**
 * Controller class responsible for handling HTTP requests related to sections of courses.
 * Exposes endpoints for CRUD operations on sections.
 */
@RestController
public class SectionController {

	// Logger for logging
	private static final Logger logger = LoggerFactory.getLogger(SectionController.class);

	@Autowired
	SectionService sectionService;

	// Endpoint to retrieve all sections
	// Only instructors are allowed to access this endpoint
	@PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
	@GetMapping("/courses/sections")
	public ArrayList<Section> getSections() {
		logger.info("Fetching all sections");
		return sectionService.getSections();
	}

	// Endpoint to retrieve a section by ID
	// Only instructors are allowed to access this endpoint
	@PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
	@GetMapping("/courses/sections/{sectionId}")
	public Section getSectionById(@PathVariable("sectionId") int id) {
		logger.info("Fetching section by ID: {}", id);
		return sectionService.getSectionById(id);
	}

	// Endpoint to create a new section
	// Only instructors are allowed to access this endpoint 
	@PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
	@PostMapping("/courses/sections")
	public Section createSection(@RequestBody Section section) {
		logger.info("Creating new section: {}", section);
		return sectionService.addSection(section);
	}

	// Endpoint to update a section by ID
	// Only instructors are allowed to access this endpoint
	@PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
	@PutMapping("/courses/sections/{sectionId}")
	public Section updateSection(@PathVariable("sectionId") int id, @RequestBody Section section) {
		logger.info("Updating section with ID {}: {}", id, section);
		return sectionService.updateSection(id, section);
	}

	// Endpoint to delete a section by ID
	// Only instructors are allowed to access this endpoint
	@PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
	@DeleteMapping("/courses/sections/{sectionId}")
	public void deleteSection(@PathVariable("sectionId") int id) {
		logger.info("Deleting section with ID: {}", id);
		sectionService.deleteSectionById(id);
	}
}
