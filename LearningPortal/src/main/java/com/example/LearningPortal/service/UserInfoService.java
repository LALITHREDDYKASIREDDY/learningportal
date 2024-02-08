package com.example.LearningPortal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.LearningPortal.model.User;
import com.example.LearningPortal.model.UserInfoDetails;
import com.example.LearningPortal.repository.UserJpaRepository;

@Service
public class UserInfoService implements UserDetailsService {

	@Autowired
	private UserJpaRepository userJpaRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		Optional<User> user = userJpaRepository.findByUserName(userName);

		// Converting userDetail to UserDetails 
		return user.map(UserInfoDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + userName));
	}

}
