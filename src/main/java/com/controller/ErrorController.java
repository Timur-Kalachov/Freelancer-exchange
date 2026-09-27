package com.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {

	@ExceptionHandler(Exception.class)
	public String handleException(Exception exception, Model model) {

		model.addAttribute("errorMessage", exception.getMessage());
		model.addAttribute("errorType", exception.getClass().getSimpleName());
		exception.printStackTrace();

		return "error.html";
	}
}
