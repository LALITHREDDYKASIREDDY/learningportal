package com.example.LearningPortal.repository;

import java.util.ArrayList;

import com.example.LearningPortal.model.SubSection;

public interface SubSectionRepository {
	ArrayList<SubSection> getSubSections();

	SubSection getSubSectionById(int id);

	SubSection addSubSection(SubSection subSection);

	SubSection updateSubSection(int id, SubSection subSection);

	void deleteSubSectionById(int id);
}
