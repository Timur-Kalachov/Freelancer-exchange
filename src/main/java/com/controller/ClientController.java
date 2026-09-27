package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.model.Client;
import com.model.Company;
import com.service.OrderService;
import com.service.ClientService;
import com.service.CompanyService;
import com.service.FreelancerService;
import com.service.ApplicationService;
import com.service.CategoryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ClientController {

	private final OrderService orderService;
	private final ClientService clientService;
	private final CategoryService categoryService;
	private final CompanyService companyService;
	
	

	public ClientController(OrderService orderService, ClientService clientService,
			ApplicationService applicationService,CategoryService categoryService, CompanyService companyService,FreelancerService freelancerService) {
		this.orderService = orderService;
		this.clientService = clientService;
		this.categoryService=categoryService;
		this.companyService = companyService;
	}

	@GetMapping("/client-main-page")
	public String mainPage(Model model,@RequestParam(defaultValue = "dateDown") String sort,@RequestParam(defaultValue = "none") String filter, HttpSession session) {
		int clientId=Integer.valueOf(session.getAttribute("id").toString());
		model.addAttribute("userName",clientService.getClient(clientId).getName());
		Client client=clientService.getClient(session.getAttribute("userEmail").toString(),session.getAttribute("password").toString());
		model.addAttribute("order", orderService.findAllByClient(client,sort));
		model.addAttribute("categories",categoryService.getAllCategories());
		return "client-main-page.html";
	}



	@PostMapping("/save-client-profile")
	public String saveProifile(@RequestParam String name, @RequestParam String description,@RequestParam String email,@RequestParam String password,
			@RequestParam String companyName, @RequestParam String companyDescription, Model model,
			HttpSession session) {
		Client c = new Client();
		int clientId=Integer.valueOf(session.getAttribute("id").toString());
		addProfileDataToModel(c,clientId,name,description,email,password);
		int companyId=clientService.getClient(clientId).getCompanyId();
		Company company=new Company(companyId,companyName,companyDescription);
		companyService.save(company);
		c.setCompanyId(companyService.find(company).getId());
		clientService.save(c, session);
		if(session.getAttribute("role").equals("ADMIN")) {
			return "redirect:/admin";
		}
		return "redirect:/client-main-page";
	}
	

	@PostMapping("/delete-client-profile")
	public String deleteProifile(@RequestParam String email,@RequestParam String password) {
		clientService.deleteClient(email,password);
		return "redirect:/";
		
	}
	
	private void addProfileDataToModel(Client client, int id, String name, String description, String email,
			String password) {
		client.setId(id);
		client.setEmail(email);
		client.setPassword(password);
		client.setDescription(description);
		client.setName(name);
	}

	@GetMapping("/client-profile/{id}")
	public String showProfilePage(@PathVariable int id, HttpSession session, Model model) {
		String role=session.getAttribute("role").toString();
		if(role.equals("ADMIN")) {
			session.setAttribute("id", id);
		}
		model.addAttribute("role",role);
		Client client =clientService.getClient(id);
		model.addAttribute("client", client);
		model.addAttribute("company",companyService.findById(client.getCompanyId()));
		if(session.getAttribute("role").equals("ADMIN"))
		model.addAttribute("profileSaveURL", "/save-client-profile");
		return "client-profile.html";
	}
}
