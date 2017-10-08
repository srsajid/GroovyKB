package springboot.management.store.service;

import springboot.management.store.model.Customer;
import springboot.management.store.model.Account;

import java.util.List;

/**
 * Created by sajedur on 11/2/2016.
 */
public interface AccountService {

    public Account save(Account account);
    public List<Account> findByCustomer(Customer customer);

}
