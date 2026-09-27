package com.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.dao.OrderDAO;
import com.dao.ApplicationDAO;
import com.dao.ClientDAO;
import com.model.Order;
import com.model.Client;
import jakarta.servlet.http.HttpSession;

@Service
public class OrderService {
	private final OrderDAO orderDAO;
	private final ClientDAO clientDAO;
	private final ApplicationDAO applicationDAO;

	public OrderService(OrderDAO orderDAO, ClientDAO clientDAO, ApplicationDAO applicationDAO) {
		this.orderDAO = orderDAO;
		this.clientDAO = clientDAO;
		this.applicationDAO = applicationDAO;
	}

	public List<Order> getAllOrders(String sort) {
		return orderDAO.findAll(sort);
	}

	public Order findById(int id) {

		return orderDAO.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
	}

	public Map<Integer, Order> createOrdersMap(List<Order> orders) {
		Map<Integer, Order> ordersMap = new HashMap();
		for (Order o : orders) {
			ordersMap.put(o.getId(), o);
		}
		return ordersMap;
	}

	public void createOrder(HttpSession session, String name, int categoryId, String description, String budget,
			String deadline) {

		Client client = clientDAO
				.find(session.getAttribute("userEmail").toString(), session.getAttribute("password").toString())
				.orElseThrow(() -> new RuntimeException("User not found"));
		LocalDate date = LocalDate.now();
		orderDAO.save(name, description, budget, deadline, String.valueOf(client.getId()), categoryId, "Open",
				date.toString());// add real status

	}

	public List<Order> findAllByClient(Client client, String sort) {

		Client c = clientDAO.find(client.getEmail(), client.getPassword())
				.orElseThrow(() -> new RuntimeException("Order not found"));

		List<Order> orders = orderDAO.findAllByClient(c.getId(), sort);
		return orders;
	}

	public void deleteById(int id, HttpSession session) {
		Client c = clientDAO
				.find(session.getAttribute("userEmail").toString(), session.getAttribute("password").toString())
				.orElseThrow(() -> new RuntimeException("User not found"));
		applicationDAO.deleteByOrderId(id);
		orderDAO.deleteById(id, c.getId());
	}

	public void updateById(int id, String name, String description, String budget, String deadline,
			int category_category_id, String status, HttpSession session) {
		clientDAO.find(session.getAttribute("userEmail").toString(), session.getAttribute("password").toString())
				.orElseThrow(() -> new RuntimeException("User not found"));
		orderDAO.update(id, name, description, budget, deadline, category_category_id, status);
	}

	public List<Order> getAllOrdersByFreelancerId(int freelancerId) {

		return orderDAO.findAllByFreelancerId(freelancerId);
	}

}
