package com.example.LearningPortal.mapper;

import org.mapstruct.Mapper;

import com.example.LearningPortal.model.User;
import com.example.LearningPortal.modeldto.UserDto;

@Mapper
public interface UserMapper {
	UserDto modelToDto(User user);

	User dtoToModel(UserDto userDto);
}
