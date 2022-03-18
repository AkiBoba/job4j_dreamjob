package ru.job4j.dream.store;

import org.springframework.stereotype.Repository;
import ru.job4j.dream.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PostStore {

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private final AtomicInteger ids = new AtomicInteger(4);

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job",
                "Junior Java", "01-03-20222"));

        posts.put(2, new Post(2, "Middle Java Job",
                "Middle Java", "01-03-2022"));

        posts.put(3, new Post(3, "Senior Java Job",
                "Senior Java", "01-03-2022"));
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        post.setId(ids.incrementAndGet());
        posts.put(post.getId(), post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void update(Post post) {
        posts.put(post.getId(), post);
    }
}