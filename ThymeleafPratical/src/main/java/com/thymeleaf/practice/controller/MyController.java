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

	// handler for condition
	@GetMapping("/condition")
	public String conditionalHandler(Model model) {

		model.addAttribute("isActive", false);
		// model.addAttribute("isActive", true);
		List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7);
		model.addAttribute("gender", "female");
		model.addAttribute("list", list);
		return "condition";
	}

	// handler for including fragment
	@GetMapping("/service")
	public String servicesHandlder(Model model) {
		model.addAttribute("title", "About Hitesh Ahire.");
		model.addAttribute("subtitle", "To learn code and try to performe it as well.");

		return "service";
	}

	// for new about
	@GetMapping("/about-new")
	public String aboutNew() {
		return "aboutNew";
	}

	// for new contact
	@GetMapping("/contact-new")
	public String contactNew() {
		return "contactNew";
	}
}
