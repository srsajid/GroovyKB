package springboot.management.store.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.management.store.model.Book;
import springboot.management.store.model.Publisher;

/**
 * Created by sajedur on 1/31/2017.
 */
public interface PublisherRepository extends JpaRepository <Publisher, Integer>{
}
