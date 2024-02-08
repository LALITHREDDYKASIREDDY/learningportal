package com.example.LearningPortal.repository;

import java.util.ArrayList;

import com.example.LearningPortal.model.Section;

public interface SectionRepository {
	ArrayList<Section> getSections();

	Section getSectionById(int id);

	Section addSection(Section section);

	Section updateSection(int id, Section section);

	void deleteSectionById(int id);
}
