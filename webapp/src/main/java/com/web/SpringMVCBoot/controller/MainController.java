package com.web.SpringMVCBoot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping("/")
	public String home() {
		 
		System.out.println("home run...");
		return "home";
	}
	
	@RequestMapping("/contact")
	public String contact() {
		 
		System.out.println("home run...");
		return "contact";
	}
}
