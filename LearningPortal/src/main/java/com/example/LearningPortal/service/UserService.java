package com.example.LearningPortal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.LearningPortal.model.AuthRequest;
import com.example.LearningPortal.model.Course;
import com.example.LearningPortal.model.User;
import com.example.LearningPortal.repository.CourseJpaRepository;
import com.example.LearningPortal.repository.UserJpaRepository;
import com.example.LearningPortal.repository.UserRepository;

@Service
public class UserService implements UserRepository {
	@Autowired
	UserJpaRepository userJpaRepository;
	@Autowired
	CourseJpaRepository courseJpaRepository;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public ArrayList<User> getUsers() {

		return (ArrayList<User>) userJpaRepository.findAll();
	}

	@Override
	public User getUseById(int id) {
		try {
			return userJpaRepository.findById(id).get();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public User addUser(User user) {
		try {
			List<Course> enrolledCourses = user.getEnrolledCourses();
			List<Integer> enrolledCourseIds = new ArrayList<>();

			for (Course enrolledCourse : enrolledCourses) {
				enrolledCourseIds.add(enrolledCourse.getCourseId());
			}
			List<Course> enrolledCoursesList = courseJpaRepository.findAllById(enrolledCourseIds);
			for (Course enrolledCourse : enrolledCoursesList) {
				enrolledCourse.getEnrolledUsers().add(user);
			}
			courseJpaRepository.saveAll(enrolledCoursesList);
			user.setEnrolledCourses(enrolledCoursesList);
			user.setPassword(encoder.encode(user.getPassword()));
			userJpaRepository.save(user);
			return user;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public User updateUser(User user, int id) {
		try {
			User original = userJpaRepository.findById(id).get();
			if (user.getUserName() != null) {
				original.setUserName(user.getUserName());
			}
			if (user.getEmail() != null) {
				original.setEmail(user.getEmail());
			}
			if (user.getPassword() != null) {
				user.setPassword(encoder.encode(user.getPassword()));
				original.setPassword(user.getPassword());
			}
			if (user.getAccountType() != null) {
				original.setAccountType(user.getAccountType());
			}
			if (user.getEnrolledCourses() != null) {
				List<Integer> orginalEnrolledIds = new ArrayList<Integer>();
				for (Course course : original.getEnrolledCourses()) {
					orginalEnrolledIds.add(course.getCourseId());
				}
				List<Course> originalCourses = courseJpaRepository.findAllById(orginalEnrolledIds);
				for (Course course : originalCourses) {
					course.getEnrolledUsers().remove(user);
				}
				courseJpaRepository.saveAll(originalCourses);
				List<Course> enrolledCourses = user.getEnrolledCourses();
				List<Integer> enrolledCourseIds = new ArrayList<>();

				for (Course enrolledCourse : enrolledCourses) {
					enrolledCourseIds.add(enrolledCourse.getCourseId());
				}
				List<Course> enrolledCoursesList = courseJpaRepository.findAllById(enrolledCourseIds);
				original.setEnrolledCourses(enrolledCoursesList);
				for (Course enrolledCourse : enrolledCoursesList) {
					enrolledCourse.getEnrolledUsers().add(user);
				}
				courseJpaRepository.saveAll(enrolledCoursesList);

			}
			if (user.getAccountType() != null) {
				throw new Exception("user cannot change the accounttype");
			}
			userJpaRepository.save(original);
			return original;
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.LOCKED);
		}
	}

	@Override
	public void deleteUserById(int id) {
		try {
			User user = userJpaRepository.findById(id).get();
			List<Course> enrolledCourses = user.getEnrolledCourses();
			for (Course course : enrolledCourses) {
				course.getEnrolledUsers().remove(user);
			}
			courseJpaRepository.saveAll(enrolledCourses);
			userJpaRepository.deleteById(id);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public String generateToken(AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));

		if (authentication.isAuthenticated()) {
			System.out.println();
			return jwtService.generateToken(authRequest.getUserName());
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}
	}

	@Override
	public String addingFavCourse(int courseId, int userId) {
		try {
			Course course = courseJpaRepository.findById(courseId).get();
			User user = userJpaRepository.findById(userId).get();
			user.getFavouriteCourses().add(course);
			userJpaRepository.save(user);
			return "course added as favourite";
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

}
