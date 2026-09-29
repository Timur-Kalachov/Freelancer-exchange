package com.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.model.Category;
import com.model.Company;

public class CompanyRowMapper implements RowMapper<Company> {

	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Company(rs.getInt("company_id"),rs.getString("name"),rs.getString("description"));
	}

}
