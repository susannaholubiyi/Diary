package service;

import data.repository.DiaryRepository;
import data.repository.DiaryRepositoryImplement;
import data.repository.EntryRepository;
import data.repository.EntryRepositoryImplement;
import dtos.request.*;
import exceptions.exceptions.NotRegisteredUserException;
import exceptions.exceptions.UserNotLoggedInException;
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
        entryRepository.clear();
        diaryRepository.clearDiary();
    }
    @AfterEach
    public void tearDown(){
        entryRepository.findAll().clear();
        diaryRepository.findAll().clear();
        entryRepository.clear();
        diaryServices.clear();
        diaryRepository.clearDiary();
    }
    @Test
    public void userCanFindAllEntryByUsernameTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("username");
        registerRequest.setPassword("password");
        diaryServices.register(registerRequest);

        assertEquals(1L, diaryServices.getNumberOfUsers());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("username");
        loginRequest.setPassword("password");
        diaryServices.login(loginRequest);
        assertFalse(diaryServices.findBy("username").isLocked());

        CreateEntryRequest entryRequest = new CreateEntryRequest();
        entryRequest.setTitle("title");
        entryRequest.setBody("body");
        entryRequest.setAuthor("username");
        diaryServices.createEntry(entryRequest);
        assertEquals(1, entryServices.findAllEntriesBy(entryRequest.getAuthor()).size());
        assertEquals(1, entryServices.findAllEntriesBy("username").get(0).getId());

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
        assertFalse(diaryServices.findBy("username").isLocked());

        CreateEntryRequest entryRequest = new CreateEntryRequest();
        entryRequest.setTitle("title");
        entryRequest.setBody("body");
        entryRequest.setAuthor("username");
        diaryServices.createEntry(entryRequest);
        assertEquals(1, entryServices.findAllEntriesBy(entryRequest.getAuthor()).size());

        CreateEntryRequest entryRequest2 = new CreateEntryRequest();
        entryRequest2.setTitle("new title");
        entryRequest2.setBody("new body");
        entryRequest2.setAuthor("username");
        diaryServices.createEntry(entryRequest);
        assertEquals(2, entryServices.findAllEntriesBy(entryRequest.getAuthor()).size());
    }

    @Test
    public void userThatIsNotLoggedInCannotCreateEntryTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("username");
        registerRequest.setPassword("password");
        diaryServices.register(registerRequest);

        CreateEntryRequest entryRequest = new CreateEntryRequest();
        entryRequest.setTitle("title");
        entryRequest.setBody("body");
        entryRequest.setAuthor("username");
        assertThrows(UserNotLoggedInException.class,() ->diaryServices.createEntry(entryRequest));
    }
    @Test
    public void userThatIsNotRegisteredCannotCreateEntryTest(){
        CreateEntryRequest entryRequest = new CreateEntryRequest();
        entryRequest.setTitle("title");
        entryRequest.setBody("body");
        entryRequest.setAuthor("username");
        assertThrows(NotRegisteredUserException.class,() ->diaryServices.createEntry(entryRequest));
    }
    @Test
    public void userCanDeleteEntryTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("username");
        registerRequest.setPassword("password");
        diaryServices.register(registerRequest);

        assertEquals(1L, diaryServices.getNumberOfUsers());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("username");
        loginRequest.setPassword("password");
        diaryServices.login(loginRequest);
        assertFalse(diaryServices.findBy("username").isLocked());

        CreateEntryRequest entryRequest = new CreateEntryRequest();
        entryRequest.setTitle("title");
        entryRequest.setBody("body");
        entryRequest.setAuthor("username");
        diaryServices.createEntry(entryRequest);
        assertEquals(1, entryRepository.findAllEntriesBy("username").get(0).getId());
        assertEquals(1, entryServices.findAllEntriesBy(entryRequest.getAuthor()).size());

        CreateEntryRequest entryRequest2 = new CreateEntryRequest();
        entryRequest2.setTitle("second title");
        entryRequest2.setBody("second body");
        entryRequest2.setAuthor("username");
        diaryServices.createEntry(entryRequest2);
        assertEquals(2, entryRepository.findAllEntriesBy("username").get(1).getId());
        assertEquals(2, entryServices.findAllEntriesBy(entryRequest2.getAuthor()).size());

        DeleteEntryRequest deleteEntryRequest = new DeleteEntryRequest();
        deleteEntryRequest.setAuthor("username");
        deleteEntryRequest.setId(1);
        entryServices.deleteEntry(deleteEntryRequest);
        assertEquals(1, entryServices.findAllEntriesBy(entryRequest.getAuthor()).size());

    }
    @Test
    public void userCanUpdateEntryTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("username");
        registerRequest.setPassword("password");
        diaryServices.register(registerRequest);

        assertEquals(1L, diaryServices.getNumberOfUsers());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("username");
        loginRequest.setPassword("password");
        diaryServices.login(loginRequest);
        assertFalse(diaryServices.findBy("username").isLocked());

        CreateEntryRequest entryRequest = new CreateEntryRequest();
        entryRequest.setTitle("title");
        entryRequest.setBody("body");
        entryRequest.setAuthor("username");
        diaryServices.createEntry(entryRequest);
        assertEquals(1, entryServices.findAllEntriesBy(entryRequest.getAuthor()).size());

        UpdateEntryRequest updateEntryRequest = new UpdateEntryRequest();
        updateEntryRequest.setTitle("new title");
        updateEntryRequest.setBody("new body");
        updateEntryRequest.setAuthor("username");
        updateEntryRequest.setId(1);
        entryServices.updateEntry(updateEntryRequest);
        assertEquals("new title",entryServices.findBy(1).getTitle() );
        assertEquals("new body",entryServices.findBy(1).getBody() );
    }

}
