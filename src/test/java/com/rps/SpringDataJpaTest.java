package com.rps;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rps.entities.AppUsers;
import com.rps.repositories.AppUsersRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@DataJpaTest
public class SpringDataJpaTest {

	
	@Autowired
	TestEntityManager em;
	
	@Autowired
	AppUsersRepository apprepo;
	
	@Test
	public void test1() {
		AppUsers user = new AppUsers();
		user.setFirstName("A");
		user.setLastName("B");
		em.persistAndFlush(user);
		
		AppUsers u01 = apprepo.findOne(1L);
		Assert.assertEquals(user.getFirstName(), u01.getFirstName());
	}
	
	
}
