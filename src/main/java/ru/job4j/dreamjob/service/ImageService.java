package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Image;
import ru.job4j.dreamjob.store.ImageStore;

import java.io.FileNotFoundException;
import java.util.Collection;

@Service
@ThreadSafe
public class ImageService {
    private final ImageStore store;

    public ImageService(ImageStore store) {
        this.store = store;
    }

    public Collection<Image> findAll() {
        return store.findAll();

    }

    public void add(Image image) throws FileNotFoundException {
        store.add(image);

    }
}
