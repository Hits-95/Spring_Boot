package com.smart.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.smart.dao.UserDao;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserDao userDao;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String dashBoard(Model model, Principal principal) {

		// get username from security
		String userName = principal.getName();
		
		System.out.println(userName);

		// send data to view
		model.addAttribute("title", "User-Dashboard");
		model.addAttribute("user", this.userDao.getUserByUserName(userName));
		return "user/dash_board";
	}
}
