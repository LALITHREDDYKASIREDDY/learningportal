package com.example.LearningPortal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.LearningPortal.model.Category;

@Repository
public interface CategoryJpaRepository extends JpaRepository<Category, Integer> {

}
