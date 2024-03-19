package service;

import data.model.Diary;
import data.repository.DiaryRepository;
import data.repository.DiaryRepositoryImplement;
import dtos.request.LoginRequest;
import dtos.request.RegisterRequest;
import service.exceptions.IncorrectPasswordException;
import service.exceptions.NotRegisteredUserException;
import service.exceptions.UserAlreadyExistsException;

public class DiaryServicesImplements implements DiaryServices{
    DiaryRepository diaryRepository = new DiaryRepositoryImplement();

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
        var diary = diaryRepository.findById(cleanedName);
        return diary;
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
