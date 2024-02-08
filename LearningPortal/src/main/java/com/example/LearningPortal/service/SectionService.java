package com.example.LearningPortal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.LearningPortal.model.Course;
import com.example.LearningPortal.model.Section;
import com.example.LearningPortal.model.SubSection;
import com.example.LearningPortal.repository.CourseJpaRepository;
import com.example.LearningPortal.repository.SectionJpaRepository;
import com.example.LearningPortal.repository.SectionRepository;
import com.example.LearningPortal.repository.SubSectionJpaRepository;

@Service
public class SectionService implements SectionRepository {
	@Autowired
	SectionJpaRepository sectionJpaRepository;
	@Autowired
	CourseJpaRepository courseJpaRepository;

	@Autowired
	SubSectionJpaRepository subSectionJpaRepository;

	@Override
	public ArrayList<Section> getSections() {
		return (ArrayList<Section>) sectionJpaRepository.findAll();
	}

	@Override
	public Section getSectionById(int id) {
		try {
			return sectionJpaRepository.findById(id).get();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		}
	}

	@Override
	public Section addSection(Section section) {
		try {
			int id = section.getCourse().getCourseId();
			Course course = courseJpaRepository.findById(id).get();

			section.setCourse(course);

			List<SubSection> subSections = section.getSubSections();
			List<Integer> subSectionIds = new ArrayList<Integer>();
			for (SubSection subSection : subSections) {
				subSectionIds.add(subSection.getSubSectionId());
			}

			section.setSubSections(subSectionJpaRepository.findAllById(subSectionIds));

			sectionJpaRepository.save(section);
			return section;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		}
	}

	@Override
	public Section updateSection(int id, Section section) {
		try {
			Section orginal = sectionJpaRepository.findById(id).get();
			if (section.getSectionName() != null) {
				orginal.setSectionName(section.getSectionName());
			}
			if (section.getSubSections() != null) {
				List<SubSection> subSections = section.getSubSections();
				List<Integer> subSectionIds = new ArrayList<Integer>();
				for (SubSection subSection : subSections) {
					subSectionIds.add(subSection.getSubSectionId());
				}

				orginal.setSubSections(subSectionJpaRepository.findAllById(subSectionIds));

			}
			if (section.getCourse() != null) {

				int courseId = section.getCourse().getCourseId();
				Course course = courseJpaRepository.findById(courseId).get();

				orginal.setCourse(course);

			}
			sectionJpaRepository.save(orginal);
			return orginal;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		}
	}

	@Override
	public void deleteSectionById(int id) {
		try {
			Section section = sectionJpaRepository.findById(id).get();
			int courseId = section.getCourse().getCourseId();
			Course course = courseJpaRepository.findById(courseId).get();
			course.getSections().remove(section);
			courseJpaRepository.save(course);
			sectionJpaRepository.deleteById(id);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		}
	}

}
