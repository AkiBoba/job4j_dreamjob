package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateDbStore;
import ru.job4j.dreamjob.store.CandidateStore;

import java.util.Collection;
import java.util.Map;

@Service
@ThreadSafe
public class CandidateService {
    private final CandidateDbStore store;

    public CandidateService(CandidateDbStore store) {
        this.store = store;

    }

    public Map<Integer, byte[]> findPhotos() {
        return store.findPhotos();
    }

    public Collection<Candidate> findAll() {
        return store.findAll();

    }

    public void add(Candidate candidate) {
        store.add(candidate);

    }

    public Candidate getById(int id) {
        return store.findById(id);
    }

    public void update(Candidate candidate) {
        store.update(candidate);
    }

    public byte[] getPhotoRepo(Integer candidateId) {
        return store.getPhotoRepo(candidateId);
    }
}