package springboot.management.store;

import springboot.management.StoreManagementApplication;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(StoreManagementApplication.class)
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {

}
