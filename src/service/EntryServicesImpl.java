package service;

import data.model.Entry;
import data.repository.DiaryRepository;
import data.repository.DiaryRepositoryImplement;
import data.repository.EntryRepository;
import data.repository.EntryRepositoryImplement;
import dtos.request.DeleteEntryRequest;
import dtos.request.UpdateEntryRequest;
import exceptions.exceptions.InvalidId;
import exceptions.exceptions.NoEntryException;
import exceptions.exceptions.NotRegisteredUserException;
import exceptions.exceptions.UserNotLoggedInException;

import java.util.ArrayList;
import java.util.List;


public class EntryServicesImpl implements EntryServices {

    private  final EntryRepository entryRepository = new EntryRepositoryImplement();
    private DiaryRepository diaryRepository = new DiaryRepositoryImplement();
    @Override
    public void save(Entry entry) {
        entryRepository.save(entry);
    }



    @Override
    public List<Entry> findAllEntriesBy(String author) {
        List<Entry> allEntries = entryRepository.findAll();

        List<Entry> allEntriesOfUser = new ArrayList<>();

        for (Entry entry : allEntries){
            if (entry.getAuthor().equalsIgnoreCase(author)) allEntriesOfUser.add(entry);
        }

        if (allEntriesOfUser.size() == 0){throw new NoEntryException("You don't have any entry");}
        else return allEntriesOfUser;
    }
    @Override
    public void updateEntry(UpdateEntryRequest updateEntryRequest) {
        Entry entry = new Entry();
        String username = updateEntryRequest.getAuthor();
        if (isRegisteredUser(username)){
            if (isLoggedIn(username)) {
                entry.setTitle(updateEntryRequest.getTitle());
                entry.setBody(updateEntryRequest.getBody());
                entry.setAuthor(updateEntryRequest.getAuthor());
                entry.setId(updateEntryRequest.getId());
            }
        }
        entryRepository.save(entry);
    }

    @Override
    public void deleteEntry(DeleteEntryRequest deleteEntryRequest) {
        if(isRegisteredUser(deleteEntryRequest.getAuthor())){
            if (isLoggedIn(deleteEntryRequest.getAuthor())) {
                validateId(deleteEntryRequest.getId());
                entryRepository.delete(deleteEntryRequest.getId());
            }
        }
       else throw new NotRegisteredUserException(String.format("%s is not a registered user", deleteEntryRequest.getAuthor()));

    }

    @Override
    public Entry findBy(int id) {
        return entryRepository.findById(id);
    }

    private boolean isLoggedIn(String author){
        boolean condition = !diaryRepository.findById(author).getLock();
        if (condition){
            return  true;
        }
        else throw new UserNotLoggedInException("You are not logged in");
    }

    private void validateId(int id) {
        Entry entry = entryRepository.findById(id);
        if (entry == null) {
            throw new InvalidId(String.format("Entry with ID %s not found", id));
        }
            if (entry.getId()!= id) {
                throw new InvalidId(String.format("%s is not a valid ID", id));}
    }

    private boolean isRegisteredUser(String author) {
        List<Entry> entries = entryRepository.findAll();
        for (Entry entry: entries){
            if (entry.getAuthor().equalsIgnoreCase(author)) return true;
        }
        return false;
    }
}
