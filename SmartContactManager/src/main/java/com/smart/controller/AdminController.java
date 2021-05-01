package com.smart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String dashBoard() {
		System.out.println("admin/index called");
		return "admin/dash_board";
	}

}
