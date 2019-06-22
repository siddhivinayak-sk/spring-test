package com.rps.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class HomeController {

	@RequestMapping("/")
	public String home(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		return "index";
	}

	
	


}
