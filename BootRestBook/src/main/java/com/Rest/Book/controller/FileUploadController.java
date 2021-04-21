package com.Rest.Book.controller;

import javax.servlet.Servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Rest.Book.helper.FileUploadHelper;

@RestController
public class FileUploadController {

	@Autowired
	private FileUploadHelper fileUploadHelper;

	@PostMapping("/upload-file")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getSize());
		System.out.println(file.getContentType());
		System.out.println(file.getName());
		try {
			// check empty
			if (file.isEmpty())
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request must containt file.");

			// check for image/jpeg only
			if (!file.getContentType().equals("image/jpeg"))
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("File must have image/jpeg formate.");

			// file upload code
			boolean fileUpload = fileUploadHelper.fileUpload(file);
			if (fileUpload)
				return ResponseEntity.ok(ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/")
						.path(file.getOriginalFilename()).toUriString());
			// return ResponseEntity.ok("File successfully uploaded.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong !!! try again.");

	}
}
