package com.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.RowMapper.ClientRowMapper;
import com.model.Client;
import com.model.Freelancer;

import jakarta.servlet.http.HttpSession;

@Repository
public class ClientDAO {
	private final JdbcTemplate jdbcTemplate;
	private final BaseDAO baseDAO;

	public ClientDAO(JdbcTemplate jdbcTemplate,BaseDAO baseDAO) {
		this.jdbcTemplate = jdbcTemplate;
		this.baseDAO=baseDAO;
	}

	public void save(Client c) {

		jdbcTemplate.update("""
				    insert into client (name, email, password)
				    VALUES (?, ?, ?)
				""", c.getName(), c.getEmail(), c.getPassword());

	}

	public void saveChanges(Client c) {
		String sql = "UPDATE client SET name = ?,email=?, password=?,description = ?, company_company_id=? where (client_id = ?)";
		jdbcTemplate.update(sql, c.getName(),c.getEmail(),c.getPassword(), c.getDescription(),c.getCompanyId(), c.getId());

	}

	public Optional<Client> find(String email, String password) {
		try {
			String sql = "select * from client where email = ? AND password = ?";
			Client client = jdbcTemplate.queryForObject(sql, new ClientRowMapper(), email, password);
			
			return Optional.ofNullable(client);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}
	public Optional<Client> findWithName(String email, String name) {
		try {
			String sql = "select * from client where email = ? AND name = ?";

			Client client = jdbcTemplate.queryForObject(sql, new ClientRowMapper(), email, name);
			return Optional.ofNullable(client);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}
	public Optional<Client> findWithEmail(String email) {
		try {
			String sql = "select * from client where email = ? ";

			Client client = jdbcTemplate.queryForObject(sql, new ClientRowMapper(), email);
			return Optional.ofNullable(client);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public List<Client> findAll() {
		String sql = "select * from freelance_exchange.client";

		List<Client> clients = jdbcTemplate.query(sql, new ClientRowMapper());
		return clients;
	}

	public Optional<Client> find(int id) {
		try {
			String sql = "select * from client where client_id=?";

			Client client = jdbcTemplate.queryForObject(sql, new ClientRowMapper(), id);
			return Optional.ofNullable(client);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public void deleteById(int id) {
		baseDAO.deleteById("client","client_id", id);
		
	}



}
