package service;

import data.repository.DiaryRepository;
import data.repository.DiaryRepositoryImplement;
import data.repository.EntryRepository;
import data.repository.EntryRepositoryImplement;
import dtos.request.CreateEntryRequest;
import dtos.request.LoginRequest;
import dtos.request.RegisterRequest;
import exceptions.exceptions.InvalidInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import exceptions.exceptions.IncorrectPasswordException;
import exceptions.exceptions.NotRegisteredUserException;
import exceptions.exceptions.UserAlreadyExistsException;

import static org.junit.jupiter.api.Assertions.*;

class DiaryServicesImplTest {

    private DiaryServices diaryServices;
    private EntryServices entryServices;
    private DiaryRepository diaryRepository;
    private EntryRepository entryRepository;
    @BeforeEach
    public  void setUp() {
        diaryServices = new DiaryServicesImplements();
        entryServices = new EntryServicesImpl();

        diaryRepository = new DiaryRepositoryImplement();
        entryRepository = new EntryRepositoryImplement();
        diaryRepository.clearDiary();
        entryRepository.clear();
    }
    @AfterEach
    public void tearDown(){
        diaryRepository.clearDiary();
        entryRepository.clear();

    }

    @Test
    public void registerUserTest() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("username");
        registerRequest.setPassword("1111");
        diaryServices.register(registerRequest);
        assertEquals(1L, diaryServices.getNumberOfUsers());
    }
    @Test
    public void registerTwoUserNameTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("username");
        registerRequest.setPassword("1111");
        diaryServices.register(registerRequest);

        RegisterRequest registerRequest2 = new RegisterRequest();
        registerRequest2.setUserName("Second user name");
        registerRequest2.setPassword("0000");
        diaryServices.register(registerRequest2);
        assertEquals(2L, diaryServices.getNumberOfUsers());
    }
    @Test
    public void registerTwoUniqueUserNameTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("username");
        registerRequest.setPassword("1111");
        diaryServices.register(registerRequest);

        RegisterRequest registerRequest2 = new RegisterRequest();
        registerRequest2.setUserName("username");
        registerRequest2.setPassword("0000");
        assertThrows(UserAlreadyExistsException.class, ()->diaryServices.register(registerRequest2));
    }
    @Test
    public void registerOneUser_userCanLoginTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("username");
        registerRequest.setPassword("password");
        diaryServices.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("username");
        loginRequest.setPassword("password");
        diaryServices.login(loginRequest);
        assertFalse(diaryServices.findBy("username").isLocked());
    }
    @Test
    public void userLoginWithoutRegistering_notRegisteredUserExceptionIsThrownTest(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("different user name");
        loginRequest.setPassword("password");
        assertThrows(NotRegisteredUserException.class,()->diaryServices.login(loginRequest));
    }
    @Test
    public void userLoginWithIncorrectPassword_incorrectPasswordExceptionIsThrownTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("username");
        registerRequest.setPassword("password");
        diaryServices.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("username");
        loginRequest.setPassword("different password");
        assertThrows(IncorrectPasswordException.class,()->diaryServices.login(loginRequest));
    }
    @Test
    public void registerUser_userLogin_userLogoutTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("username");
        registerRequest.setPassword("password");
        diaryServices.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("username");
        loginRequest.setPassword("password");
        diaryServices.login(loginRequest);

        assertFalse(diaryServices.findBy("username").isLocked());
        diaryServices.logout("username");
        assertTrue(diaryServices.findBy("username").isLocked());
    }
    @Test
    public void registerUserWithSmallerCase_UserCanLoginWithUpperCaseTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("username");
        registerRequest.setPassword("password");
        diaryServices.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("username");
        loginRequest.setPassword("password");
        diaryServices.login(loginRequest);
        assertEquals(1L, diaryServices.getNumberOfUsers());
    }
    @Test
    public void usernameIsStrippedOfTrailingZeroTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("  username");
        registerRequest.setPassword("password");
        diaryServices.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("username");
        loginRequest.setPassword("password");
        diaryServices.login(loginRequest);
        assertEquals(1L, diaryServices.getNumberOfUsers());
    }
    @Test
    public void createEntryTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("username");
        registerRequest.setPassword("password");
        diaryServices.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("username");
        loginRequest.setPassword("password");
        diaryServices.login(loginRequest);

        CreateEntryRequest entryRequest = new CreateEntryRequest();
        entryRequest.setTitle("title");
        entryRequest.setBody("body");
        entryRequest.setAuthor("username");
        diaryServices.createEntry(entryRequest);
        assertEquals(1, entryServices.findAllEntriesBy(entryRequest.getAuthor()).size());
    }
    @Test
    public void createEntryWithEmptyString_invalidInputExceptionIThrownTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("username");
        registerRequest.setPassword("password");
        diaryServices.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("username");
        loginRequest.setPassword("password");
        diaryServices.login(loginRequest);
        CreateEntryRequest entryRequest = new CreateEntryRequest();
        entryRequest.setTitle("");
        entryRequest.setBody("");
        entryRequest.setAuthor("");
        assertThrows(InvalidInputException.class, ()->diaryServices.createEntry(entryRequest));
    }
    @Test
    public void userCreatesTwoEntriesTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("username");
        registerRequest.setPassword("password");
        diaryServices.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("username");
        loginRequest.setPassword("password");
        diaryServices.login(loginRequest);

        CreateEntryRequest entryRequest = new CreateEntryRequest();
        entryRequest.setTitle("title");
        entryRequest.setBody("body");
        entryRequest.setAuthor("username");
        diaryServices.createEntry(entryRequest);

        CreateEntryRequest entryRequest2 = new CreateEntryRequest();
        entryRequest2.setTitle("second title");
        entryRequest2.setBody("second body");
        entryRequest2.setAuthor("username");
        diaryServices.createEntry(entryRequest2);
        assertEquals(2, entryServices.findAllEntriesBy(entryRequest.getAuthor()).size());
    }



}