package com.example.LearningPortal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.LearningPortal.model.Section;

@Repository
public interface SectionJpaRepository extends JpaRepository<Section, Integer> {

}
