package com.weatherInfo.service;

import org.springframework.http.ResponseEntity;

public interface weatherService {
	
	ResponseEntity<String> getForecastSummaryByCity(String city);
	
	ResponseEntity<String> getHourlyForecastByCity(String city);
}
