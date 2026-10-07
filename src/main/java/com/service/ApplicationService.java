package com.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.dao.FreelancerDAO;
import com.dao.ClientDAO;
import com.dao.ApplicationDAO;
import com.model.Freelancer;

import com.exception.UserNotFoundException;

import com.model.Application;
import jakarta.servlet.http.HttpSession;

@Service
public class

ApplicationService {
	private final FreelancerDAO freelancerDAO;
	private final ApplicationDAO applicationDAO;

	public ApplicationService(FreelancerDAO freelancerDAO, ApplicationDAO applicationDAO, ClientDAO clientDAO,
			ClientService clientService) {
		this.freelancerDAO = freelancerDAO;
		this.applicationDAO = applicationDAO;
	}

	public void createApplication(int orderId, HttpSession session) {
		Freelancer f = freelancerDAO
				.find(session.getAttribute("userEmail").toString(), session.getAttribute("password").toString())
				.orElseThrow(() -> new UserNotFoundException(session.getAttribute("userEmail").toString(), session.getAttribute("password").toString()));
		if (applicationDAO.findApplication(orderId, f.getId()).isEmpty()) {
			applicationDAO.create(orderId, f.getId());
		}

	}

	public Application getApplication(int applicationId, Model model, HttpSession session) {
		return applicationDAO.findApplication(applicationId).get();

	}

	public void acceptApplication(int applicationId, HttpSession session) {
		if (checkRightsToEditApplicationForClient(applicationId, session)) {
			applicationDAO.setStatus(applicationId, "accepted");
		}
	}

	public List<Application> getAllApplicationsByClient(int clientId, String sort) {
		if (sort.contains("experience")) {
			return applicationDAO.findAllApplicationsByClientAndSortByExperience(clientId, sort);
		}
		return applicationDAO.findAllApplicationsByClient(clientId, sort);
	}

	public List<Application> getAllApplicationsByFreelancer(int freelancerId, String sort) {
		return applicationDAO.findAllApplicationsByFreelancer(freelancerId, sort);
	}

	public void rejectApplication(int applicationId, HttpSession session) {
		if (checkRightsToEditApplicationForClient(applicationId, session)) {
			applicationDAO.setStatus(applicationId, "rejected");
		}

	}

	public void deleteApplication(int applicationId, HttpSession session) {
		if (checkRightsToEditApplicationForFreelancer(applicationId, session)) {
			applicationDAO.deleteById(applicationId);
		}

	}

	private boolean checkRightsToEditApplicationForClient(int applicationId, HttpSession session) {
		boolean idFound = false;
		if (session.getAttribute("role").equals("CLIENT") || session.getAttribute("role").equals("ADMIN")) {
			List<Application> appls = applicationDAO
					.findAllApplicationsByClient(Integer.valueOf(session.getAttribute("id").toString()), null);
			for (int i = 0; i < appls.size(); i++) {
				if (appls.get(i).getId() == applicationId) {
					idFound = true;
					break;
				}
			}
		}
		return idFound;

	}

	private boolean checkRightsToEditApplicationForFreelancer(int applicationId, HttpSession session) {
		if (session.getAttribute("role").equals("FREELANCER") || session.getAttribute("role").equals("ADMIN")) {
			int userId = Integer.valueOf(session.getAttribute("id").toString());
			if (applicationDAO.findApplication(applicationId).get().getFreelancerId() == userId) {
				return true;
			}
		}
		return false;
	}

}
