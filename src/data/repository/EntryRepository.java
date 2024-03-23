package data.repository;

import data.model.Entry;

import java.util.List;

public interface EntryRepository {
    Entry save (Entry entry);
    List<Entry> findAll();
    Entry findById(int id);
    void updateEntry(Entry entry);
    long count();
    void delete(int id);
    void delete(Entry entry);
    List<Entry> findAllEntriesBy(String author);

    void clear();
}
