package com.thymeleaf.practice.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyController {

	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String about(Model model) {

		model.addAttribute("name", "Hitesh Ahire");
		model.addAttribute("date", new Date().toString());
		System.out.println("about called...");
		return "about";
		// about.html
	}
}
