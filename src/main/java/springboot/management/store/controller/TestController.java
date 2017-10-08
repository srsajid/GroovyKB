package springboot.management.store.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import grails.transaction.Transactional;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springboot.management.store.model.Book;
import springboot.management.store.model.Publisher;
import springboot.management.store.service.TestService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

/**
 * Created by sajedur on 1/31/2017.
 */
@Controller
@RequestMapping(value = "test")
public class TestController {
    @Autowired
    TestService testService;

    @ResponseBody

    @RequestMapping(value = "test1")
    String test1() {
        testService.test1();
        return "Sucess";
    }
}
