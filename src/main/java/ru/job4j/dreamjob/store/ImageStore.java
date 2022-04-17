package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Image;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ImageStore {

    private final BasicDataSource pool;

    public ImageStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Image> findAll() {
        List<Image> images = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM image")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    images.add(new Image(it.getInt("id"),
                            it.getString("name"),
                            it.getBytes("bytes"),
                            it.getInt("candidateId")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }

    public Image add(Image image) throws FileNotFoundException {
        byte[] bytes = image.getBytes();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("insert into image(name,"
                            + " bytes, candidateId) values (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, image.getName());
            ps.setBytes(2, bytes);
            ps.setInt(3, image.getCandidateId());
            ps.executeUpdate();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    image.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Image();
    }
}
