package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.service.AuthorisationService;
import com.service.ClientService;
import com.service.FreelancerService;
import com.service.LoginStatus;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthorisationController {
	private final AuthorisationService authService;
	private final ClientService clientService;
	private final FreelancerService freelancerService;

	public AuthorisationController(AuthorisationService authService,ClientService clientService,FreelancerService freelancerService) {
		this.authService = authService;
		this.clientService=clientService;
		this.freelancerService = freelancerService;
	}

	@GetMapping("/login")
	public String loginPage() {
		return "authorisation.html";
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "authorisation.html";
	}

	@GetMapping("/")
	public String indexPage() {
		return "authorisation.html";
	}

	@PostMapping("/login")
	public String login(@RequestParam String email, @RequestParam String password, @RequestParam String role,
			HttpSession session,Model model) {
		LoginStatus status = authService.login(email, password, role);
		if (status != LoginStatus.FAILED) {
			
			session.setAttribute("userEmail", email);
			session.setAttribute("password", password);
			session.setAttribute("role", role);
			if (status == LoginStatus.F_SUCCESS) {
				session.setAttribute("id", freelancerService.getFreelancer(email,password).getId());
				return "redirect:/freelancer-main-page";
			} else if (status == LoginStatus.C_SUCCESS) {
				session.setAttribute("id", clientService.getClient(email,password).getId());
				return "redirect:/client-main-page";
			}
		}

		model.addAttribute("errorMessage", "wrong password or email");
		return "authorisation.html";
	}

}
