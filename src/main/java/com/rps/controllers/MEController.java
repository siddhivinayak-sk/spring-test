package com.rps.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/me")
public class MEController {

	
	@Value("${server.port}")
	private String serverPort;
	

	@ResponseBody
	@RequestMapping("/port")
	public String getport() {
		return serverPort;
	}
}
