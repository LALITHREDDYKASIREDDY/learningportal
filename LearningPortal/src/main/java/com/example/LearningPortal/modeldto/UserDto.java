package com.example.LearningPortal.modeldto;

import lombok.Data;

@Data
public class UserDto {
	private int userId;
	private String firstName;
	private String lastName;
	private String email;
	private String accountType;
}
