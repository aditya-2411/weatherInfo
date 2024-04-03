package com.weatherInfo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(path ="/weather")
public interface weatherController {
	
	@GetMapping(path = "/summary")
	public ResponseEntity<String> getForecastSummaryByCity(@RequestParam String city);
	
	@GetMapping(path="/hourly")
	public ResponseEntity<String> getHourlyForecastByCity(@RequestParam String city);

	
}
