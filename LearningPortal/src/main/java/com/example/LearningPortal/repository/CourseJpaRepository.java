package com.example.LearningPortal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.LearningPortal.model.Course;

@Repository
public interface CourseJpaRepository extends JpaRepository<Course, Integer> {

}
