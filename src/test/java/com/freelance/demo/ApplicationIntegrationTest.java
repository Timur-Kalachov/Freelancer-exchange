package com.freelance.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationIntegrationTest {
	
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void openPages() throws Exception {
    	mockMvc.perform(get("/")).andExpect(status().isOk());
    	mockMvc.perform(get("/register")).andExpect(status().isOk());
    }
    
    @Test
    void testClient() throws Exception{
    	
    	mockMvc.perform(post("/register").param("email", "test!1@test!1").param("password", "testtest!@#!1").param("name", "testtest!@#!1").param("role", "CLIENT")).andExpect(status().is3xxRedirection());
    	mockMvc.perform(post("/login").param("email", "test!1@test!1").param("password", "testtest!@#!1").param("role", "CLIENT")).andExpect(redirectedUrl("/client-main-page"));
    	
    	mockMvc.perform(post("/register").param("email", "test!1@test!1").param("password", "testtest!@#!1").param("name", "testtest!@#!1").param("role", "CLIENT")).andExpect(redirectedUrl("/register"));
    	
    	mockMvc.perform(post("/delete-client-profile").param("email", "test!1@test!1").param("password", "testtest!@#!1")).andExpect(status().is3xxRedirection());
    }
    
    @Test
    void testFreelancer() throws Exception{
    	mockMvc.perform(post("/register").param("email", "test!1@test!1").param("password", "testtest!@#!1").param("name", "testtest!@#!1").param("role", "FREELANCER")).andExpect(status().is3xxRedirection());
    	mockMvc.perform(post("/login").param("email", "test!1@test!1").param("password", "testtest!@#!1").param("role", "FREELANCER")).andExpect(redirectedUrl("/freelancer-main-page"));
    	mockMvc.perform(post("/register").param("email", "test!1@test!1").param("password", "testtest!@#!1").param("name", "testtest!@#!1").param("role", "FREELANCER")).andExpect(redirectedUrl("/register"));
    	
    	mockMvc.perform(post("/delete-freelancer-profile").param("email", "test!1@test!1").param("password", "testtest!@#!1").param("name", "testtest!@#!1").param("role", "CLIENT")).andExpect(status().is3xxRedirection());
    }

}
