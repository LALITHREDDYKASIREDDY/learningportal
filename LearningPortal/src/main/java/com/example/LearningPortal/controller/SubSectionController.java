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

import com.example.LearningPortal.model.SubSection;
import com.example.LearningPortal.service.SubSectionService;

@RestController
public class SubSectionController {

	// Logger for logging events within the controller
	private static final Logger logger = LoggerFactory.getLogger(SubSectionController.class);

	@Autowired
	SubSectionService subSectionService;

	// Retrieves all sub-sections
	// Only instructors are allowed to access this endpoint
	@PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
	@GetMapping("/courses/sections/subsections")
	public ArrayList<SubSection> getSubSections() {
		logger.info("Fetching all sub-sections");
		return subSectionService.getSubSections();
	}

	// Retrieves a specific sub-section by its ID
	// Only instructors are allowed to access this endpoint
	@PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
	@GetMapping("/courses/sections/subsections/{subSectionId}")
	public SubSection getSubSectionById(@PathVariable("subSectionId") int id) {
		logger.info("Fetching sub-section with ID: {}", id);
		return subSectionService.getSubSectionById(id);
	}

	// Creates a new sub-section
	// Only instructors are allowed to access this endpoint
	@PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
	@PostMapping("/courses/sections/subsections")
	public SubSection createSubSection(@RequestBody SubSection subSection) {
		logger.info("Creating new sub-section");
		return subSectionService.addSubSection(subSection);
	}

	// Updates an existing sub-section
	// Only instructors are allowed to access this endpoint
	@PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
	@PutMapping("/courses/sections/subsections/{subSectionId}")
	public SubSection updateSubSection(@PathVariable("subSectionId") int id, @RequestBody SubSection subSection) {
		logger.info("Updating sub-section with ID: {}", id);
		return subSectionService.updateSubSection(id, subSection);
	}

	// Deletes a sub-section by its ID
	// Only instructors are allowed to access this endpoint
	@PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
	@DeleteMapping("/courses/sections/subsections/{subSectionId}")
	public void deleteSubSection(@PathVariable("subSectionId") int id) {
		logger.info("Deleting sub-section with ID: {}", id);
		subSectionService.deleteSubSectionById(id);
	}
}