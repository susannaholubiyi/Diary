package service;

import data.model.Diary;
import data.model.Entry;
import dtos.request.*;

import java.util.List;

public interface DiaryServices {
    void register(RegisterRequest registerRequest);
    long getNumberOfUsers();

    void login(LoginRequest loginRequest);
    void logout(String name);

    Diary findBy(String username);

    void createEntry(CreateEntryRequest entryRequest);
    void clear();
    void updateEntry(UpdateEntryRequest updateEntryRequest);
    void deleteEntry(DeleteEntryRequest deleteEntryRequest);
    Entry findBy(int id);
    List<Entry> findAllEntriesBy(String author);

}
