package com.smart.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;

@Service
public class ContactServiceImp implements ContactService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Override
	public User saveContact(Contact contact,User user) {
		try {
			user.getContacts().add(contact);
			contact.setUser(user);
			
			//File uploading
			
			User contactuser = this.userRepository.save(user);
			return contactuser;
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Page<Contact> findAllContact(User user,Pageable pageable) {
		// TODO Auto-generated method stub
		Page<Contact> contactsByUser = this.contactRepository.findContactsByUser(user,pageable);
		return contactsByUser;
	}

	@Override
	public Contact findContact(int cid) {
		Optional<Contact> contactOptional = this.contactRepository.findById(cid);
		Contact contact = contactOptional.get();
		return contact;
	}

	@Override
	public boolean deleteContact(int cid) {
		boolean flag = false;
		try {
			Contact contact = this.contactRepository.findById(cid).get();
			contact.setUser(null);
			this.contactRepository.delete(contact);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Contact updateContact(User user, Contact contact) {
		try {
			contact.setUser(user);
			Contact savecontact = this.contactRepository.save(contact);
			return savecontact;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
}
