package com.weatherInfo.jwt;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.weatherInfo.entity.User;
import com.weatherInfo.entity.UserRepository;



@Service
public class UserInfoService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	private User user;
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		user=userRepository.findByEmail(username);
		
		if(user != null) {
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
		}else {
			throw new UsernameNotFoundException("User not found.");
		}
	}
	
	public User getUserDetail(){
		return user;
	}
	
	
	
	

}
