package springboot.management.store.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import springboot.management.store.AbstractControllerUnitTest;
import springboot.management.store.model.Store;
import springboot.management.store.repo.StoreRepository;
import springboot.management.store.service.StoreManagementService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class StoreContollerMocksTest extends AbstractControllerUnitTest {

	@Mock
	StoreManagementService storeService;

	@Mock
	StoreRepository storeRepo;

	@InjectMocks
	StoreManagementController storeController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		setUp(storeController);
	}
	
	//To stub data for service method.

	private List<Store> stubDataGetAllStores() {
		List<Store> stores = new ArrayList<Store>();

		for (int i = 1; i < 3; i++) {
			Store st = new Store();
			st.setStoreName("StubStore" + i);
			stores.add(st);
		}

		return stores;
	}

	@Test
	public void testGetAllStores() throws Exception {

		when(storeService.getAllStores()).thenReturn(stubDataGetAllStores());
		String uri = "/getallstores";

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri))
				.andReturn();

		int status = result.getResponse().getStatus();

		System.out.println("Status is :" + status);

		verify(storeService, times(1)).getAllStores();

		Assert.assertTrue(status == 200);

	}

}
