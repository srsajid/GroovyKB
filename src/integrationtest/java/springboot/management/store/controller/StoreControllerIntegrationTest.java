package springboot.management.store.controller;

import springboot.management.store.AbstractControllerIntegrationTest;
import springboot.management.store.service.StoreManagementService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



public class StoreControllerIntegrationTest extends AbstractControllerIntegrationTest {

	@Autowired
	StoreManagementService storeManagementService;
	
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void testPlainLoadStore() throws Exception {

		String uri = "/loadstore";

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		System.out.println("Status is :" + status);
		System.out.println("content is :" + content);
		Assert.assertTrue(status == 200);
		Assert.assertTrue(content.trim().length() > 0);
	}
	
	
	@Test
	public void testEditStore3() throws Exception {

		String uri = "/editstore/3";

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri))
				.andExpect(MockMvcResultMatchers.view().name("editstore"))
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		System.out.println("Status is :" + status);
		System.out.println("content is :" + content);
		Assert.assertTrue(status == 200);
		Assert.assertTrue(content.trim().length() > 0);

	}
	
	@Test
	public void testDeleteStore3() throws Exception {

		String uri = "/deletestore/3";

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri))
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		System.out.println("Status is :" + status);
		System.out.println("content is :" + content);
		Assert.assertTrue(status == 200);
		Assert.assertTrue(content.trim().length() > 0);

	}
}
