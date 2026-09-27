package com.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.RowMapper.FreelancerRowMapper;
import com.model.Freelancer;

import jakarta.servlet.http.HttpSession;

@Repository
public class FreelancerDAO {
	private final JdbcTemplate jdbcTemplate;
	private final BaseDAO baseDAO;

	public FreelancerDAO(JdbcTemplate jdbcTemplate,BaseDAO baseDAO) {
		this.jdbcTemplate = jdbcTemplate;
		this.baseDAO=baseDAO;
	}

	public void save(Freelancer f) {
		jdbcTemplate.update("""
				    insert into freelancer (name, email, password)
				    VALUES (?, ?, ?)
				""", f.getName(), f.getEmail(), f.getPassword());

	}

	public Optional<Freelancer> find(String email, String password) {
		try {
			String sql = "select * from freelancer where email = ? AND password = ?";

			Freelancer freelancer = jdbcTemplate.queryForObject(sql, new FreelancerRowMapper(), email, password);
			return Optional.ofNullable(freelancer);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public Optional<Freelancer> findWithName(String email, String name) {
		try {
			String sql = "select * from freelancer where email = ? AND name = ?";

			Freelancer freelancer = jdbcTemplate.queryForObject(sql, new FreelancerRowMapper(), email, name);
			return Optional.ofNullable(freelancer);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	public Optional<Freelancer> findWithEmail(String email) {
		try {
			String sql = "select * from freelancer where email = ?";

			Freelancer freelancer = jdbcTemplate.queryForObject(sql, new FreelancerRowMapper(), email);
			return Optional.ofNullable(freelancer);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public void saveChanges(Freelancer f) {
		String sql = "UPDATE freelancer SET name = ?,email=?, password=?,description = ?, experience=?, portfolio=? where (freelancer_id = ?)";
		jdbcTemplate.update(sql, f.getName(), f.getEmail(), f.getPassword(), f.getDescription(), f.getExperience(),
				f.getPortfolio(), f.getId());

	}

	public Optional<Freelancer> find(int id) {
		try {
			String sql = "select * from freelancer where freelancer_id = ?";

			Freelancer freelancer = jdbcTemplate.queryForObject(sql, new FreelancerRowMapper(), id);
			return Optional.ofNullable(freelancer);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public List<Freelancer> findAllByClientId(int orderId) {
		String sql = "select * from freelancer inner join application on freelancer.freelancer_id = application.freelancer_freelancer_id \r\n"
				+ "join freelance_exchange.`order` on freelance_exchange.`order`.order_id = application.order_order_id  where freelance_exchange.`order`.client_client_id = ?";

		List<Freelancer> freelancer = jdbcTemplate.query(sql, new FreelancerRowMapper(), orderId);

		return freelancer;
	}

	public List<Freelancer> findAll() {
		String sql = "select * from freelance_exchange.freelancer";

		List<Freelancer> freelancers = jdbcTemplate.query(sql, new FreelancerRowMapper());
		return freelancers;
	}

	public void deleteById(int id) {
		baseDAO.deleteById("freelancer","freelancer_id", id);
		
	}
}
