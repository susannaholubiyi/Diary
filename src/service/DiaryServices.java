package service;

import data.model.Diary;
import dtos.request.EntryRequest;
import dtos.request.LoginRequest;
import dtos.request.RegisterRequest;

public interface DiaryServices {
    void register(RegisterRequest registerRequest);
    long getNumberOfUsers();

    void login(LoginRequest loginRequest);
    void logout(String name);

    Diary findUserBy(String username);

    void createEntry(EntryRequest entryRequest);
    void clear();

}
