package springcore;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springcore.bean.MyService;
import springcore.config.AppConfig;

/**
 * Created by sajedur on 10/20/2016.
 */
public class SpringApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan("springcore.config");
        ctx.refresh();
        MyService myService = ctx.getBean(MyService.class);
        myService.doStuff();
    }

}
