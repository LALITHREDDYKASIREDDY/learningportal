package com.example.LearningPortal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.LearningPortal.model.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUserName(String userName);
}
