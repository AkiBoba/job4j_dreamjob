package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class CandidateStore {

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private final AtomicInteger ids = new AtomicInteger(3);

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Ivanov",
                "Junior", "01-03-2022"));

        candidates.put(2, new Candidate(2, "Petrov",
                "Middle", "01-03-2022"));

        candidates.put(3, new Candidate(3, "Sidorov",
                "Senior", "01-03-2022"));
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public void add(Candidate candidate) {
        candidate.setId(ids.incrementAndGet());
        candidates.put(candidate.getId(), candidate);
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public void update(Candidate candidate) {
        candidates.put(candidate.getId(), candidate);
    }
}