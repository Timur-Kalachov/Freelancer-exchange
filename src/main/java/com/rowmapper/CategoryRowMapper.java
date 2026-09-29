package com.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.model.Category;

@Repository
public class CategoryRowMapper implements RowMapper<Category> {
	
	public CategoryRowMapper() {
		
	}

	@Override
	public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Category(rs.getInt("category_id"),rs.getString("name"));
	}

}
