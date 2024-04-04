package com.weatherInfo.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserInfoService service;
	
	Claims claims=null;
	
	private String userName=null;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getServletPath().matches("/weather/login | /weather/signup")) {
			filterChain.doFilter(request, response);
		}else {
			String authorizationHeader= request.getHeader("Authorization");
			String token=null;
			
			if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")) {

				
				token = authorizationHeader.substring(7);
				
				userName=jwtUtil.extractUsername(token);
				
				claims=jwtUtil.extractAllClaims(token);
				
			}
			
			if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null ) {
				
				UserDetails userDetails= service.loadUserByUsername(userName);
				
				if(jwtUtil.validateToken(token, userDetails)) {
					
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(token, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
			filterChain.doFilter(request, response);
		}
		

		
	}
	public String getCurrentUser() {
		return userName;
	}
}
