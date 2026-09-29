package com.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.model.Freelancer;
import com.model.Order;
import com.rowmapper.FreelancerRowMapper;
import com.rowmapper.OrderRowMapper;

@Repository
public class OrderDAO {
	private final JdbcTemplate jdbcTemplate;

	public OrderDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Order> findAllByClient(int client_id, String sort) {
		String sql = "select * from freelance_exchange.order where client_client_id = ?";
		sql = addSort(sql, sort);
		List<Order> orders = jdbcTemplate.query(sql, new OrderRowMapper(), client_id);
		return orders;
	}


	public List<Order> findAll(String sort) {
		String sql = "select * from freelance_exchange.order";
		sql = addSort(sql, sort);
		List<Order> orders = jdbcTemplate.query(sql, new OrderRowMapper());
		return orders;
	}

	private String addSort(String sql,String sort) {
		if (sort.equals("budgetUp")) {
			sql += " ORDER BY budget DESC";
		} else if (sort.equals("budgetDown")) {
			sql += " ORDER BY budget ASC";
		} else if (sort.equals("dateUp")) {
			sql += " ORDER BY order_id ASC";
		} else {
			sql += " ORDER BY order_id DESC";
		}
		
		
		return sql;
	}


	public void save(String name, String description, String budget, String deadline, String client_client_id,
			int category_category_id, String status, String data) {

		String sql = "insert into freelance_exchange.order ( description, deadline, name, client_client_id, category_category_id, status, budget, date) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";

		jdbcTemplate.update(sql, description, deadline, name, client_client_id, category_category_id, status, budget,
				data);

	}

	public void update(int id, String name, String description, String budget, String deadline,
			int category_category_id, String status) {

		String sql = "Update freelance_exchange.order set name=?, description=?, budget=?,deadline=?,category_category_id=?,status=? where(order_id=?)";

		jdbcTemplate.update(sql, name, description, budget, deadline, category_category_id, status, id);

	}

	public Optional<Order> findById(int id) {

		try {
			String sql = "select * from freelance_exchange.order where order_id = ?";
			Order order = jdbcTemplate.queryForObject(sql, new OrderRowMapper(), id);
			return Optional.ofNullable(order);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public void deleteById(int id, int client_client_id) {
		String sql = "DELETE from freelance_exchange.order where (order_id = ?) and (client_client_id = ?)";
		jdbcTemplate.update(sql, id, client_client_id);

	}

	public List<Order> findAllByFreelancerId(int freelancerId) {
		String sql = "select * from `order` inner join application on `order`.order_id = application.order_order_id\r\n"
				+ "  where`application`.freelancer_freelancer_id = ?";

		List<Order> orders = jdbcTemplate.query(sql, new OrderRowMapper(), freelancerId);
		return orders;
	}

}
