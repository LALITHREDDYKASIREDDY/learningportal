package com.example.LearningPortal.modeldto;

import com.example.LearningPortal.model.Category;
import com.example.LearningPortal.model.User;

import lombok.Data;

@Data
public class CourseDto {
	private int courseId;
	private String title;
	private Category category;
	private String description;
	private User author;
}
