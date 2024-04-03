package com.weatherInfo.configuration;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
	
	
	 	@Value("${rapidapi.key}")
	    private String rapidApiKey;

	    @Value("${rapidapi.host}")
	    private String rapidApiHost;
	    
	    @Value("${client.id}")
	    private String clientId;

	    @Value("${client.secret}")
	    private String clientSecret;
	    
	    @Bean
	    RestTemplate restTemplate() {
	    	
	    	RestTemplate restTemplate = new RestTemplate();
	    	
	    	restTemplate.getInterceptors().add((request, body, execution) ->{
	    		request.getHeaders().set("X-RapidAPI-Key", rapidApiKey);
		        request.getHeaders().set("X-RapidAPI-Host", rapidApiHost);
		        request.getHeaders().set("Authorization", "Basic " + getEncodedCredentials(clientId, clientSecret));
		        return execution.execute(request, body);
	    	});
	    	return restTemplate;
	}
	    
	    
	    private String getEncodedCredentials(String clientId, String clientSecret) {
	        String credentials = clientId + ":" + clientSecret;
	        return Base64.getEncoder().encodeToString(credentials.getBytes());
	    }
}
