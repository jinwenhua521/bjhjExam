package com.nebulae.system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

public class MainErrorController implements ErrorController{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static final String ERROR_PATH = "/error";
	
	@RequestMapping(value = ERROR_PATH)
	public String handleError() {
		return "error/404";
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

}
