package com.weatherInfo.service;

import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.weatherInfo.entity.User;
import com.weatherInfo.entity.UserRepository;
import com.weatherInfo.jwt.JwtUtil;
import com.weatherInfo.jwt.UserInfoService;



@Service
public class weatherServiceImpl implements weatherService{
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	PasswordEncoder encoder;
	
	
	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
		try {
			if(validateSignUpMap(requestMap)) {
				User user = userRepository.findByEmail(requestMap.get("email"));
				if(Objects.isNull(user)) {
					User newuser=getUserFromMap(requestMap);
					newuser.setPassword(encoder.encode(requestMap.get("password")));
					userRepository.save(newuser);
					return new ResponseEntity<>("Successfully registered.",HttpStatus.OK);
				}else {
					return new ResponseEntity<>("Email already exists.",HttpStatus.BAD_REQUEST);
				}
			}else {
				return new ResponseEntity<>("Please provide all required fields to signup!",HttpStatus.BAD_REQUEST);
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private boolean validateSignUpMap(Map<String, String> requestMap) {
		
		if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber") && requestMap.containsKey("email") && requestMap.containsKey("password")) {
			return true;
		}
		return false;
	}
	
	private User getUserFromMap(Map<String, String> requestMap) {
		User user=new User();
		user.setName(requestMap.get("name"));
		user.setEmail(requestMap.get("email"));
		user.setContactNumber(requestMap.get("contactNumber"));
		user.setPassword(requestMap.get("password"));
		
		return user;
	
	}

	@Override
	public ResponseEntity<String> login(Map<String, String> requestMap) {
		try {
			org.springframework.security.core.Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
			if(auth.isAuthenticated()) {
				return new ResponseEntity<>("{\"token\":\""+ jwtUtil.generateToken(userInfoService.getUserDetail().getEmail())+ "\"}",HttpStatus.OK);
			}
		}catch (AuthenticationException ex) {
	        return new ResponseEntity<>("Authentication failed: Bad credentials", HttpStatus.UNAUTHORIZED);
	    }
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> getForecastSummaryByCity(String city) {
		try {
			if(city!=null) {
				String url = "https://forecast9.p.rapidapi.com/rapidapi/forecast/"+city+"/summary/";
				ResponseEntity<String >responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
				return new ResponseEntity<String>(responseEntity.getBody(),responseEntity.getHeaders(),HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("Please provide the city name",HttpStatus.BAD_REQUEST);
			}
		}
		catch(HttpClientErrorException.NotFound ex) {
			return new ResponseEntity<>("Data not found for city "+city+"!",HttpStatus.NOT_FOUND);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	public ResponseEntity<String> getHourlyForecastByCity(String city) {
		try {
			if(city!=null) {
				String url ="https://forecast9.p.rapidapi.com/rapidapi/forecast/"+city+"/hourly/";
				ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
				return new ResponseEntity<String>(responseEntity.getBody(),responseEntity.getHeaders(),HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("Please provide the city name",HttpStatus.BAD_REQUEST);
			}
		}
		catch(HttpClientErrorException.NotFound ex) {
			return new ResponseEntity<>("Data not found for city "+city+"!",HttpStatus.NOT_FOUND);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
	}


}
