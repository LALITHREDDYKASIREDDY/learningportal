package com.example.LearningPortal.modeldto;

import com.example.LearningPortal.model.Section;

import lombok.Data;

@Data
public class SubSectionDto {
	private int subSectionId;
	private String subSectionName;
	private String videoUrl;
	private Section section;
}
