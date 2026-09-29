package com.rowmapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.model.Company;
import com.model.Order;

public class OrderRowMapper implements RowMapper<Order>  {

	@Override
	public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
		Order o = new Order();
        o.setId(rs.getInt("order_id"));
        o.setName(rs.getString("name"));
        o.setDescription(rs.getString("description"));
        o.setBudget(rs.getDouble("budget"));
        Date deadline = rs.getDate("deadline");
        if (deadline != null) {
            o.setDeadline(deadline.toLocalDate());
        }

        return o;
	}

}
