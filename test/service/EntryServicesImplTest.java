package service;

import data.model.Entry;
import data.repository.DiaryRepository;
import data.repository.DiaryRepositoryImplement;
import data.repository.EntryRepository;
import data.repository.EntryRepositoryImplement;
import dtos.request.EntryRequest;
import dtos.request.LoginRequest;
import dtos.request.RegisterRequest;
import exceptions.exceptions.NotRegisteredUserException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntryServicesImplTest {

    private EntryServices entryServices;
    private DiaryServices diaryServices;
    private EntryRepository entryRepository;
    private DiaryRepository diaryRepository;
    @BeforeEach
    public void setUp() {
        entryServices = new EntryServicesImpl();
        diaryServices = new DiaryServicesImplements();
        entryRepository = new EntryRepositoryImplement();
        diaryRepository = new DiaryRepositoryImplement();
    }
    @AfterEach
    public void tearDown(){
        entryRepository.findAll().clear();
        diaryRepository.findAll().clear();
        entryRepository.clear();

        diaryRepository.clear();
    }
    @Test
    public void userCanFindEntryByTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("username");
        registerRequest.setPassword("password");
        diaryServices.register(registerRequest);

        assertEquals(1L, diaryServices.getNumberOfUsers());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("username");
        loginRequest.setPassword("password");
        diaryServices.login(loginRequest);
        assertTrue(diaryServices.findUserBy("username").isLocked());

        EntryRequest entryRequest = new EntryRequest();
        entryRequest.setTitle("title");
        entryRequest.setBody("body");
        entryRequest.setAuthor("author");
        diaryServices.createEntry(entryRequest);
        assertEquals(1, entryServices.findAllEntriesBy(entryRequest.getAuthor()).size());
        assertEquals(1, entryServices.findAllEntriesBy("author").get(0).getId());

    }

    @Test
    public void userCanCreateTwoEntry(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("username");
        registerRequest.setPassword("password");
        diaryServices.register(registerRequest);

        assertEquals(1L, diaryServices.getNumberOfUsers());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("username");
        loginRequest.setPassword("password");
        diaryServices.login(loginRequest);
        assertTrue(diaryServices.findUserBy("username").isLocked());

        EntryRequest entryRequest = new EntryRequest();
        entryRequest.setTitle("title");
        entryRequest.setBody("body");
        entryRequest.setAuthor("username");
        diaryServices.createEntry(entryRequest);
        assertEquals(1, entryServices.findAllEntriesBy(entryRequest.getAuthor()).size());

        EntryRequest entryRequest2 = new EntryRequest();
        entryRequest2.setTitle("new title");
        entryRequest2.setBody("new body");
        entryRequest2.setAuthor("username");
        diaryServices.createEntry(entryRequest);
        assertEquals(2, entryServices.findAllEntriesBy(entryRequest.getAuthor()).size());
    }

    @Test
    public void userThatIsNotRegisteredCannotCreateEntryTest(){
        EntryRequest entryRequest = new EntryRequest();
        entryRequest.setTitle("title");
        entryRequest.setBody("body");
        entryRequest.setAuthor("username");
        assertThrows(NotRegisteredUserException.class,() ->diaryServices.createEntry(entryRequest));
    }

}
