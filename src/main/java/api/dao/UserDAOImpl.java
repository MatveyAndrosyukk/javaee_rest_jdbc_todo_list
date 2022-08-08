package api.dao;

import api.configuration.sessionmanager.SessionManager;
import api.model.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class UserDAOImpl implements UserDAO{
    private final SessionManager sessionManager;

    public UserDAOImpl(final SessionManager sessionManager){
        this.sessionManager = sessionManager;
    }
    @Override
    public int save(User user) throws SQLException {
        sessionManager.beginSession();

        try(Connection connection = sessionManager.getCurrentSession();
            PreparedStatement statement = connection.prepareStatement(SQL_QUERIES.INSERT_USER.QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setString(5, user.getEmail());

            statement.executeUpdate();
            try(ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();

                int id = resultSet.getInt(1);
                sessionManager.commitSession();
                return id;
            }
        }catch (SQLException exception){
            log.error(exception.getMessage(), exception);
            sessionManager.rollbackSession();
            throw exception;
        }
    }

    @Override
    public Optional<User> findByUsername(String username) throws SQLException {
        User user = null;
        sessionManager.beginSession();

        try(Connection connection = sessionManager.getCurrentSession();
            PreparedStatement statement = connection.prepareStatement(SQL_QUERIES.GET_USER_BY_USERNAME.QUERY)) {
            statement.setString(1, username);

            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()){
                    user = new User();
                    user.setId(Integer.parseInt(resultSet.getString("id")));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setName(resultSet.getString("name"));
                    user.setSurname(resultSet.getString("surname"));
                    user.setEmail(resultSet.getString("email"));
                }
            }
        }catch (SQLException exception){
            log.error(exception.getMessage(), exception);
            sessionManager.rollbackSession();
            throw exception;
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByEmail(String email) throws SQLException {
        User user = null;
        sessionManager.beginSession();

        try(Connection connection = sessionManager.getCurrentSession();
            PreparedStatement statement = connection.prepareStatement(SQL_QUERIES.GET_USER_BY_EMAIL.QUERY)) {
            statement.setString(1, email);

            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()){
                    user = new User();
                    user.setId(Integer.parseInt(resultSet.getString("id")));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setName(resultSet.getString("name"));
                    user.setSurname(resultSet.getString("surname"));
                    user.setEmail(resultSet.getString("email"));
                }
            }
        }catch (SQLException exception){
            log.error(exception.getMessage(), exception);
            sessionManager.rollbackSession();
            throw exception;
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findById(long id) throws SQLException {
        User user = null;
        sessionManager.beginSession();

        try(Connection connection = sessionManager.getCurrentSession();
            PreparedStatement statement = connection.prepareStatement(SQL_QUERIES.GET_USER_BY_ID.QUERY)) {
            statement.setLong(1, id);

            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()){
                    user = new User();
                    user.setId(Integer.parseInt(resultSet.getString("id")));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setName(resultSet.getString("name"));
                    user.setSurname(resultSet.getString("surname"));
                    user.setEmail(resultSet.getString("email"));
                }
            }
        }catch (SQLException exception){
            log.error(exception.getMessage(), exception);
            sessionManager.rollbackSession();
            throw exception;
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();

        sessionManager.beginSession();
        try (Connection connection = sessionManager.getCurrentSession();
             PreparedStatement statement = connection.prepareStatement(SQL_QUERIES.GET_ALL_USERS.QUERY)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(parseUser(resultSet));
                }
            }
        } catch (SQLException exception) {
            log.error(exception.getMessage(), exception);
            sessionManager.rollbackSession();
            throw exception;
        }
        return users;
    }

    enum SQL_QUERIES {
        GET_ALL_USERS("SELECT * FROM users"),
        GET_USER_BY_USERNAME("SELECT * FROM users WHERE username = (?)"),
        GET_USER_BY_ID("SELECT * FROM users WHERE id = (?)"),
        GET_USER_BY_EMAIL("SELECT * FROM users WHERE email = (?)"),
        INSERT_USER("INSERT INTO users (username, password, name, surname, email) VALUES ((?),(?),(?),(?),(?))");


        final String QUERY;

        SQL_QUERIES(String QUERY) {
            this.QUERY = QUERY;
        }
    }

    private User parseUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(Integer.parseInt(resultSet.getString("id")));
        user.setUsername(resultSet.getString("username"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setEmail(resultSet.getString("email"));

        return user;
    }
}
