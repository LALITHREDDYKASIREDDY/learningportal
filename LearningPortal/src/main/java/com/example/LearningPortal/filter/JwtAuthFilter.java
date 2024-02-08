package com.example.LearningPortal.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.LearningPortal.service.JwtService;
import com.example.LearningPortal.service.UserInfoService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This class is responsible for validating JWT tokens and authenticating users.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserInfoService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Extracting token from Authorization header
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			username = jwtService.extractUsername(token);
		}

		// If username exists and no authentication is currently set in SecurityContextHolder
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			// Load UserDetails from database using username
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			// Validate token and UserDetails
			if (jwtService.validateToken(token, userDetails)) {
				// Create authentication token
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				// Set additional authentication details
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// Set authentication in SecurityContextHolder
				SecurityContextHolder.getContext().setAuthentication(authToken);

			} else {
				// Log token validation failure
				logger.warn("Token validation failed for user " + username);
			}
		}
		// Continue the filter chain
		filterChain.doFilter(request, response);
	}
}