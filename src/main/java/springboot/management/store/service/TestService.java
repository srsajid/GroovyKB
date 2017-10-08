package springboot.management.store.service;

import grails.transaction.Transactional;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.RequestScope;
import springboot.management.store.model.Book;
import springboot.management.store.model.Publisher;
import springboot.management.store.repo.BookRepository;
import springboot.management.store.repo.PublisherRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

/**
 * Created by sajedur on 1/31/2017.
 */
@Service
@Transactional
public class TestService {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    BookRepository bookRepository;
    @Autowired
    PublisherRepository publisherRepository;
    public String test1() {
        Publisher publisher =new Publisher(RandomStringUtils.random(5));
        publisherRepository.save(publisher);
        Book book = bookRepository.findOne(1);
        book.setName(RandomStringUtils.random(10, true, true));
        Collection<Publisher> publishers = book.getPublishers();
        publishers.add(publisher);
        bookRepository.save(book);
        return "Sucess";
    }
}
