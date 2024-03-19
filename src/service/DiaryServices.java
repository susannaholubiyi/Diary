package service;

import data.model.Diary;
import dtos.request.LoginRequest;
import dtos.request.RegisterRequest;

public interface DiaryServices {
    void register(RegisterRequest registerRequest);
    long getNumberOfUsers();

    void login(LoginRequest loginRequest);
    void logout();

    Diary findUserBy(String username);
}
