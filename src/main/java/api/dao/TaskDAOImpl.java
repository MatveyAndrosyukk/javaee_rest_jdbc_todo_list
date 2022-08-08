package api.dao;

import api.configuration.sessionmanager.SessionManager;
import api.model.Task;
import api.model.User;
import api.util.TimeUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Getter
public class TaskDAOImpl implements TaskDAO {
    private final SessionManager sessionManager;

    public TaskDAOImpl(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public List<Task> findAll() throws SQLException {
        List<Task> tasks = new ArrayList<>();

        sessionManager.beginSession();
        try (Connection connection = sessionManager.getCurrentSession();
             PreparedStatement statement = connection.prepareStatement(SQL_QUERIES.GET_ALL_TASKS.QUERY)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    tasks.add(parseTask(resultSet));
                }
            }
        } catch (SQLException exception) {
            log.error(exception.getMessage(), exception);
            sessionManager.rollbackSession();
            throw exception;
        }
        return tasks;
    }

    @Override
    public Optional<Task> findById(Long id) throws SQLException {
        Task task = null;

        sessionManager.beginSession();
        try (Connection connection = sessionManager.getCurrentSession();
             PreparedStatement statement = connection.prepareStatement(SQL_QUERIES.GET_TASK_BY_ID.QUERY)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    task = parseTask(resultSet);
                }
            }
        } catch (SQLException exception) {
            log.error(exception.getMessage(), exception);
            sessionManager.rollbackSession();
            throw exception;
        }

        return Optional.ofNullable(task);
    }

    @Override
    public List<Task> findByExecutor(User user) throws SQLException {
        List<Task> tasks = new ArrayList<>();

        sessionManager.beginSession();
        try (Connection connection = sessionManager.getCurrentSession();
             PreparedStatement statement = connection.prepareStatement(SQL_QUERIES.GET_TASK_BY_EXECUTOR.QUERY)) {
            statement.setLong(1, user.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    tasks.add(parseTask(resultSet));
                }
            }
        } catch (SQLException exception) {
            log.error(exception.getMessage(), exception);
            sessionManager.rollbackSession();
            throw exception;
        }
        return tasks;
    }

    @Override
    public long save(Task task) throws SQLException {
        sessionManager.beginSession();

        try (Connection connection = sessionManager.getCurrentSession();
             PreparedStatement pst = connection.prepareStatement(SQL_QUERIES.INSERT_TASK.QUERY, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, task.getTitle());
            pst.setString(2, task.getDescription());
            pst.setDate(3, Date.valueOf(task.getDeadline_date()));
            pst.setBoolean(4, task.isDone());

            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                rs.next();
                long id = rs.getLong(1);
                sessionManager.commitSession();

                saveInJoinTable(task);
                return id;
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            sessionManager.rollbackSession();
            throw ex;
        }

    }

    public void saveInJoinTable(Task task) throws SQLException {
        sessionManager.beginSession();

        try (Connection connection = sessionManager.getCurrentSession();
             PreparedStatement pst = connection.prepareStatement(SQL_QUERIES.INSERT_TASK_IN_TASKS_USERS.QUERY, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, task.getExecutor().getUsername());

            pst.executeUpdate();

            sessionManager.commitSession();

        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            sessionManager.rollbackSession();
            throw ex;
        }
    }

    @Override
    public int update(Task task) throws SQLException {
        sessionManager.beginSession();

        try (Connection connection = sessionManager.getCurrentSession();
             PreparedStatement statement = connection.prepareStatement(SQL_QUERIES.UPDATE_TASK.QUERY)) {
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setDate(3, Date.valueOf(task.getDeadline_date()));
            statement.setBoolean(4, task.isDone());
            statement.setInt(5, task.getId());

            statement.executeUpdate();
            sessionManager.commitSession();

            updateJoinTable(task);
        } catch (SQLException exception) {
            log.error(exception.getMessage(), exception);
            sessionManager.rollbackSession();
            throw exception;
        }

        return task.getId();
    }

    public void updateJoinTable(Task task) throws SQLException {
        sessionManager.beginSession();

        try (Connection connection = sessionManager.getCurrentSession();
             PreparedStatement statement = connection.prepareStatement(SQL_QUERIES.UPDATE_TASK_IN_TASKS_USERS.QUERY)) {
            statement.setString(1, task.getExecutor().getUsername());
            statement.setInt(2, task.getId());

            statement.executeUpdate();
            sessionManager.commitSession();
        } catch (SQLException exception) {
            log.error(exception.getMessage(), exception);
            sessionManager.rollbackSession();
            throw exception;
        }
    }

    @Override
    public long deleteById(Long id) throws SQLException {
        deleteByIdFromJoinTable(id);

        sessionManager.beginSession();
        try (Connection connection = sessionManager.getCurrentSession();
             PreparedStatement pst = connection.prepareStatement(SQL_QUERIES.DELETE_TASK_BY_ID.QUERY, Statement.RETURN_GENERATED_KEYS)) {
            pst.setLong(1, id);
            pst.executeUpdate();
            sessionManager.commitSession();

        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            sessionManager.rollbackSession();
            throw ex;
        }
        return id;
    }

    public void deleteByIdFromJoinTable(Long id) throws SQLException {
        sessionManager.beginSession();
        try (Connection connection = sessionManager.getCurrentSession();
             PreparedStatement pst = connection.prepareStatement(SQL_QUERIES.DELETE_TASK_FROM_TASKS_USERS.QUERY)) {
            pst.setLong(1, id);
            pst.executeUpdate();
            sessionManager.commitSession();

        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            sessionManager.rollbackSession();
            throw ex;
        }
    }

    enum SQL_QUERIES {
        GET_ALL_TASKS("SELECT tasks.id, tasks.title, tasks.description, tasks.deadline_date, tasks.done," +
                "users.username, users.password, users.name, users.surname, users.email " +
                "FROM tasks INNER JOIN tasks_users ON tasks.id = tasks_users.task_id " +
                "INNER JOIN users ON users.id = tasks_users.user_id"),
        GET_TASK_BY_ID("SELECT tasks.id, tasks.title, tasks.description, tasks.deadline_date, tasks.done," +
                "users.username, users.password, users.name, users.surname, users.email " +
                "FROM tasks INNER JOIN tasks_users ON tasks.id = tasks_users.task_id " +
                "INNER JOIN users ON users.id = tasks_users.user_id " +
                "WHERE tasks.id = (?)"),
        GET_TASK_BY_EXECUTOR("SELECT tasks.id, tasks.title, tasks.description, tasks.deadline_date, tasks.done," +
                "users.username, users.password, users.name, users.surname, users.email " +
                "FROM tasks INNER JOIN tasks_users ON tasks.id = tasks_users.task_id " +
                "INNER JOIN users ON users.id = tasks_users.user_id " +
                "WHERE users.id = (?)"),
        INSERT_TASK(
                "INSERT INTO tasks (title, description, deadline_date, done) VALUES ((?), (?),(?), (?))"),
        INSERT_TASK_IN_TASKS_USERS("INSERT INTO tasks_users(task_id,user_id)" +
                " VALUES ((SELECT max(id) from tasks),(SELECT users.id from users" +
                " WHERE username=(?)))"),
        UPDATE_TASK("UPDATE tasks SET title=(?), description=(?), deadline_date=(?), done=(?) WHERE id=(?)"),
        UPDATE_TASK_IN_TASKS_USERS("UPDATE tasks_users " +
                "SET user_id=(SELECT users.id from users where username=(?)) " +
                "WHERE task_id=(?)"),
        DELETE_TASK_BY_ID("DELETE from tasks where id=(?)"),
        DELETE_TASK_FROM_TASKS_USERS("DELETE FROM tasks_users WHERE task_id=(?)");

        final String QUERY;

        SQL_QUERIES(String QUERY) {
            this.QUERY = QUERY;
        }
    }

    private Task parseTask(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUsername(resultSet.getString("username"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setEmail(resultSet.getString("email"));

        Task task = new Task();
        task.setId(Integer.parseInt(resultSet.getString("id")));
        task.setTitle(resultSet.getString("title"));
        task.setDescription(resultSet.getString("description"));
        task.setDeadline_date(TimeUtils.convertSQLDateToLocalDate(resultSet.getDate("deadline_date")));
        task.setDone(resultSet.getBoolean("done"));
        task.setExecutor(user);

        return task;
    }
}
