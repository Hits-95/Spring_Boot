package com.smart.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.smart.dao.UserDao;
import com.smart.entities.Contact;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserDao userDao;

	// method for adding common data
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		// get username from security
		String userName = principal.getName();

		System.out.println(userName);
		model.addAttribute("user", this.userDao.getUserByUserName(userName));
	}

	// dashbord home handler
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String dashBoard(Model model) {

		// send data to view
		model.addAttribute("title", "User-Dashboard");
		return "user/dash_board";
	}

	// open add form hadler
	@GetMapping("/add-contact")
	public String opneAddContactForm(Model model) {

		model.addAttribute("title", "Add-Contact");
		model.addAttribute("contact", new Contact());
		return "user/add_contact_form";
	}
}
