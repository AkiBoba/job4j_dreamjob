package ru.job4j.dream.store;


import ru.job4j.dream.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PostStore {

    private static final PostStore INST = new PostStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job",
                "Junior Java", "01-03-20222"));

        posts.put(2, new Post(2, "Middle Java Job",
                "Middle Java", "01-03-2022"));

        posts.put(3, new Post(3, "Senior Java Job",
                "Senior Java", "01-03-2022"));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}