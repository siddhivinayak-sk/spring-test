package com.rps.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rps.entities.AppUsers;

@Transactional
@Repository
public interface AppUsersRepository extends JpaRepository<AppUsers, Long> {

	
	public AppUsers findByFirstName(String firstName);
	
	
}
