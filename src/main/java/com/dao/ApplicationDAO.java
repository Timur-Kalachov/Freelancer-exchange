package com.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.model.Freelancer;
import com.rowmapper.ApplicationRowMapper;
import com.rowmapper.FreelancerRowMapper;
import com.model.Application;

@Repository
public class ApplicationDAO {
	private final JdbcTemplate jdbcTemplate;
	private final BaseDAO baseDAO;
	public ApplicationDAO(JdbcTemplate jdbcTemplate,BaseDAO baseDAO) {
		this.jdbcTemplate = jdbcTemplate;
		this.baseDAO=baseDAO;
	}

	public void create(int orderId, int freelancerId) {
		String sql = "insert into application (freelancer_freelancer_id, order_order_id, date, status) VALUES (?, ?, ?, ?)";
		LocalDate date = LocalDate.now();
		jdbcTemplate.update(sql, freelancerId, orderId, date.toString(), "in processing");
	}

	public Optional<Application> findApplication(int id) {
		String sql = "select * from application  where application_id=?";
		Application appl = jdbcTemplate.queryForObject(sql, new ApplicationRowMapper(), id);
		return Optional.ofNullable(appl);
	}

	public Optional<Application> findApplication(int orderId, int freelancerId) {
		String sql = "select * from application  where order_order_id=? and freelancer_freelancer_id=?";
		try {
			Application appl = jdbcTemplate.queryForObject(sql, new ApplicationRowMapper(), orderId, freelancerId);
			return Optional.ofNullable(appl);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public void setStatus(int id,String status) {

		String sql = "Update freelance_exchange.application set status=? where(application_id=?)";

		jdbcTemplate.update(sql, status, id);

	}
	
	public void deleteById(int application_id) {
		baseDAO.deleteById("application", "application_id", application_id);
	}

	public List<Application> findAllApplicationsByAnyOrderId(int orderId) {
		String sql = "select * from application  join `order` on `order`.order_id=order_order_id \r\n"
				+ "where `order`.client_client_id=(select client_client_id from `order` where order_id =? )";
		List<Application> applications = jdbcTemplate.query(sql, new ApplicationRowMapper(), orderId);

		return applications;
	}

	public List<Application> findAllApplicationsByClient(int clientId,String sort) {
		String sql = "select * from application  join `order` on `order`.order_id=order_order_id  where client_client_id =? ";
		sql = addSort(sql, sort);
		List<Application> applications = jdbcTemplate.query(sql, new ApplicationRowMapper(), clientId);

		return applications;
	}

	public List<Application> findAllApplicationsByFreelancer(int freelancerId,String sort) {
		String sql = "select * from application where freelancer_freelancer_id =? ";
		sql = addSort(sql, sort);
		List<Application> applications = jdbcTemplate.query(sql, new ApplicationRowMapper(), freelancerId);

		return applications;
	}

	public List<Application> findAllApplicationsByClientAndSortByExperience(int clientId,String sort) {
		String sql = "select * from application join `order` on order_id=order_order_id  join freelancer on freelancer_id=freelancer_freelancer_id  where client_client_id =? ";
		sql = addSort(sql, sort);
		addSort(sql,sort);
		List<Application> applications = jdbcTemplate.query(sql, new ApplicationRowMapper(), clientId);
		return applications;
	}
	
	private String addSort(String sql,String sort) {
		if (sort.equals("dateUp")) {
			sql += " order by application_id ASC";
		} else if (sort.equals("experienceUp")) {
			sql += " order by experience ASC";
		} else if (sort.equals("experienceDown")) {
			sql += " order by experience DESC";
		} else {
			sql += " order by application_id DESC";
		}
		
		
		return sql;
	}
	
	public void deleteByOrderId(int orderId) {
		baseDAO.deleteById("application", "order_order_id",orderId );
	}

}
