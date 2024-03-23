package service;

import data.model.Entry;
import dtos.request.UpdateRequest;

import java.util.List;

public interface EntryServices {
    void save(Entry entry);
    List<Entry> findAllEntriesBy(String author);
    void updateEntry(UpdateRequest updateRequest);
    void deleteEntry(String author);

}

