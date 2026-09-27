package com.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model.Freelancer;
import com.model.Order;
import com.model.Category;
import com.model.Client;
import com.service.FreelancerService;
import com.service.OrderService;
import com.service.ApplicationService;
import com.service.CategoryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class FreelancerController {

	private final OrderService orderService;
	private final ApplicationService аpplicationService;
	private final FreelancerService freelancerService;
	private final CategoryService categoryService;

	public FreelancerController(OrderService orderService, ApplicationService аpplicationService,
			FreelancerService freelancerService, CategoryService categoryService) {
		this.orderService = orderService;
		this.аpplicationService = аpplicationService;
		this.freelancerService = freelancerService;
		this.categoryService = categoryService;
	}

	@GetMapping("/freelancer-main-page")
	public String mainPage(Model model, @RequestParam(defaultValue = "dateDown") String sort,
			@RequestParam(defaultValue = "none") String filter, HttpSession session) {
		int freelancerId = Integer.valueOf(session.getAttribute("id").toString());
		model.addAttribute("orders", orderService.getAllOrders(sort));
		model.addAttribute("userName", freelancerService.getFreelancer(freelancerId).getName());
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		return "freelancer-main-page.html";
	}

	@GetMapping("/order/{id}/apply")
	public String applyOffer(@PathVariable int id, HttpSession session) {
		аpplicationService.createApplication(id, session);
		return "redirect:/freelancer-main-page";
	}

	@GetMapping("/freelancer_profile/{id}")
	public String getProfile(@PathVariable int id, Model model, HttpSession session) {
		Freelancer f = freelancerService.getFreelancerById(id);
		String role = session.getAttribute("role").toString();
		if (role.equals("ADMIN")) {
			session.setAttribute("id", id);
		}
		model.addAttribute("freelancer", f);
		model.addAttribute("role", session.getAttribute("role").toString());
		return "freelancer-profile.html";
	}

	@PostMapping("/save-freelancer-profile")
	public String saveProifile(@RequestParam String name, @RequestParam String description, @RequestParam String email,
			@RequestParam String password, @RequestParam double experience, @RequestParam String portfolio,
			HttpSession session) {
		Freelancer f = new Freelancer();
		int userId = Integer.valueOf(session.getAttribute("id").toString());
		addProfileDataToModel(f, userId, name, description, email, password, experience, portfolio);
		freelancerService.save(f, session);
		if (session.getAttribute("role").equals("ADMIN")) {
			return "redirect:/admin";
		}
		return "redirect:/freelancer-main-page";
	}
	
	@PostMapping("/delete-freelancer-profile")
	public String deleteProifile(@RequestParam String email,@RequestParam String password) {
		//TODO add security check
		freelancerService.deleteFreelancer(email,password);
		return "redirect:/";
		
	}
	

	private void addProfileDataToModel(Freelancer freelancer, int id, String name, String description, String email,
			String password, double experience, String portfolio) {
		freelancer.setId(id);
		freelancer.setEmail(email);
		freelancer.setPassword(password);
		freelancer.setDescription(description);
		freelancer.setName(name);
		freelancer.setExperience(experience);
		freelancer.setPortfolio(portfolio);
	}

	@GetMapping("/freelancer-profile/{id}")
	public String showProfilePage(@PathVariable int id, HttpSession session, Model model) {
		model.addAttribute("role", session.getAttribute("role").toString());
		model.addAttribute("freelancer", freelancerService.getFreelancer(id));
		return "freelancer-profile.html";
	}

}
