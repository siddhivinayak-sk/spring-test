package com.rps.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rps.entities.AppUsers;
import com.rps.model.AppUsersDTO;
import com.rps.repositories.AppUsersRepository;
import com.rps.repositories.CustomAppUsersRepository;

@RestController
@RequestMapping("/h2")
public class H2ControllerTest {

	@Autowired
	private AppUsersRepository repo;
	
	@Autowired
	private CustomAppUsersRepository repo1;
	
	@GetMapping("/list")
	public ResponseEntity<List<AppUsers>> getList() {
		return new ResponseEntity(repo.findAll(), HttpStatus.OK); 
	}

	@GetMapping("/listj")
	public ResponseEntity<List<AppUsersDTO>> getList1() {
		return new ResponseEntity(repo1.getListAll(), HttpStatus.OK); 
	}
	
	
	
}
