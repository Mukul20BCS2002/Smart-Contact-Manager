package com.smart.controllers;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.smart.entities.User;
import com.smart.message.Message;
import com.smart.service.UserService;

import jakarta.servlet.http.HttpSession;

import jakarta.validation.Valid;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String Home(Model model) {
		model.addAttribute("title", "Home-Smart Contact Manager");
		return "home";
	}

	@GetMapping("/about")
	public String About(Model model) {
		model.addAttribute("title", "About-Smart Contact Manager");
		return "about";
	}

	@GetMapping("/signUp")
	public String signUp(Model model,HttpSession session) {
		model.addAttribute("title", "Register-Smart Contact Manager");
		model.addAttribute("user", new User());
		
		Object message = session.getAttribute("message");
        if (message != null) {
            model.addAttribute("message", message);
            session.removeAttribute("message");
        }
		return "signUp";
	}
	
	@GetMapping("/login")
	public String login(Model model)
	{
		model.addAttribute("title", "Login-Smart Contact Manager");
		return "login";
	}
	
	@PostMapping("/registerAction")
	public String registerHandler(@Valid @ModelAttribute("user") User user,BindingResult result,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,RedirectAttributes redirectAttributes) {

		try {
			if (!agreement) {
				throw new Exception("You must agree to the terms and conditions.");
			}
			if(result.hasErrors())
			{
				model.addAttribute("title", "Register-Smart Contact Manager");
				model.addAttribute("user",user);
				return "signUp";
			}
			this.userService.saveUser(user);
			model.addAttribute("title", "Register-Smart Contact Manager");
			redirectAttributes.addFlashAttribute("message",new Message("Successfull Registered!!","alert-success"));
			return "redirect:/signUp";
			
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			model.addAttribute("title", "Register-Smart Contact Manager");
			redirectAttributes.addFlashAttribute("message",new Message("Something Went Wrong!!"+e.getMessage(),"alert-danger"));
			return "redirect:/signUp";
		}
		
	}
	
}
