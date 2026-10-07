package com.service;

import com.controller.RegistrationStatus;
import com.dao.ClientDAO;
import com.dao.CompanyDAO;
import com.model.Client;
import com.model.Company;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dao.FreelancerDAO;
import com.model.Freelancer;

@Service
public class RegistrationService {

	private final ClientDAO clientDAO;
	private final FreelancerDAO freelancerDAO;
	private final CompanyDAO companyDAO;
	
	public RegistrationService(ClientDAO clientDAO,FreelancerDAO freelancerDAO,CompanyDAO companyDAO) {
		this.clientDAO = clientDAO;
		this.freelancerDAO = freelancerDAO;
		this.companyDAO=companyDAO;
		
	}
	
	
	public RegistrationStatus register(String name, String email, String password, String role) {		
		 if(role.equals("CLIENT")) {
			 Client c =new Client();
			 c.setName(name);
			 c.setEmail(email);
			 c.setPassword(password);
			 c.setCompanyId(companyDAO.save());
			 if(clientDAO.findWithEmail(email).isPresent()) {
				return RegistrationStatus.ALREADY_EXISTS; 
			 }
			 clientDAO.save(c);
			 return RegistrationStatus.SUCCESS;
			 
		 }else if (role.equals("FREELANCER")) {
			 Freelancer f = new Freelancer();
			 f.setName(name);
			 f.setEmail(email);
			 f.setPassword(password);
			 if(freelancerDAO.findWithEmail(email).isPresent()) {
				 return RegistrationStatus.ALREADY_EXISTS;
			 }
			 freelancerDAO.save(f);
			 return RegistrationStatus.SUCCESS;
		 } else {
			 return RegistrationStatus.FAILED;
		 }
		 
	}

}
