package com.weatherInfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.weatherInfo.service.weatherService;

@RestController
public class weatherControllerImpl implements weatherController {
	
	@Autowired
	weatherService weatherService;

	@Override
	public ResponseEntity<String> getForecastSummaryByCity(String city) {
		try {
			return weatherService.getForecastSummaryByCity(city);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<String>("Something went wrong!",HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> getHourlyForecastByCity(String city) {
		try {
			return weatherService.getHourlyForecastByCity(city);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<String>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
