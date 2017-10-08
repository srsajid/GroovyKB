package springboot.management.store.repo;

import springboot.management.store.model.Store;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends CrudRepository<Store, Long> {

}
