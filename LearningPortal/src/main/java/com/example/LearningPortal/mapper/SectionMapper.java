package com.example.LearningPortal.mapper;

import org.mapstruct.Mapper;

import com.example.LearningPortal.model.Section;
import com.example.LearningPortal.modeldto.SectionDto;

@Mapper
public interface SectionMapper {
	SectionDto modelToDto(Section section);

	Section dtoToModel(SectionDto sectionDto);
}
