package com.example.LearningPortal.repository;

import java.util.ArrayList;

import com.example.LearningPortal.model.Course;

public interface CourseRepository {
	ArrayList<Course> getCourses();

	Course getCourseById(int id);

	Course addCourse(Course course);

	Course updateCourse(Course course, int id);

	void deleteCourse(int id);

	String enrollingStudent(int studentId, int courseId);
}
