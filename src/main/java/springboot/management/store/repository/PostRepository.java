package springboot.management.store.repository;

import springboot.management.store.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by hungnguyen on 12/28/14.
 */
public interface PostRepository extends ElasticsearchRepository<Post, String> {

    Page<Post> findByTagsName(String name, Pageable pageable);

}
