package com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.dao.ClientDAO;
import com.dao.CompanyDAO;
import com.model.Client;
import com.model.Company;
import com.model.Freelancer;

import com.exception.AccessDeniedException;
import com.exception.UserNotFoundException;
import jakarta.servlet.http.HttpSession;

@Service
public class ClientService {
	private final ClientDAO clientDAO;
	public ClientService(ClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}

	public Map<Integer, Freelancer> createFreelancersMap(List<Freelancer> freelancers) {
		Map<Integer, Freelancer> freelancersMap = new HashMap();
		for (Freelancer f : freelancers) {
			freelancersMap.put(f.getId(), f);
			
		}
		return freelancersMap;
	}

	public Client getClient(String email,String password) {
		Client c = clientDAO
				.find(email, password)
				.orElseThrow(() -> new UserNotFoundException(email,password));
		if (c.getDescription() == null) {
			c.setDescription("No description");
		}
		return c;
	}

	public List<Client> getAllClients() {
		List<Client> c = clientDAO.findAll();
		return c;
	}

	public void save(Client client, HttpSession session) {
		String role = session.getAttribute("role").toString();
		if (role.equals("ADMIN") || role.equals("CLIENT")) {
			clientDAO.saveChanges(client);
			session.setAttribute("userEmail", client.getEmail());
			session.setAttribute("password", client.getPassword());
		} else {
			throw new AccessDeniedException( session.getAttribute("role").toString(),session.getAttribute("userEmail").toString());
		}
	}

	public Client getClient(int id) {
		Client c = clientDAO.find(id).orElseThrow(() -> new UserNotFoundException(id));
		if (c.getDescription() == null) {
			c.setDescription("-");
		}
		return c;
	}

	public void deleteClient(String email,String password) {
		clientDAO.deleteById(getClient(email,password).getId());
		
	}

}
