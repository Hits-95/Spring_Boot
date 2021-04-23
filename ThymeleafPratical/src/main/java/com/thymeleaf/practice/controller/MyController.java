package com.thymeleaf.practice.controller;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

	// for loop example
	@GetMapping("/example-loop")
	public String iterater(Model model) {

		// create list, set

		List<String> names = List.of("Hitesh", "Ganesh", "Pradhunya", "Nikita");
		model.addAttribute("names", names);
		return "iterater";
	}
}
