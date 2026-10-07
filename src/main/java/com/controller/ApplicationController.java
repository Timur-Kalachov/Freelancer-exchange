package com.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model.Application;
import com.model.Order;
import com.model.Freelancer;
import com.service.ApplicationService;
import com.service.CategoryService;
import com.service.ClientService;
import com.service.FreelancerService;
import com.service.OrderService;

import com.exception.AccessDeniedException;
import jakarta.servlet.http.HttpSession;

@Controller
public class ApplicationController {
	private ClientService clientService;
	private FreelancerService freelancerService;
	private OrderService orderService;
	private ApplicationService applicationService;
	private final CategoryService categoryService;

	public ApplicationController(FreelancerService freelancerService,ClientService clientService, OrderService orderService,
			ApplicationService applicationService,CategoryService categoryService) {
		this.applicationService = applicationService;
		this.orderService = orderService;
		this.freelancerService = freelancerService;
		this.clientService=clientService;
		this.categoryService=categoryService;
	}

	@GetMapping("/application/{id}")
	public String applicationPage(@PathVariable int id, HttpSession session, Model model) {
		Application appl = applicationService.getApplication(id, model, session);
		model.addAttribute("appl", appl);
		model.addAttribute("freelancer", freelancerService.getFreelancer(appl.getFreelancerId()));
		model.addAttribute("order", orderService.findById(appl.getOrderId()));
		return "application.html";
	}

	@GetMapping("/client/applications")
	public String clientApplicationsPage( HttpSession session,@RequestParam(defaultValue = "dateDown") String sort, Model model) {
		int userId =Integer.valueOf(session.getAttribute("id").toString());
		String userRole = session.getAttribute("role").toString();
		model.addAttribute("role",userRole);
		if(userRole.equals("CLIENT")) {
			model.addAttribute("categories",categoryService.getAllCategories());
			Map<Integer,Freelancer> freelancersMap = clientService.createFreelancersMap(freelancerService.getAllFreelancersByClientId(userId));
			model.addAttribute("appls", applicationService.getAllApplicationsByClient(userId,sort));
			model.addAttribute("freelancers",freelancersMap );
			
		}  else if(userRole.equals("ADMIN")) {
			//TODO  add admin access
		} else {
			throw new AccessDeniedException(userRole,session.getAttribute("userEmail").toString());
		}
		
		return "applications.html";
	}

	@GetMapping("/freelancer/applications")
	public String freelancerApplicationsPage( HttpSession session,@RequestParam(defaultValue = "dateDown") String sort, Model model) {
		int userId =Integer.valueOf(session.getAttribute("id").toString());
		String userRole = session.getAttribute("role").toString();
		model.addAttribute("role",userRole);
		if(userRole.equals("FREELANCER")) {
			model.addAttribute("categories",categoryService.getAllCategories());
			Map<Integer,Order> ordersMap = orderService.createOrdersMap(orderService.getAllOrdersByFreelancerId(userId));
			model.addAttribute("orders",ordersMap);
			model.addAttribute("appls", applicationService.getAllApplicationsByFreelancer(userId,sort));
			
		} else if(userRole.equals("ADMIN")) {
			//TODO  add admin access
		} else {
			throw new AccessDeniedException(userRole,session.getAttribute("userEmail").toString());
		}
		return "applications.html";
	}

	@PostMapping("/accept-application/{id}")
	public String acceptApplication(@PathVariable int id, HttpSession session, Model model) {
		applicationService.acceptApplication(id, session);
		if (session.getAttribute("role").equals("CLIENT")) {
			return "redirect:/client-main-page";
		}
		return "redirect:/application/" + id;
	}
	
	@PostMapping("/reject-application/{id}")
	public String rejectApplication(@PathVariable int id, HttpSession session, Model model) {
		applicationService.rejectApplication(id, session);
		if (session.getAttribute("role").equals("CLIENT")) {
			return "redirect:/client-main-page";
		}
		return "redirect:/application/" + id;
	}
	@PostMapping("/delete-application/{id}")
	public String deleteApplication(@PathVariable int id, HttpSession session, Model model) {
		String role =session.getAttribute("role").toString();
		if (role.equals("CLIENT")) {
			return "redirect:/client/applications";
		} else if(role.equals("FREELANCER")) {
			applicationService.deleteApplication(id, session);
			return "redirect:/freelancer/applications";
		}
		applicationService.deleteApplication(id, session);
		return "redirect:/application/" + id;
	}
}
