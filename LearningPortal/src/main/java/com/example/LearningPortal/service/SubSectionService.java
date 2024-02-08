package com.example.LearningPortal.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.LearningPortal.model.Section;
import com.example.LearningPortal.model.SubSection;
import com.example.LearningPortal.repository.SectionJpaRepository;
import com.example.LearningPortal.repository.SubSectionJpaRepository;
import com.example.LearningPortal.repository.SubSectionRepository;

@Service
public class SubSectionService implements SubSectionRepository {
	@Autowired
	SubSectionJpaRepository subSectionJpaRepository;
	@Autowired
	SectionJpaRepository sectionJpaRepository;

	@Override
	public ArrayList<SubSection> getSubSections() {

		return (ArrayList<SubSection>) subSectionJpaRepository.findAll();
	}

	@Override
	public SubSection getSubSectionById(int id) {
		try {
			return subSectionJpaRepository.findById(id).get();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public SubSection addSubSection(SubSection subSection) {

		try {
			int id = subSection.getSection().getSectionId();
			Section section = sectionJpaRepository.findById(id).get();
			subSection.setSection(section);
			subSectionJpaRepository.save(subSection);
			return subSection;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public SubSection updateSubSection(int id, SubSection subSection) {

		try {
			SubSection orginal = subSectionJpaRepository.findById(id).get();
			if (subSection.getSubSectionName() != null) {
				orginal.setSubSectionName(subSection.getSubSectionName());
			}
			if (subSection.getVideoUrl() != null) {
				orginal.setVideoUrl(subSection.getVideoUrl());
			}
			if (subSection.getSection() != null) {

				int sectionId = subSection.getSection().getSectionId();
				Section section = sectionJpaRepository.findById(sectionId).get();

				orginal.setSection(section);

			}

			subSectionJpaRepository.save(orginal);
			return orginal;

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void deleteSubSectionById(int id) {
		try {
			SubSection subSection = subSectionJpaRepository.findById(id).get();

			subSectionJpaRepository.deleteById(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

}
