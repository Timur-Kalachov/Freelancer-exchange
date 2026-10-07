package com.service;

import org.springframework.stereotype.Service;

import com.dao.FreelancerDAO;

import com.exception.AccessDeniedException;

import com.dao.ClientDAO;

@Service
public class AuthorisationService {
	private final ClientDAO clientDAO;
	private final FreelancerDAO freelancerDAO;

	public AuthorisationService(ClientDAO clientDAO, FreelancerDAO freelancerDAO) {
		this.clientDAO = clientDAO;
		this.freelancerDAO = freelancerDAO;

	}

	public LoginStatus login(String email, String password, String role) {
		LoginStatus status = LoginStatus.FAILED;
		if (email.isBlank() || password.isBlank()) {
			return status;
		}else if (role.equals("CLIENT")) {
			if (clientDAO.find(email, password).isPresent()) {
				status = LoginStatus.C_SUCCESS;
			}
		} else if (role.equals("FREELANCER")) {
			if (freelancerDAO.find(email, password).isPresent()) {
				status = LoginStatus.F_SUCCESS;
			}
		} else {
			throw new AccessDeniedException( role,email);
		}
		return status;
	}

}
