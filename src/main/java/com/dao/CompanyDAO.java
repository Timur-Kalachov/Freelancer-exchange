package com.dao;

import java.sql.Statement;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.model.Company;
import com.rowmapper.CompanyRowMapper;

@Repository
public class CompanyDAO {
	private final JdbcTemplate jdbcTemplate;
	private final BaseDAO baseDAO;

	public CompanyDAO(JdbcTemplate jdbcTemplate, BaseDAO baseDAO) {
		this.jdbcTemplate = jdbcTemplate;
		this.baseDAO = baseDAO;
	}

	public Optional<Company> findById(int id) {
		Company c = baseDAO.findById("company", "company_id", id, new CompanyRowMapper());
		return Optional.ofNullable(c);
	}

	public Optional<Company> find(String name, String description) {
		try {
			String sql = "select * from company where name = ? AND description = ?";

			Company cResult = jdbcTemplate.queryForObject(sql, new CompanyRowMapper(), name, description);
			return Optional.ofNullable(cResult);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public int save() {
		String sql = "insert into company (name, description) VALUES (' ', ' ')";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS), keyHolder);

		return keyHolder.getKey().intValue();
	}

	public void save(Company company) {

		jdbcTemplate.update("insert into company (name, description) VALUES (?, ?)", company.getName(),
				company.getDescription());

	}

	public void saveChanges(Company company) {
		jdbcTemplate.update("update company set name=?, description=? where company_id=?", company.getName(),
				company.getDescription(), company.getId());

	}

}
