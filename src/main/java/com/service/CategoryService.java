package com.service;

import java.util.List;
import com.dao.CategoryDAO;
import com.model.Category;

import org.springframework.stereotype.Service;

@Service
public class CategoryService {
	 private final CategoryDAO categoryDAO;

	    public CategoryService(CategoryDAO categoryDAO) {
	        this.categoryDAO = categoryDAO;
	    }

	    public List<Category> getAllCategories() {
	        return categoryDAO.findAll();
	    }

		public Category findByOrderId(int orderId) {
			return categoryDAO.findByOrderId(orderId);
			
		}
}
