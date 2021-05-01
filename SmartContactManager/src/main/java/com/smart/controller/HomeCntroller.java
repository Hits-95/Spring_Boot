package com.smart.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserDao;
import com.smart.entities.User;
import com.smart.helper.Message;

@Controller
public class HomeCntroller {

	// creating object of user dao
	@Autowired
	private UserDao userDao;

	// password encoder
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@RequestMapping(path = "/")
	public String home(Model model) {

		model.addAttribute("title", "Home - Smart Context");
		return "home";
	}

	@RequestMapping(path = "/about")
	public String about(Model model) {

		model.addAttribute("title", "About - Smart Context");
		return "about";
	}

	// signup hadler
	@RequestMapping(path = "/signup")
	public String signup(Model model) {

		model.addAttribute("title", "Register - Smart Context");
		model.addAttribute("user", new User()); // this is use for validation like in form there is th:object="${user}"
		return "signup";
	}

	// handler for registering user
	@PostMapping("/do_register")
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,
			HttpSession session) {

		try {
			if (!agreement) {
				System.out.println("You have not agreed terms and conditons.");
				throw new Exception("You have not agreed terms and conditons.");
			}

			// if result has errors
			if (result.hasErrors()) {
				// bind wrong data and redirect it into same page for display...
				System.out.println("ERRORS : " + result.toString());
				model.addAttribute("user", user);
				return "signup";
			}

			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageUrl("default.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			this.userDao.save(user);

			// make field as blank
			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("Successfully Register !!! ", "alert-success"));
			return "signup";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something wents wrong !!! " + e.getMessage(), "alert-danger"));
			return "signup";
		}
	}

	// hadler for custom login
	@GetMapping("/signin")
	public String customLogin(Model model) {

		model.addAttribute("title", "Login-Page");
		return "login";
	}
}
