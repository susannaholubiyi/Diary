package africa.semicolon.DiaryThatRemembers.service;

import africa.semicolon.DiaryThatRemembers.dtos.request.*;
import africa.semicolon.DiaryThatRemembers.data.model.Diary;
import africa.semicolon.DiaryThatRemembers.data.model.Entry;


import java.util.List;

public interface DiaryServices {
    void register(RegisterRequest registerRequest);
    long getNumberOfUsers();

    void login(LoginRequest loginRequest);
    void logout(String name);

    Diary findBy(String username);

    void createEntry(CreateEntryRequest entryRequest);
    void updateEntry(UpdateEntryRequest updateEntryRequest);
    void deleteEntry(DeleteEntryRequest deleteEntryRequest);
    Entry findEntryBy(String id);
    List<Entry> findAllEntriesBy(String author);

}
