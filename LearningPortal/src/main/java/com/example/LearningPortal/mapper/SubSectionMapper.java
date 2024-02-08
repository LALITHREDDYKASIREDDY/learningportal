package com.example.LearningPortal.mapper;

import org.mapstruct.Mapper;

import com.example.LearningPortal.model.SubSection;
import com.example.LearningPortal.modeldto.SubSectionDto;

@Mapper
public interface SubSectionMapper {
	SubSectionDto modelToDto(SubSection subSection);

	SubSection dtoToModel(SubSectionDto subSectionDto);
}
