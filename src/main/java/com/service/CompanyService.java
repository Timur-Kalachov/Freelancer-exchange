package com.service;

import org.springframework.stereotype.Service;

import com.dao.CompanyDAO;
import com.model.Company;

@Service
public class CompanyService {
	private final CompanyDAO companyDAO;

	public CompanyService(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public Company findById(int companyId) {
		return companyDAO.findById(companyId).orElse(null);
	}

	public Company find(Company company) {
		return companyDAO.find(company.getName(), company.getDescription()).orElse(null);
	}

	public void save(Company company) {
			companyDAO.saveChanges(company);
	}

}
