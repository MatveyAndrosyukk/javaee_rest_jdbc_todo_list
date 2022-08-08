package api.service;

import api.exceptions.EmailAlreadyRegisteredException;
import api.exceptions.UserAlreadyRegisteredException;
import api.model.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserService {
    void registerUser(String username, String password, String name, String surname, String email) throws SQLException,
            UserAlreadyRegisteredException, EmailAlreadyRegisteredException;

    Optional<User> findByUsername(String username) throws SQLException;

    Optional<User> findByEmail(String email) throws SQLException;
}
