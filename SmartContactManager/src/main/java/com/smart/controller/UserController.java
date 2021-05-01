package com.smart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String dashBoard() {
		System.out.println("user/index called");
		return "user/dash_board";
	}
}
