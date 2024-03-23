package data.repository;

import data.model.Entry;

import java.util.ArrayList;
import java.util.List;

public class EntryRepositoryImplement implements EntryRepository{
    private int counter = 0;
    private static List<Entry> entries = new ArrayList<>();
    @Override
    public Entry save(Entry entry) {
        if (isNew(entry)) {
            createIdFor(entry);
            entries.add(entry);
        }
        else updateEntry(entry);
        return entry;
    }

    private void createIdFor(Entry entry) {
        entry.setId(++counter);
    }

    private boolean isNew(Entry entry) {
        return entry.getId() == 0;
    }

    @Override
    public List<Entry> findAll() {

        return entries;
    }

    @Override
    public Entry findById(int id) {
        for (Entry entry : entries){
            if(entry.getId() == id) return entry;
        }
        return null;
    }

    @Override
    public void updateEntry(Entry entry) {
        for (Entry findEntry :entries) {
            if(findEntry.getId() == entry.getId()) {
                findEntry.setBody(entry.getBody());
                findEntry.setTitle(entry.getTitle());

            }
        }

    }
    @Override
    public List<Entry> findAllEntriesBy(String author) {
        List<Entry> allEntriesOfUser = new ArrayList<>();
        for (Entry entry: entries){
            if (entry.getAuthor().equalsIgnoreCase(author)){
                allEntriesOfUser.add(entry);
            }
        }
        return allEntriesOfUser;
    }

    @Override
    public void clear() {
        entries.clear();
    }

    @Override
    public long count() {
        return entries.size();
    }

    @Override
    public void delete(int id) {
        for (Entry entry : entries){ if(entry.getId() == id) entries.remove(entry); break;}
    }

    @Override
    public void delete(Entry entry) {
        for (Entry entryToBeDeleted : entries){
            if (entry.equals(entryToBeDeleted)) entries.remove(entry);
            break;
        }
    }

}
