package service;

import data.model.Diary;
import data.model.Entry;
import data.repository.DiaryRepository;
import data.repository.DiaryRepositoryImplement;
import dtos.request.*;
import exceptions.exceptions.*;

import java.util.List;
import java.util.Objects;

public class DiaryServicesImplements implements DiaryServices{
    private static DiaryRepository diaryRepository = new DiaryRepositoryImplement();
    private static EntryServices entryServices = new EntryServicesImpl();
    @Override
    public void register(RegisterRequest registerRequest) {
        isNewUser(registerRequest.getUserName());
            Diary newDiary = new Diary();
            String cleanedName = cleanUp(registerRequest.getUserName());
            newDiary.setUserName(cleanedName);
            newDiary.setPassword(registerRequest.getPassword());
            diaryRepository.save(newDiary);

    }

    private void isNewUser(String username) {
        var diary = diaryRepository.findById(username);
        if (diary != null) throw new UserAlreadyExistsException(String.format("%s already exists as a username, kindly reselect", username));
    }

    @Override
    public long getNumberOfUsers() {
        return diaryRepository.count();
    }

    @Override
    public void login(LoginRequest loginRequest) {
        String cleanedName = cleanUp(loginRequest.getUserName());
        Diary diary = isRegisteredUser(cleanedName);
        String providedPassword = loginRequest.getPassword();
        if(validatePassword(diary.getPassword(), providedPassword)){diary.setLock(false);}
        diaryRepository.save(diary);
    }

    @Override
    public void logout(String username) {
        String cleanedName =  cleanUp(username);
        var diary = findBy(cleanedName);
        diary.setLock(true);
    }



    @Override
    public Diary findBy(String username) {
        String cleanedName =  cleanUp(username);
        return  diaryRepository.findById(cleanedName);
    }

    @Override
    public void createEntry(CreateEntryRequest entryRequest) {
        Entry newEntry = new Entry();
        newEntry.setTitle(validateRequest(entryRequest.getTitle()));
        newEntry.setBody(validateRequest(entryRequest.getBody()));
        if (isValidAuthor(validateRequest(entryRequest.getAuthor()))) {
            if (isLoggedIn(entryRequest.getAuthor())) {
                newEntry.setAuthor(validateRequest(entryRequest.getAuthor()));
                entryServices.save(newEntry);
            }
        }else {
            throw new NotRegisteredUserException(String.format("%s is not a registered user", entryRequest.getAuthor()));
        }

    }

    @Override
    public void clear() {
        diaryRepository.clearDiary();
    }

    @Override
    public void updateEntry(UpdateEntryRequest updateEntryRequest) {
        String cleanedName = cleanUp(updateEntryRequest.getAuthor());
        Diary diary = isRegisteredUser(cleanedName);
        entryServices.updateEntry(updateEntryRequest);
        diaryRepository.save(diary);
    }

    @Override
    public void deleteEntry(DeleteEntryRequest deleteEntryRequest) {
        entryServices.deleteEntry(deleteEntryRequest);
    }

    @Override
    public Entry findBy(int id) {
        return  entryServices.findBy(id);
    }

    @Override
    public List<Entry> findAllEntriesBy(String author) {
        return entryServices.findAllEntriesBy(author);
    }

    private boolean isValidAuthor(String author){
        boolean condition = diaryRepository.findById(author) == null;
        return !condition;
    }
    private boolean isLoggedIn(String author){
        boolean condition = !diaryRepository.findById(author).getLock();
        if (condition){
            return  true;
        }
        else throw new UserNotLoggedInException("You are not logged in");
    }
    private String  validateRequest(String request) {
        if(!Objects.equals(request, "")){
            return request;
        }
        throw new InvalidInputException("Input is invalid");
    }


    private boolean validatePassword(String storedPassword, String providedPassword) {
        if(providedPassword.equals(storedPassword)) return true;
        else throw new IncorrectPasswordException(String.format("%s is incorrect, kindly input correct password", providedPassword));
    }

    private Diary isRegisteredUser(String userName) {
        var diary = diaryRepository.findById(userName);
        if(diary == null) throw new NotRegisteredUserException(String.format("%s is not a registered user, kindly register", userName));

        return diary;
    }
    private String cleanUp(String username) {
        return username.toLowerCase().strip();
    }



}
