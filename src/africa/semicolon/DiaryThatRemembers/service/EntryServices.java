package africa.semicolon.DiaryThatRemembers.service;

import africa.semicolon.DiaryThatRemembers.data.model.Entry;
import africa.semicolon.DiaryThatRemembers.dtos.request.DeleteEntryRequest;
import africa.semicolon.DiaryThatRemembers.dtos.request.UpdateEntryRequest;

import java.util.List;

public interface EntryServices {
    void save(Entry entry);
    List<Entry> findAllEntriesBy(String author);
    void updateEntry(UpdateEntryRequest updateEntryRequest);
    void deleteEntry(DeleteEntryRequest deleteEntryRequest);
    Entry findBy(String id);

}

