package com.weatherInfo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(path ="/weather")
public interface weatherController {
	
	@PostMapping(path="/signup")
	public ResponseEntity<String> signUp(@RequestBody(required=true) Map<String, String > requestMap);
	
	@PostMapping(path="/login")
	public ResponseEntity<String> login(@RequestBody(required = true) Map<String, String> requestMap);
	
	@GetMapping(path = "/summary")
	public ResponseEntity<String> getForecastSummaryByCity(@RequestParam String city);
	
	@GetMapping(path="/hourly")
	public ResponseEntity<String> getHourlyForecastByCity(@RequestParam String city);

	
}
