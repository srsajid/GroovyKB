package springboot.management.store.service

import org.hibernate.Session
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.orm.hibernate5.HibernateTemplate
import org.springframework.stereotype.Service
import spring.boot.model.Store

/**
 * Created by sajedur on 11/1/2016.
 */
@Service
class StoreService {

//    @Autowired
//    HibernateTemplate template;
//    @Autowired
//    SessionFactory sessionFactory
//
    public void save() {
        Session session = this.sessionFactory.openSession();
        List<Store> personList = session.createQuery("from Store").list();
        session.close();
        println(personList)
    }
}
