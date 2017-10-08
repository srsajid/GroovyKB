package springcore.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springcore.bean.MyService;

@Configuration
public class AppConfig {

    @Bean
    public MyService myService() {
        return new MyService();
    }
}
