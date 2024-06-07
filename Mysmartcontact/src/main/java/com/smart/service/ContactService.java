package com.smart.service;





import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smart.entities.Contact;
import com.smart.entities.User;

public interface ContactService {
	public User saveContact(Contact contact,User user);
	public Page<Contact> findAllContact(User user,Pageable pageable);
	public Contact findContact(int cid);
	public boolean deleteContact(int cid);
	public Contact updateContact(User user,Contact contact);
}
