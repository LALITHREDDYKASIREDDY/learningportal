package com.example.LearningPortal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.LearningPortal.model.SubSection;

@Repository
public interface SubSectionJpaRepository extends JpaRepository<SubSection, Integer> {

}
