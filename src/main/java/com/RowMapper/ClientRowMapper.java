package com.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.model.Order;
import com.model.Client;

public class ClientRowMapper implements RowMapper<Client>  {

	@Override
	public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
		 Client c = new Client();

	        c.setId(rs.getInt("client_id"));
	        c.setName(rs.getString("name"));
	        c.setEmail(rs.getString("email"));
	        c.setPassword(rs.getString("password"));
	        c.setDescription(rs.getString("description"));
	        c.setCompanyId(rs.getInt("company_company_id"));
	        return c;
	}

}
