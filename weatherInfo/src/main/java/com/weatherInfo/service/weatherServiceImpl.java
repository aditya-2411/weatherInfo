package com.weatherInfo.service;

import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Service
public class weatherServiceImpl implements weatherService{
	
	@Autowired
	RestTemplate restTemplate;
	

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
