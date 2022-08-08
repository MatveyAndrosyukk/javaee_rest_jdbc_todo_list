package api.service;

import api.dao.UserDAO;
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
public class AuthenticationServiceImpl implements AuthenticationService{
    private UserDAO userDAO;

    @Override
    public boolean isAuthenticated(String email, String password) {
        try {
            Optional<User> findByEmail = userDAO.findByEmail(email);
            if (findByEmail.isEmpty()) return false;

            User user = findByEmail.get();
            return user.getPassword().equals(DigestUtils.md5Hex(password));
        } catch (SQLException exception) {
            log.error(exception.getMessage(), exception);
            exception.printStackTrace();
            return false;
        }
    }
}
