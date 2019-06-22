package com.rps;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rps.repositories.DataService;
import com.rps.services.BusinessService;

import org.junit.Assert;;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class MockTest {

	@MockBean
	DataService dataService;
	
	@Autowired
	BusinessService businessService;
	
	@Test
	public void test1() {
		Mockito.when(dataService.retrieveAllData()).thenReturn(new int[]{99, 11});
		Assert.assertEquals(99, businessService.findTheGreatestFromAllData());
	}
	
	@Test
	public void test2() {
		Mockito.when(dataService.retrieveAllData()).thenReturn(new int[]{9, 8, 2, 0, 11, 3});
		Assert.assertEquals(11, businessService.findTheGreatestFromAllData());
	}
	
	
}
