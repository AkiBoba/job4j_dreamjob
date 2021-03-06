package ru.job4j.dreamjob.model;

import java.util.Objects;

public class Candidate {
    private int id;
    private String name;
    private String desc;
    private String created;
    private byte[] photo;

    public Candidate(int id, String name, String desc, String created) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.created = created;
    }

    public Candidate(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Candidate() {
    }

    public Candidate(int id, String name, byte[] photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreated() {
        return created;
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
        Candidate candidate = (Candidate) o;
        return id == candidate.id && name.equals(candidate.name)
                && desc.equals(candidate.desc) && created.equals(candidate.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, desc, created);
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}