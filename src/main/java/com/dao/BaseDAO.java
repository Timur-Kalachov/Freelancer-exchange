package com.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
class BaseDAO {
	private final JdbcTemplate jdbcTemplate;

	public BaseDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Finds an entity by its ID.
	 *
	 * @param table database table
	 * @param column ID column
	 * @param id entity ID
	 * @param mapper mapper used to convert the database row into an object
	 * @param <T> type of the returned entity
	 * @return the found entity
	 */
	public <T> T findById(String table, String column, int id, RowMapper<T> mapper) {
		try {
			String sql = "select * from " + table + " where " + column + " =  ?";
			T result = jdbcTemplate.queryForObject(sql, mapper,id);
			return result;
		 }catch(EmptyResultDataAccessException e) {
			return null; 
		 }

	}

	/**
	 * Finds all entities by specified table.
	 *
	 * @param table  database table
	 * @param mapper mapper used to convert the database row into an object
	 * @param <T>    type of the returned entity
	 * @return the found entities
	 */
	public <T> List<T> findAll(String table, RowMapper<T> mapper) {
		String sql = "select * from " + table;
		return jdbcTemplate.query(sql, mapper);
	}

	/**
	 * Finds all entities matching the specified id and sorts them.
	 *
	 * @param table       database table
	 * @param column      ID column
	 * @param id          ID value
	 * @param orderColumn the column to sort by
	 * @param sortType    the type of sort
	 * @param mapper      mapper used to convert the database row into an object
	 * @param <T>         type of the returned entity
	 * @return the found entities
	 */
	public <T> List<T> findAndSort(String table, String column, int id, String orderColumn, SortType sortType,
			RowMapper<T> mapper) {
		String sql = "select * from " + table + " where " + column + " = ?" + " order by " + orderColumn + " "
				+ sortType.name();
		return jdbcTemplate.query(sql, mapper, id);
	}

	/**
	 * Finds all entities by specified table and sorts them.
	 *
	 * @param table       database table
	 * @param orderColumn the column to sort by
	 * @param sortType    the type of sort
	 * @param mapper      mapper used to convert the database row into an object
	 * @param <T>         type of the returned entity
	 * @return the found entities
	 */
	public <T> List<T> findAndSort(String table, String orderColumn, SortType sortType, RowMapper<T> mapper) {
		String sql = "select * from " + table + " order by " + orderColumn + " " + sortType.name();
		return jdbcTemplate.query(sql, mapper);
	}

	/**
	 * Finds all entities by specified table and sorts them in specified range.
	 *
	 * @param table       database table
	 * @param orderColumn the column to sort by
	 * @param sortType    the type of sort
	 * @param minValue    minimum value
	 * @param maxValue    maximum value
	 * @param mapper      mapper used to convert the database row into an object
	 * @param <T>         type of the returned entity
	 * @return the found entities
	 */
	public <T> List<T> findAndSortRange(String table, String orderColumn, String minValue, String maxValue,
			SortType sortType, RowMapper<T> mapper) {
		// TODO not ready
		String sql = "select * from " + table + " order by " + orderColumn + " " + sortType.name();
		return jdbcTemplate.query(sql, mapper);
	}

	/**
	 * Deletes entity by its id.
	 *
	 * @param table  database table
	 * @param column ID column
	 * @param id     entity ID
	 */
	public void deleteById(String table, String column, int id) {

		String sql = "delete from " + table + " where " + column + " = ?";

		jdbcTemplate.update(sql, id);
	}
}
