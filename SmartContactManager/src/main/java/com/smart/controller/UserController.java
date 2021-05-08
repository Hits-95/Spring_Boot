package com.smart.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

	private File saveFile = null;

	public UserController() throws IOException {
		this.saveFile = new ClassPathResource("static/upload_image").getFile();
		;
	}

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
		model.addAttribute("opration", "add");
		model.addAttribute("contact", new Contact());
		return "user/add_contact_form";
	}

	/// process-contact save contact in database handler
	@PostMapping("/process-contact")
	public String processContact(@Valid @ModelAttribute("contact") Contact contact, BindingResult bindingResul,
			@RequestParam("image") MultipartFile file, @RequestParam("opration") String opr, Model model,
			HttpSession session) {
		try {
			System.out.println(contact);
			model.addAttribute("title", opr.equals("add") ? "Add-Contact" : "Update-Contact");
			// validation part
			if (bindingResul.hasErrors()) {
				System.out.println("Errors : " + bindingResul.toString());

			}
			// processing and uploading file
			if (file.isEmpty()) {
				if (opr.equals("add"))
					contact.setImage("contact.png");
				else
					contact.setImage(this.contactDao.findById(contact.getcId()).get().getImage());

			} else {

				// delete file path
				Paths.get(saveFile.getAbsolutePath() + File.separator);

				System.out.println(contact);
				if (opr.equals("update")) {
					// get data from database for deletion image in folder
					String deleteimage = this.contactDao.findById(contact.getcId()).get().getImage();

					if (!deleteimage.equals("contact.png")) {
						File deleteFile = new File(saveFile, deleteimage);
						deleteFile.delete();
					}
				}

				// save image in database...

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				// rename file
				String newFileName = "Hits_"
						+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss")) + ".jpg";

				// save name with this name into database...
				contact.setImage(newFileName);

				Path newFilePath = Paths.get(saveFile.getAbsolutePath() + File.separator + newFileName);
				Files.move(path, newFilePath);

			}

			// bidirectional mapping for save user_id into contact...
			contact.setUser(user);
			user.getContacts().add(contact);
			userDao.save(user);

			// success message...
			session.setAttribute("message", new Message((opr.equals("add")) ? "Your contact is added !!! Add more..."
					: "Your contact has been updated !!! ", "bg-success"));
			model.addAttribute("contact", new Contact());

		} catch (Exception e) {

			e.printStackTrace();
			String Error = "Something went wrong !!! Try again... " + e.getMessage();
			session.setAttribute("message", new Message(Error, "bg-danger"));
			model.addAttribute("opration", opr.equals("add") ? "add" : "update");
			return "user/add_contact_form";

		}

		return ((opr.equals("add")) ? "redirect:/user/show-contacts/0"
				: "redirect:/user/" + contact.getcId() + "/contact");
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
		// sequrity...
		if (user.getId() == contact.getUser().getId()) {
			model.addAttribute("title", contact.getName());
			model.addAttribute("contact", contact);
		}
		return "user/contact_detail";
	}

	// delete contact..
	@GetMapping("/delete/{cId}/{currentPage}")
	public String deleteContact(@PathVariable("cId") Integer cId, @PathVariable("currentPage") Integer currentPage,
			Model model) {

		Optional<Contact> contactOptional = this.contactDao.findById(cId);
		Contact contact = contactOptional.get();

		// check..
		if (user.getId() == contact.getUser().getId()) {

			// delete file from folder

			if (!contact.getImage().equals("contact.png")) {
				File deleteFile = new File(saveFile, contact.getImage());
				deleteFile.delete();
			}
			// unlinke this with user
			contact.setUser(null);

			this.contactDao.delete(contact);
		}
		return "redirect:/user/show-contacts/" + currentPage;
	}

	// open update form handler
	@GetMapping("/update-contact/{cId}")
	public String updateContact(@PathVariable("cId") Integer cId, Model model) {

		model.addAttribute("title", "Update-Contact");
		model.addAttribute("opration", "update");
		Contact contact = this.contactDao.findById(cId).get();

		// check...
		if (user.getId() == contact.getUser().getId()) {
			System.out.println("delete file = " + saveFile + contact.getImage());
			model.addAttribute("contact", contact);
		}
		return "user/add_contact_form";
	}
}
