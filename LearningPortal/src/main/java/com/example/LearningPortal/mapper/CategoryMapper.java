package com.example.LearningPortal.mapper;

import org.mapstruct.Mapper;

import com.example.LearningPortal.model.Category;
import com.example.LearningPortal.modeldto.CategoryDto;

@Mapper
public interface CategoryMapper {
	CategoryDto modelToDto(Category category);

	Category dtoToModel(CategoryDto categoryDto);
}
