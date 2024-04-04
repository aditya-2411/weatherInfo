package com.weatherInfo.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface weatherService {
	
	ResponseEntity<String> signUp(Map<String, String> requestMap);

	ResponseEntity<String> login(Map<String, String> requestMap);
	
	ResponseEntity<String> getForecastSummaryByCity(String city);
	
	ResponseEntity<String> getHourlyForecastByCity(String city);


}
