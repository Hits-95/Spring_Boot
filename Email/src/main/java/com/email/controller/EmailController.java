package com.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.email.model.EmailRequest;
import com.email.service.EmailService;

@RestController
public class EmailController {

	@Autowired
	private EmailService emailService;

	@RequestMapping("/home")
	public String home() {
		return "this is email home controller";
	}

	// API to send mail
	@PostMapping("/send-email")
	public ResponseEntity<?> sendMail(@RequestBody EmailRequest emailRequest) {

		System.out.println(emailRequest);
		if (this.emailService.sendMail(emailRequest.getMessage(), emailRequest.getSubject(), emailRequest.getTo()))
			return ResponseEntity.ok("Mail was sended successfully.");
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email wasn't sended.");

	}
}
