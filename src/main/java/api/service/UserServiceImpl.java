package api.service;

import api.dao.UserDAO;
import api.exceptions.EmailAlreadyRegisteredException;
import api.exceptions.UserAlreadyRegisteredException;
import api.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.SQLException;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final String USER_ALREADY_REGISTERED_MESSAGE = "Пользователь с таким именем уже существует";
    private final String EMAIL_ALREADY_REGISTERED_MESSAGE = "Пользователь с таким email уже существует";
    private UserDAO userDAO;


    @Override
    public void registerUser(String username, String password, String name, String surname, String email) throws SQLException, UserAlreadyRegisteredException, EmailAlreadyRegisteredException {
        Optional<User> findByUsername = userDAO.findByUsername(username);
        if (findByUsername.isPresent()){
            throw new UserAlreadyRegisteredException(USER_ALREADY_REGISTERED_MESSAGE);
        }

        Optional<User> findByEmail = userDAO.findByEmail(email);
        if (findByEmail.isPresent()){
            throw new EmailAlreadyRegisteredException(EMAIL_ALREADY_REGISTERED_MESSAGE);
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(DigestUtils.md5Hex(password));
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);

        userDAO.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) throws SQLException {
        return userDAO.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) throws SQLException {
        return userDAO.findByEmail(email);
    }
}
