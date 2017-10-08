package springboot.management.store;

import springboot.management.store.controller.BaseController;

import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebAppConfiguration
public abstract class AbstractControllerUnitTest extends AbstractUnitTest {


	protected MockMvc mockMvc;
	
	protected void setUp(BaseController controller)
	{
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
}
