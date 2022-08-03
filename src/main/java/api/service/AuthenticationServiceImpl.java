package api.service;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AuthenticationServiceImpl implements AuthenticationService{
    private final Map<String, String> userData = new ConcurrentHashMap<>();

    {
        userData.put("admin", DigestUtils.md5Hex("admin"));
    }

    @Override
    public boolean isUsernameExists(String username) {
        return this.userData.containsKey(username);
    }

    @Override
    public boolean isAuthenticated(String username, String password) {
        if (!isUsernameExists(username)) return false;

        return this.userData.get(username).equals(DigestUtils.md5Hex(password));
    }
}
