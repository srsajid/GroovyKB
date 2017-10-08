package springboot.management.store.service;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import springboot.management.store.model.Account;
import springboot.management.store.model.Customer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by sajedur on 11/2/2016.
 */
@Repository
@Transactional(readOnly = true)
class AccountServiceImpl implements AccountService {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Account save(Account account) {

        if (account.getId() == null) {
            em.persist(account);
            return account;
        } else {
            return em.merge(account);
        }
    }

    @Override
    public List<Account> findByCustomer(Customer customer) {

        TypedQuery query = em.createQuery("select a from Account a where a.customer = :customer", Account.class);
        query.setParameter("customer", customer);

        return query.getResultList();
    }
}