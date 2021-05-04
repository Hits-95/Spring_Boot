package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.ContactDao;
import com.smart.dao.UserDao;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private ContactDao contactDao;

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
				contact.setImage("contact.png");
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
			String Error = "Something went wrong !!! Try again... " + e.getMessage();
			session.setAttribute("message", new Message(Error, "bg-danger"));

		}
		model.addAttribute("title", "Add-Contact");
		return "user/add_contact_form";
	}

	// show contacts handler
	// per page = 5[n]
	// current page = 0 [page]
	@GetMapping("/show-contacts/{page}")
	public String showContacts(@PathVariable("page") Integer page, Model model, HttpSession session) {

		model.addAttribute("title", "View-Contacts");
		// send all contacts list to show_contact page
		// get log in user data from database
		// gor paggination create object of PageRequest
		PageRequest pageAble = PageRequest.of(page, 5);
		Page<Contact> contacts = this.contactDao.findContactByUser(this.user.getId(), pageAble);
		model.addAttribute("contacts", contacts);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", contacts.getTotalPages());

		// empty contact
		if (contacts.isEmpty()) {
			session.setAttribute("message", new Message("You have not contacts please add it!!! ", "bg-danger"));
		}
		return "user/show_contacts";
	}

	// show perticular contact details
	@GetMapping("/{cId}/contact")
	public String showContactDetails(@PathVariable("cId") Integer cId, Model model) {

		Optional<Contact> optional = this.contactDao.findById(cId);
		Contact contact = optional.get();
		
		model.addAttribute("title", contact.getName());
		model.addAttribute("contact", contact);
		return "user/contact_detail";
	}
}
