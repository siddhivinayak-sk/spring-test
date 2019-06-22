package com.rps.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rps.repositories.DataService;

@Service
public class BusinessService {
	
	@Autowired
	private DataService dataService;

	public void setDataService(DataService dataService) {
		this.dataService = dataService;
	}
	
	public int findTheGreatestFromAllData() {
		int data[] = dataService.retrieveAllData();
		int greatest = Integer.MIN_VALUE;
		for(int i:data) {
			if(i > greatest) {
				greatest = i;
			}
		}
		return greatest;
	}
	

}
