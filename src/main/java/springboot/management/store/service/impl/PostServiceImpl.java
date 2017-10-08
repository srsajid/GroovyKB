package springboot.management.store.service.impl;

import springboot.management.store.entities.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import springboot.management.store.repository.PostRepository;
import springboot.management.store.service.PostService;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public Post save(Post post) {
        postRepository.save(post);
        return post;
    }

    @Override
    public Post findOne(String id) {
        return postRepository.findOne(id);
    }

    @Override
    public Iterable<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Page<Post> findByTagsName(String tagName, PageRequest pageRequest) {
        return postRepository.findByTagsName(tagName, pageRequest);
    }
}
