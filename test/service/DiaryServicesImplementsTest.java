package service;

import dtos.request.EntryRequest;
import dtos.request.LoginRequest;
import dtos.request.RegisterRequest;
import org.junit.jupiter.api.Test;
import exceptions.exceptions.IncorrectPasswordException;
import exceptions.exceptions.NotRegisteredUserException;
import exceptions.exceptions.UserAlreadyExistsException;

import static org.junit.jupiter.api.Assertions.*;

class DiaryServicesImplementsTest {

    private DiaryServices diaryServices = new DiaryServicesImplements();
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
        assertTrue(diaryServices.findUserBy("username").isLocked());
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

        assertTrue(diaryServices.findUserBy("username").isLocked());
        diaryServices.logout("username");
        assertFalse(diaryServices.findUserBy("username").isLocked());
    }
    @Test
    public void registerUserWithSmallerCase_UserCanLoinWithUpperCaseTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("username");
        registerRequest.setPassword("password");
        diaryServices.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("username");
        loginRequest.setPassword("password");
        diaryServices.login(loginRequest);
        assertEquals(1L, diaryServices.getNumberOfUsers());
        assertTrue(diaryServices.findUserBy("Username").isLocked());
    }
    @Test
    public void usernameIsStrippedOfTrailingZeroTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("  username");
        registerRequest.setPassword("password");
        diaryServices.register(registerRequest);
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

        EntryRequest entryRequest = new EntryRequest();
        entryRequest.setTitle("title");
        entryRequest.setBody("body");
        entryRequest.setAuthor("author");
        diaryServices.createEntry(entryRequest);
        assertEquals(1l, diaryServices.getNumberOfEntries());
    }



}