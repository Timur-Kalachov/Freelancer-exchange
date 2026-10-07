package com.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.service.ClientService;
import com.service.FreelancerService;
import com.service.OrderService;

import com.exception.AccessDeniedException;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminController {
	ClientService clientService;
	FreelancerService freelancerService;
	OrderService orderService;
	public AdminController(ClientService clientService,FreelancerService freelancerService,	OrderService orderService){
		this.clientService=clientService;
		this.freelancerService=freelancerService;
		this.orderService=orderService;
	}
	
	
	@GetMapping("/admin")
	public String showAdminPanelPage(@RequestParam(required = false) String password,HttpSession session,Model model) {
		String adminPassword="i87q34ft67ieylvfhujtu874yr43wy7u8";
		if(!adminPassword.equals(password) && !session.getAttribute("role").equals("ADMIN")) {
			throw new AccessDeniedException( session.getAttribute("role").toString(),session.getAttribute("userEmail").toString());
		}
		session.setAttribute("password", adminPassword);
		model.addAttribute("clients",clientService.getAllClients());
		model.addAttribute("freelancers",freelancerService.getAllFreelancers());
		model.addAttribute("orders",orderService.getAllOrders("dateDown"));
		session.setAttribute("email", "admin");
		session.setAttribute("role", "ADMIN");
		session.setAttribute("id", 0);// When admin will try to change someones profile, he need to take this persons id
		return "admin-panel.html";
	}
	
}
