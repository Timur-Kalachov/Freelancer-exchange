package com.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.model.Category;
import com.rowmapper.CategoryRowMapper;

@Repository
public class CategoryDAO {
	private final JdbcTemplate jdbcTemplate;
	private final BaseDAO baseDAO;

	public CategoryDAO(JdbcTemplate jdbcTemplate,BaseDAO baseDAO) {
		this.jdbcTemplate = jdbcTemplate;
		this.baseDAO = baseDAO;
	}

	public List<Category> findAll() {
		String sql = "select category_id, name from category";
		return jdbcTemplate.query(sql, new CategoryRowMapper());
	}

	public void delete(String tableName) {
		String sql = "select category_id, name from category";
		jdbcTemplate.query(sql, new CategoryRowMapper());
	}

	public void save(Category category) {
		String sql = """
				    insert into category (name)
				    VALUES (?)
				""";

		jdbcTemplate.update(sql, category.getName());
	}

	public Category findByOrderId(int orderId) {
		String sql = "select * from category join `order` on category_category_id=category_id where order_id=?";
		return jdbcTemplate.queryForObject(sql, new CategoryRowMapper(),orderId);
		
	}

}
