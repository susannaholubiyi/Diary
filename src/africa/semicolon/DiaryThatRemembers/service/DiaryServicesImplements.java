package africa.semicolon.DiaryThatRemembers.service;

import africa.semicolon.DiaryThatRemembers.dtos.request.*;
import africa.semicolon.DiaryThatRemembers.exceptions.*;
import africa.semicolon.DiaryThatRemembers.data.model.Diary;
import africa.semicolon.DiaryThatRemembers.data.model.Entry;
import africa.semicolon.DiaryThatRemembers.repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DiaryServicesImplements implements DiaryServices{
    @Autowired
    private DiaryRepository diaryRepository ;
    @Autowired
    private EntryServices entryServices;
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
        if (diaryRepository.existsById(username.toLowerCase())) throw new UserAlreadyExistsException(String.format("%s already exists as a username, kindly reselect", username));
    }

    @Override
    public long getNumberOfUsers() {
        return diaryRepository.count();
    }

    @Override
    public void login(LoginRequest loginRequest) {
        String cleanedName = cleanUp(loginRequest.getUserName());
        Diary diary = findBy(cleanedName);
        String providedPassword = loginRequest.getPassword();
        if(validatePassword(diary.getPassword(), providedPassword)){diary.setLocked(false);}
        diaryRepository.save(diary);
    }

    @Override
    public void logout(String username) {
        String cleanedName =  cleanUp(username);
        var diary = findBy(cleanedName);
        diary.setLocked(true);
    }



    @Override
    public Diary findBy(String username) {
        String cleanedName =  cleanUp(username);
        Optional<Diary> diary =  diaryRepository.findById(cleanedName);
        if(diary.isEmpty()) throw new NotRegisteredUserException(String.format("%s is not a registered user, kindly register", username));
        return diary.get();
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
    public void updateEntry(UpdateEntryRequest updateEntryRequest) {
        String cleanedName = cleanUp(updateEntryRequest.getAuthor());
        Diary diary = findBy(cleanedName);
        entryServices.updateEntry(updateEntryRequest);
        diaryRepository.save(diary);
    }

    @Override
    public void deleteEntry(DeleteEntryRequest deleteEntryRequest) {
        entryServices.deleteEntry(deleteEntryRequest);
    }

    @Override
    public Entry findEntryBy(String id) {
        return  entryServices.findBy(id);
    }

    @Override
    public List<Entry> findAllEntriesBy(String author) {
        findBy(author);
        return entryServices.findAllEntriesBy(author);
    }

    private boolean isValidAuthor(String author){
        boolean condition = diaryRepository.findById(author.toLowerCase()).isEmpty();
        return !condition;
    }
    private boolean isLoggedIn(String author){
        boolean condition = !diaryRepository.findById(author).get().isLocked();
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

    private String cleanUp(String username) {
        return username.toLowerCase().strip();
    }



}
