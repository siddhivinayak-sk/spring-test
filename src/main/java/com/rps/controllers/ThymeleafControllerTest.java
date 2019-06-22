package com.rps.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


//Thymeleaf is lib which converts HTML to XHTML and create template for web-based UI creation.
//It will work only when InternalResourceViewResolver will be disabled
//Create templates directory into resources directory and place the HTML files to be viewed

@Controller
public class ThymeleafControllerTest {

	@RequestMapping("/one")
	public String getOne() {
		return "one";
	}
}
