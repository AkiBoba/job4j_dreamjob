package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDBStore {
    private final static String SQL_SELECT_ALL = "select * from users";
    private final static String SQL_INSERT_INTO = "INSERT INTO users(name, email, password, city) VALUES (?, ?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE users SET NAME = ?, EMAIL = ?, CITY = ? WHERE id = ?";
    private final static String SQL_SELECT_BY_ID = "SELECT * FROM users WHERE id = ?";
    private final static String SQL_SELECT_BY_EMAIL_AND_PASSWORD = "SELECT * FROM users WHERE email = ? and password = ?";

    private final BasicDataSource pool;

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SQL_SELECT_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    users.add(new User(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("email"),
                            it.getString("password"),
                            it.getString("city")
                        )
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public Optional<User> add(User user) {
        User result = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SQL_INSERT_INTO,
                     PreparedStatement.RETURN_GENERATED_KEYS)
                                                            ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getCity()
            );
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));

                }
                    result = user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
     return Optional.ofNullable(result);
    }

    public void update(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SQL_UPDATE)
        ) {
            ps.setInt(3, user.getId());
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getCity());
            int res = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public User findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SQL_SELECT_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new User(it.getInt("id"),
                            it.getString("name"),
                            it.getString("email"),
                            it.getString("password"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        User user = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SQL_SELECT_BY_EMAIL_AND_PASSWORD)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    user = new User(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("email"),
                            it.getString("password"),
                            it.getString("city")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }
}
