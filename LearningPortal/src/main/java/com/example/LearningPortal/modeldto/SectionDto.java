package com.example.LearningPortal.modeldto;

import com.example.LearningPortal.model.Course;

import lombok.Data;

@Data
public class SectionDto {
	private int sectionId;
	private String sectionName;
	private Course course;
}
