package com.smart.controllers;



import java.security.Principal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.message.Message;
import com.smart.service.ContactService;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactService contactServiceImp;

	
	
	@ModelAttribute
	public void commonData(Model model,Principal principal)
	{
		String username = principal.getName();
		User user = userRepository.findByEmail(username);
		model.addAttribute("user", user);
	}
	
	@GetMapping("/index")
	public String dashBoard(Model model,Principal principal)
	{
		String username = principal.getName();
		User user = userRepository.findByEmail(username);
		model.addAttribute("user", user);
		model.addAttribute("title", "UserDashboard-Smart Contact Manager");
		return "normal/user_dashboard";
	}
	
	@GetMapping("/add_contact")
	public String addContact(Model model,HttpSession session)
	{
		model.addAttribute("title", "Add Contact-Smart Contact Manager");
		model.addAttribute("contact", new Contact());
		Object message = session.getAttribute("message");
        if (message != null) {
            model.addAttribute("message", message);
            session.removeAttribute("message");
        }
		return "normal/add_contact_form";
	}
	
	//Processing add contact form
	@PostMapping("/contactAddAction")
	public String processContact(@Valid @ModelAttribute Contact contact,BindingResult result,Principal p,Model model,RedirectAttributes redirectAttributes)
	{
		try {
			if(result.hasErrors())
			{
				model.addAttribute("title", "Register-Smart Contact Manager");
				model.addAttribute("contact",contact);
				return "normal/add_contact_form";
			}
			String username = p.getName();
			User user = userRepository.findByEmail(username);
			this.contactServiceImp.saveContact(contact,user);
			model.addAttribute("title", "Add Contact-Smart Contact Manager");
			redirectAttributes.addFlashAttribute("message",new Message("Contact Added Successfull!!","alert-success"));
			return "redirect:/user/add_contact";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("contact",contact);
			redirectAttributes.addFlashAttribute("message",new Message("Something went wrong Try Again!!"+e.getMessage(),"alert-danger"));
			return "redirect:/user/add_contact";
		}
		
	}
	
	//Show Contact
	//per page = 5[n]
	//current page = 0 [page]
	@GetMapping("/showContact/{page}")
	public String showContact(@PathVariable("page") Integer page,Model model,Principal p)
	{
		String username = p.getName();
		User user = this.userRepository.findByEmail(username);
		
		//Current Page
		//Contact per Page
		Pageable pageable = PageRequest.of(page, 5);
		Page<Contact> allContact = this.contactServiceImp.findAllContact(user,pageable);
		System.out.println(allContact);
		model.addAttribute("title", "Your Contacts details-Smart Contact Manager");
		model.addAttribute("contacts", allContact);
		model.addAttribute("currentPage",page);
		model.addAttribute("totalPages",allContact.getTotalPages());
		return "normal/show_contacts";
	}
	
	
	//Showing specific Contact Details
	@GetMapping("/contact/{cid}")
	public String showContactDetails(@PathVariable("cid") int cid,Model model,Principal p)
	{
		Contact contact = this.contactServiceImp.findContact(cid);
		model.addAttribute("title", "Contact details-Smart Contact Manager");
		String username = p.getName();
		User user = this.userRepository.findByEmail(username);
		if(user.getId()==contact.getUser().getId())
		{
			model.addAttribute("contact",contact);
		}
		return "normal/contact_details";
	}
	
	//Delete  Contact Handler
	@GetMapping("/deleteContact/{cid}/{currentPage}")
	public String deleteContact(@PathVariable("cid") int cid,@PathVariable("currentPage") Integer currentPage,Model model,Principal p,RedirectAttributes redirectAttributes)
	{
		boolean flag = this.contactServiceImp.deleteContact(cid);
		if(!flag)
		{
			redirectAttributes.addFlashAttribute("message",new Message("Something went wrong Try Again!!","alert-danger"));
			return "redirect:/user/showContact/" + currentPage;
		}
		redirectAttributes.addFlashAttribute("message",new Message("Contact Deleted Successfully!!","alert-success"));
		return "redirect:/user/showContact/" + currentPage;
	}
	
	//Update Contact details
	@GetMapping("/updateContact/{cid}")
	public String updateContact(@PathVariable("cid") int cid,Model model)
	{
		Contact contact = this.contactServiceImp.findContact(cid);
		model.addAttribute("contact",contact);
		model.addAttribute("title","Update Contact details-Smart Contact Manager");
		return "normal/update_contact";
	}
	
	// Update Contact Processing 
	@PostMapping("/contactUpdateAction")
	public String updateContactPro(@ModelAttribute Contact contact,Model model,RedirectAttributes redirectAttributes,Principal p)
	{
		try {
			String username = p.getName();
			User user = this.userRepository.findByEmail(username);
			this.contactServiceImp.updateContact(user, contact);	
			redirectAttributes.addFlashAttribute("message",new Message("Contact Updated Successfully!!","alert-success"));
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message",new Message("Something went wrong Try Again!!","alert-danger"));
			return "redirect:/user/updateContact/"+contact.getCid();
		}
		return "redirect:/user/contact/"+contact.getCid();
	}
	
	//Profile Handler
	@GetMapping("/profile")
	public String profilehandler(Model model)
	{
		model.addAttribute("title","User Profile-Smart Contact Manager");
		return "normal/profile";
	}
}
