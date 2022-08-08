package api.service;

import api.dao.TaskDAO;
import api.model.Task;
import api.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService{
    private TaskDAO taskDAO;

    @Override
    public Optional<Task> findById(Long id) throws SQLException {
        return taskDAO.findById(id);
    }


    @Override
    public List<Task> findAll() throws SQLException {
        return taskDAO.findAll();
    }


    @Override
    public List<Task> findByExecutor(User user) throws SQLException {
        return taskDAO.findByExecutor(user);
    }

    @Override
    public void save(Task task) throws SQLException {
        taskDAO.save(task);
    }

    @Override
    public void update(Task task) throws SQLException {
        taskDAO.update(task);
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        taskDAO.deleteById(id);
    }
}
