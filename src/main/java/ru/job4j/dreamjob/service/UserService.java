package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.store.PostDBStore;
import ru.job4j.dreamjob.store.UserDBStore;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@ThreadSafe
public class UserService {

    private final UserDBStore store;

    public UserService(UserDBStore store) {
        this.store = store;

    }

    public Collection<User> findAll() {
        return store.findAll();

    }
/*
    public Optional<User> add(User user) {
        return store.add(user);

    }

 */
    public Optional<User> add(User user) {
        return store.add(user);

    }

    public User findById(int id) {
        return store.findById(id);
    }

    public void update(User user) {
        store.update(user);
    }

    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        return store.findUserByEmailAndPwd(email, password);
    }
}
