package com.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


import com.model.Application;

public class ApplicationRowMapper implements RowMapper<Application> {

	@Override
	public Application mapRow(ResultSet rs, int rowNum) throws SQLException {
		Application a = new Application();
        a.setId(rs.getInt("application_id"));
        a.setFreelancerId(rs.getInt("freelancer_freelancer_id"));
        a.setOrderId(rs.getInt("order_order_id"));
        a.setStatus(rs.getString("status"));
        return a;
	}

}
