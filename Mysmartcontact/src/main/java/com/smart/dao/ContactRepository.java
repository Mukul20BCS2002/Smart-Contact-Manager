package com.smart.dao;





import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smart.entities.Contact;
import com.smart.entities.User;



public interface ContactRepository extends JpaRepository<Contact,Integer>{
	
	@Query("from Contact as c where c.user =:user")
	public Page<Contact> findContactsByUser(User user,Pageable pageable);
	
}
