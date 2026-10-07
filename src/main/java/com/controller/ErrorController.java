package com.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.exception.UserNotFoundException;
import com.exception.AccessDeniedException;
import com.exception.OrderNotFoundException;

@ControllerAdvice
public class ErrorController {

	@ExceptionHandler(Exception.class)
	public String handleException(Exception exception, Model model) {
		model.addAttribute("errorCode", "500");
		model.addAttribute("errorMessage", ("Something went wrong"));
		exception.printStackTrace();

		return "error.html";
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public String handleUserNotFoundException(Exception exception, Model model) {

		model.addAttribute("errorCode", "404");
		model.addAttribute("errorMessage", exception.getMessage());
		exception.printStackTrace();

		return "error.html";
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public String handleAccessDeniedException(Exception exception, Model model) {

		model.addAttribute("errorCode", "403");
		model.addAttribute("errorMessage", exception.getMessage());
		exception.printStackTrace();

		return "error.html";
	}
	
	@ExceptionHandler(OrderNotFoundException.class)
	public String handleOrderNotFoundException(Exception exception, Model model) {

		model.addAttribute("errorCode", "404");
		model.addAttribute("errorMessage", exception.getMessage());
		exception.printStackTrace();

		return "error.html";
	}
}
