package com.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.dao.FreelancerDAO;
import com.model.Client;
import com.model.Freelancer;

import jakarta.servlet.http.HttpSession;

@Service
public class FreelancerService {
	private final FreelancerDAO freelancerDAO;

	public FreelancerService(FreelancerDAO freelancerDAO) {
		this.freelancerDAO = freelancerDAO;
	}

	public Freelancer getFreelancer(String email, String password) {
		Freelancer f = freelancerDAO
				.find(email, password)
				.orElseThrow(() -> new RuntimeException("User not found"));
		if (f.getDescription() == null) {
			f.setDescription("-");
		}
		return f;
	}

	/**
	 * Finds all freelancers, who sent applications on any order of current client
	 * 
	 * @param model takes email and password from model to find current user in
	 *              database
	 * @return the found freelancers
	 */
	public List<Freelancer> getAllFreelancersByClientId(int clientId) {
		return freelancerDAO.findAllByClientId(clientId);

	}

	public void save(Freelancer freelancer, HttpSession session) {
		String role = session.getAttribute("role").toString();
		if (role.equals("ADMIN") || role.equals("FREELANCER")) {
			freelancerDAO.saveChanges(freelancer);
			session.setAttribute("userEmail", freelancer.getEmail());
			session.setAttribute("password", freelancer.getPassword());
		} else {
			 throw new RuntimeException("Wrong role, no changes allowed");
		}

	}

	public Freelancer getFreelancerById(int id) {
		Freelancer f = freelancerDAO.find(id).orElseThrow(() -> new RuntimeException("User not found"));
		return f;
	}

	public List<Freelancer> getAllFreelancers() {
		List<Freelancer> c = freelancerDAO.findAll();
		return c;
	}

	public Freelancer getFreelancer(int id) {
		Freelancer f = freelancerDAO.find(id).orElseThrow(() -> new RuntimeException("User not found"));
		if (f.getDescription() == null) {
			f.setDescription("No description");
		}
		return f;
	}

	public void deleteFreelancer(String email, String password) {
		freelancerDAO.deleteById(getFreelancer(email,password).getId());
		
	}

}
