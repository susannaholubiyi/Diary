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
    private boolean isLoggedIn;

    @Override
    public void register(RegisterRequest registerRequest) {
        isNewUser(registerRequest.getUserName());
            Diary newDiary = new Diary();
            newDiary.setUserName(registerRequest.getUserName());
            newDiary.setPassword(registerRequest.getPassword());
            diaryRepository.save(newDiary);
    }

    private void isNewUser(String userName) {
        var diary = diaryRepository.findById(userName);
        if (diary != null) throw new UserAlreadyExistsException(String.format("%s already exists as a username, kindly reselect", userName));
    }

    @Override
    public long getNumberOfUsers() {
        return diaryRepository.count();
    }

    @Override
    public void login(LoginRequest loginRequest) {
        Diary diary = isRegisteredUser(loginRequest.getUserName());
        String providedPassword = loginRequest.getPassword();

        if(validatePassword(diary.getPassword(), providedPassword)) diary.unlock();
        isLoggedIn = true;
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

    @Override
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

}
