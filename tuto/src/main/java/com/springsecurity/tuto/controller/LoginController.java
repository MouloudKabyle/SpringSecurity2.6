package com.springsecurity.tuto.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	@RolesAllowed("USER")
	@GetMapping("/*")
	public String getUser() {
		return "Hello i am User";
	}
	@RolesAllowed({"USER","ADMIN"})
	@GetMapping("/admin")
	public String getAdmin() {
		return "Hello i am Admin";
	}


}
