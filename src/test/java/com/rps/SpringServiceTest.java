package com.rps;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.rps.entities.AppUsers;
import com.rps.repositories.AppUsersRepository;
import com.rps.services.AppUsersService;
import com.rps.services.AppUsersServiceImpl;

import org.junit.Assert;

@RunWith(SpringRunner.class)
public class SpringServiceTest {

	@TestConfiguration
	static class EmployeeServiceImplTestContextConfiguration {
		
		@Bean
		public AppUsersService appUsersService() {
			return new AppUsersServiceImpl();
		}
	}
	
	
	@MockBean
	AppUsersRepository repo;
	
	
	@Autowired
	AppUsersService appUsersService;
	
	
	@Before
	public void beforeTest1() {
		AppUsers a1 = new AppUsers();
		a1.setId(1);
		a1.setFirstName("A");
		a1.setLastName("B");
		Mockito.when(repo.findByFirstName("A")).thenReturn(a1);
	}
	
	
	@Test
	public void test1() {
		String name = "A";
		AppUsers u01 = appUsersService.getByFirstName("A");
		Assert.assertEquals(name, u01.getFirstName());
	}
	
	
	
	
}
