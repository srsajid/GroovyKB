package springboot.management.store.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.management.store.model.Book;

/**
 * Created by sajedur on 1/31/2017.
 */
public interface BookRepository extends JpaRepository <Book, Integer>{
}
