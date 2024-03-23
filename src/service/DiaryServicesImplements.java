package service;

import data.model.Diary;
import data.model.Entry;
import data.repository.DiaryRepository;
import data.repository.DiaryRepositoryImplement;
import data.repository.EntryRepository;
import data.repository.EntryRepositoryImplement;
import dtos.request.EntryRequest;
import dtos.request.LoginRequest;
import dtos.request.RegisterRequest;
import exceptions.exceptions.IncorrectPasswordException;
import exceptions.exceptions.InvalidInputException;
import exceptions.exceptions.NotRegisteredUserException;
import exceptions.exceptions.UserAlreadyExistsException;

import java.util.Objects;

public class DiaryServicesImplements implements DiaryServices{
    private static DiaryRepository diaryRepository = new DiaryRepositoryImplement();
    private static EntryServices entryServices = new EntryServicesImpl();
    @Override
    public void register(RegisterRequest registerRequest) {
        isNewUser(registerRequest.getUserName());
            Diary newDiary = new Diary();
            String cleanedName = cleanUp(registerRequest.getUserName());
            newDiary.setUserName(registerRequest.getUserName());
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
        if(validatePassword(diary.getPassword(), providedPassword)){diary.setLock(true);}
        diaryRepository.save(diary);
    }

    @Override
    public void logout(String username) {
        String cleanedName =  cleanUp(username);
        var diary = findUserBy(cleanedName);
        diary.setLock(false);
    }



    @Override
    public Diary findUserBy(String username) {
        String cleanedName =  cleanUp(username);
        return  diaryRepository.findById(cleanedName);
    }

    @Override
    public void createEntry(EntryRequest entryRequest) {
        Entry newEntry = new Entry();
        newEntry.setTitle(validateRequest(entryRequest.getTitle()));
        newEntry.setBody(validateRequest(entryRequest.getBody()));
        newEntry.setAuthor(validateRequest(entryRequest.getAuthor()));
        newEntry.setId(entryRequest.getId());
        entryServices.save(newEntry);

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
