package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.service.RegistrationService;

import jakarta.servlet.http.HttpSession;

@Controller
public class RegistrationController {
	private final RegistrationService regService;

	public RegistrationController(RegistrationService regService) {
		this.regService = regService;
	}

	@GetMapping("/register")
	public String registerPage() {
		return "registration.html";
	}

	@PostMapping("/register")
	public String register(@RequestParam String name, @RequestParam String email, @RequestParam String password,
			@RequestParam String role, HttpSession session) {

		RegistrationStatus status = regService.register(name, email, password, role);
		if (status.equals(RegistrationStatus.ALREADY_EXISTS)) {
			session.setAttribute("errorMessage","Email is already used");
			return "redirect:/register";
		} else if(status.equals(RegistrationStatus.FAILED)){
			session.setAttribute("errorMessage","Something went wrong");
			return "redirect:/register";
		}
		session.invalidate();
		return "redirect:/login";
	}

}
