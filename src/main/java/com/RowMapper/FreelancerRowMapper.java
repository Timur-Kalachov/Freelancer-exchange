package com.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.model.Freelancer;

public class FreelancerRowMapper implements RowMapper<Freelancer> {

	@Override
	public Freelancer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Freelancer f = new Freelancer();
		f.setId(rs.getInt("freelancer_id"));
		f.setExperience(rs.getDouble("experience"));
		f.setName(rs.getString("name"));
		f.setEmail(rs.getString("email"));
		f.setPassword(rs.getString("password"));
		f.setDescription(rs.getString("description"));
		f.setPortfolio(rs.getString("portfolio"));
		return f;
	}

}
