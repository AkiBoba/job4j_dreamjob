package ru.job4j.dreamjob.model;

import java.io.Serializable;
import java.util.Objects;

public class Post implements Serializable {
    private int id;
    private String name;
    private String description;
    private String created;
    private City city;

    public Post(int id, String name, String description, String created) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
    }

    public Post(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getCreated() {
        return created;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id && name.equals(post.name) && description.equals(post.description)
                && created.equals(post.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, created);
    }

}
