package api.service;

public interface AuthenticationService {
    boolean isAuthenticated(String username, String password);
}
