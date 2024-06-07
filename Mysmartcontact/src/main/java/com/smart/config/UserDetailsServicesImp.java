
package com.smart.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.smart.dao.UserRepository;
import com.smart.entities.User;

public class UserDetailsServicesImp implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if(user==null)
		{
			throw new UsernameNotFoundException("Unable to find User");
		}
		System.out.println("User:"+user.getEmail());
		System.out.println("User:"+user.getPassword());
		return new CustomUserDetails(user);
	}

   
}
