package api.dao;

import api.model.Task;
import api.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TaskDAO {
    List<Task> findAll() throws SQLException;

    Optional<Task> findById(Long id) throws SQLException;

    long save(Task task) throws SQLException;

    int update(Task task) throws SQLException;

    long deleteById(Long id) throws SQLException;

    List<Task> findByExecutor(User user) throws SQLException;
}
