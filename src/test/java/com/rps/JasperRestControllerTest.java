package com.rps;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.jasper.report.beans.JasperPage;
import com.jasper.report.beans.JasperReport;


public class JasperRestControllerTest extends AbstractTest {

	@Override
	@Before
	public void setup() {
		super.setup();
	}
	
	
	@Test
	public void test1() throws Exception {
		String uri = "/jasper/attribs";
		MvcResult mr = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
		int status = mr.getResponse().getStatus();
		Assert.assertEquals(status, 200);
		
		String content = mr.getResponse().getContentAsString();
		JasperPage jp = super.mapFromJson(content, JasperPage.class);
		Assert.assertEquals(jp.getFontList().size() > 0, true);
	}

	@Test
	public void test2() throws Exception {
		String uri = "/jasper/read2";
		MvcResult mr = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON).param("filename", "r2.jrxml")).andReturn();
		int status = mr.getResponse().getStatus();
		Assert.assertEquals(status, 200);
		
		String content = mr.getResponse().getContentAsString();
		JasperReport jr = super.mapFromJson(content, JasperReport.class);
		Assert.assertTrue(!jr.getFileName().isEmpty());
	}
	
	@Test
	public void test3() throws Exception {
		/*
		String readuri = "/jasper/read2";
		String uri = "/jasper/write2";
		MvcResult mr = mockMvc.perform(MockMvcRequestBuilders.get(readuri).accept(MediaType.APPLICATION_JSON).param("filename", "r2.jrxml")).andReturn();
		Assert.assertEquals(200, mr.getResponse().getStatus());
		String content = mr.getResponse().getContentAsString();
		
		JasperReport jr = super.mapFromJson(content, JasperReport.class);
		
		MvcResult mr2 = mockMvc.perform(MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON).content(super.mapToJson(jr))).andReturn();
		Assert.assertEquals(200, mr2.getResponse().getStatus());
		JasperReport jr2 = super.mapFromJson(mr2.getResponse().getContentAsString(), JasperReport.class);
		Assert.assertTrue(!jr2.getFileName().isEmpty());
		*/
	}
	
	
	
}
