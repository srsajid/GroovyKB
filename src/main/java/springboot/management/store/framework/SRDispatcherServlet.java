package springboot.management.store.framework;

import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by sajedur on 10/9/2016.
 */
public class SRDispatcherServlet extends DispatcherServlet {

    public SRDispatcherServlet() {
        super();
    }

    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.doService(request, response);
    }

}
