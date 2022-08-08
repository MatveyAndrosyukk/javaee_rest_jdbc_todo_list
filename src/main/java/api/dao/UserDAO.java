package api.dao;

import api.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDAO {
    int save(User user) throws SQLException;

    Optional<User>  findByUsername(String username) throws SQLException;

    Optional<User> findByEmail(String email) throws SQLException;

    Optional<User> findById(long id) throws SQLException;

    List<User> findAll() throws SQLException;
}
