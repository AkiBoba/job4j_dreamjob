package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class CityStore {

    private final Map<Integer, City> cities = new HashMap<>();
    private final AtomicInteger ids = new AtomicInteger(3);

    public CityStore() {
        cities.put(1, new City(1, "Москва"));
        cities.put(2, new City(2, "СПб"));
        cities.put(3, new City(3, "Екб"));
    }

    public List<City> getAllCities() {
        return new ArrayList<>(cities.values());
    }

    public City findById(int id) {
        return cities.get(id);
    }

    public void add(City city) {
        List<String> namesOfCity = new ArrayList<>();
        String name = city.getName();
        cities.values().forEach(v -> namesOfCity.add(v.getName()));
        if (!namesOfCity.contains(name)) {
            city.setId(ids.incrementAndGet());
            cities.put(city.getId(), city);
        }
        System.out.println(cities);
    }

}
