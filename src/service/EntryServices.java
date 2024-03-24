package service;

import data.model.Entry;
import dtos.request.DeleteEntryRequest;
import dtos.request.UpdateEntryRequest;

import java.util.List;

public interface EntryServices {
    void save(Entry entry);
    List<Entry> findAllEntriesBy(String author);
    void updateEntry(UpdateEntryRequest updateEntryRequest);
    void deleteEntry(DeleteEntryRequest deleteEntryRequest);
    Entry findBy(int id);

}

