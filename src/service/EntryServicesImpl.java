package service;

import data.model.Entry;
import data.repository.EntryRepository;
import data.repository.EntryRepositoryImplement;
import dtos.request.UpdateRequest;
import exceptions.exceptions.InvalidId;
import exceptions.exceptions.NoEntryException;

import java.util.ArrayList;
import java.util.List;


public class EntryServicesImpl implements EntryServices {

    private static final EntryRepository entryRepository = new EntryRepositoryImplement();
    @Override
    public void save(Entry entry) {
        entryRepository.save(entry);
    }



    @Override
    public List<Entry> findAllEntriesBy(String author) {
//        List<Entry> allEntriesOfUser = entryRepository.findAllEntriesBy(author);
//
//        if (allEntriesOfUser.size() == 0){throw new NoEntryException("You don't have any entry");}

        List<Entry> allEntries = entryRepository.findAll();

        List<Entry> allEntriesOfUser = new ArrayList<>();

        for (Entry entry : allEntries){
            if (entry.getAuthor().equalsIgnoreCase(author)) allEntriesOfUser.add(entry);
        }

        if (allEntriesOfUser.size() == 0){throw new NoEntryException("You don't have any entry");}
        else return allEntriesOfUser;
    }

    @Override
    public void updateEntry(UpdateRequest updateRequest) {

    }

    @Override
    public void deleteEntry(String author) {

    }
}
