package com.rps.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rps.model.AppUsersDTO;


public interface CustomAppUsersRepository {
	
	public List<AppUsersDTO> getListAll();

}
