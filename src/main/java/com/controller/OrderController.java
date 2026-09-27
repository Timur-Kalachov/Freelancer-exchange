package com.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model.Category;
import com.model.Order;
import com.service.CategoryService;
import com.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {
	final OrderService orderService;
	final CategoryService categoryService;

	public OrderController(OrderService orderService,CategoryService categoryService) {
		this.orderService = orderService;
		this.categoryService= categoryService;
	}

	@PostMapping("/order/{id}/delete")
	public String deleteOrder(@PathVariable int id, Model model, HttpSession session) {
		orderService.deleteById(id, session);

		return "redirect:/client-main-page";
	}
	@GetMapping("/order/{id}/edit")
	public String editOrderPage(@PathVariable int id, Model model, HttpSession session) {

		Order order = orderService.findById(id);
		List<Category> categories=categoryService.getAllCategories();
		model.addAttribute("currentCategory",categoryService.findByOrderId(order.getId()));
		model.addAttribute("categories",categories);
		model.addAttribute("order", order);
		model.addAttribute("role", session.getAttribute("role"));

		return "editOrder.html";
	}
	@PostMapping("/order/{id}/edit")
	public String editOrder(@PathVariable int id,@RequestParam int categoryId,@RequestParam String name,@RequestParam String description,@RequestParam String budget,
			@RequestParam String deadline,/*@RequestParam String status,*/ HttpSession session) {

		Order order = orderService.findById(id);
		orderService.updateById(id, name, description, budget, deadline, categoryId, "Open", session);//TODO add real status

		return "redirect:/client-main-page";
	}

	@GetMapping("/createOrder")
	public String createOrderPage(HttpSession session , Model model) {
		//TODO add user check
		List<Category> categories=categoryService.getAllCategories();
		model.addAttribute("categories",categories);
		return "create-order.html";
	}
	
	@PostMapping("/createOrder")
	public String createOrder(@RequestParam String orderName, @RequestParam String description,
			@RequestParam int categoryId, @RequestParam String budget, @RequestParam String deadline, Model model,
			HttpSession session) {
		orderService.createOrder(session, orderName, categoryId, description, budget, deadline);
		return "redirect:/client-main-page";
	}
	
	@GetMapping("/order/{id}")
	public String orderDetailsPage(@PathVariable int id, Model model, HttpSession session) {

		Order order = orderService.findById(id);
		model.addAttribute("order", order);
		model.addAttribute("category", categoryService.findByOrderId(order.getId()));
		model.addAttribute("role", session.getAttribute("role"));

		return "order";
	}

}
