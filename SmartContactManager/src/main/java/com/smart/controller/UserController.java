package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.UserDao;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserDao userDao;

	private User user = null;

	// method for adding common data
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		// get username from security
		String userName = principal.getName();
		user = this.userDao.getUserByUserName(userName);
		model.addAttribute("user", user);
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

	/// process-contact save contact in database handler
	@PostMapping("/process-contact")
	public String processContact(@Valid @ModelAttribute("contact") Contact contact, BindingResult bindingResul,
			@RequestParam("image") MultipartFile file, Model model, HttpSession session) {
		try {
			// validation part
			if (bindingResul.hasErrors()) {
				System.out.println("Errors : " + bindingResul.toString());
				model.addAttribute("contact", contact);
			}
			// processing and uploading file
			if (file.isEmpty()) {
				System.out.println("file is empty .");
			} else {

				contact.setImage(file.getOriginalFilename());
				File saveFile = new ClassPathResource("static/upload_image").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}

			// bidirectional mapping for save user_id into contact...
			contact.setUser(user);
			user.getContacts().add(contact);
			userDao.save(user);

			// success message...
			session.setAttribute("message", new Message("Your contact is added !!! Add more...", "bg-success"));
			model.addAttribute("contact", new Contact());

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Something went wrong !!! Try again...", "bg-danger"));

		}
		return "user/add_contact_form";
	}
}
