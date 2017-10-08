package springboot.management.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@IntegrationTest("server.port:0")
@Transactional
public abstract class AbstractControllerIntegrationTest extends AbstractIntegrationTest {

	protected MockMvc mockMvc;
	@Autowired
	protected WebApplicationContext webAppContext;
	
	protected void setUp()
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
	}

	
}
