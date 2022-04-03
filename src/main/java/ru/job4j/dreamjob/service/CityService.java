package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.CityStore;
import ru.job4j.dreamjob.store.PostStore;

import java.util.*;

@Service
@ThreadSafe
public class CityService {

    private final CityStore store;

    public CityService(CityStore store) {
        this.store = store;

    }

    public Collection<City> findAll() {
        return store.getAllCities();

    }

    public void add(City city) {
        store.add(city);

    }

    public City findById(int id) {
        return store.findById(id);
    }

}
