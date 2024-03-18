package service;

import dtos.request.LoginRequest;
import dtos.request.RegisterRequest;

public interface DiaryServices {
    void register(RegisterRequest registerRequest);
    long getNumberOfUsers();

    void login(LoginRequest loginRequest);
    void logout();

    boolean isLoggedIn();
}
