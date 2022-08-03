package api.service;

public interface AuthenticationService {
    boolean isUsernameExists(String username);

    boolean isAuthenticated(String username, String password);
}
