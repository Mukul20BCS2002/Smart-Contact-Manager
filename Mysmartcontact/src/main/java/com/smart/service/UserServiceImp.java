package com.smart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.smart.dao.UserRepository;
import com.smart.entities.User;

@Service
public class UserServiceImp implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public User saveUser(User user) {
		String encodePass = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePass);
		user.setRole("ROLE_USER");
		user.setEnabled(true);
		user.setImageURL("default.png");
		return userRepository.save(user);
	}

}
