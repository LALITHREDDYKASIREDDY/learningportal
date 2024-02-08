package com.example.LearningPortal.mapper;

import org.mapstruct.Mapper;

import com.example.LearningPortal.model.Course;
import com.example.LearningPortal.modeldto.CourseDto;

@Mapper
public interface CourseMapper {
	CourseDto modelToDto(Course course);

	Course dtoToModel(CourseDto courseDto);
}
