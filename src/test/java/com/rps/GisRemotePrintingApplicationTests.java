package com.rps;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.rps.services.PrintService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GisRemotePrintingApplicationTests {

	@Autowired
	private PrintService printService;
	
	@Test
	public void test1() {
		//Assert.assertArrayEquals(printService.getPrinterList().toArray(), printService.getActualPrinterList().toArray());
	}

	@Test
	public void test2() {
		Assert.assertEquals(1, 1);
	}
	
}

