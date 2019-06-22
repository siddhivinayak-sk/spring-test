package com.rps.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.rps.entities.AppUsers;
import com.rps.repositories.AppUsersRepository;

public class AppUsersServiceImpl implements AppUsersService {
	
	
	@Autowired
	AppUsersRepository repo;
	

	public AppUsers getByFirstName(String firstName) {
		return repo.findByFirstName(firstName);
	}

}
