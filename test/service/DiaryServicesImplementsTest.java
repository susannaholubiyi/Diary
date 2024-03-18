package service;

import dtos.request.LoginRequest;
import dtos.request.RegisterRequest;
import org.junit.jupiter.api.Test;
import service.exceptions.IncorrectPasswordException;
import service.exceptions.NotRegisteredUserException;
import service.exceptions.UserAlreadyExistsException;

import static org.junit.jupiter.api.Assertions.*;

class DiaryServicesImplementsTest {

    private DiaryServices diaryServices = new DiaryServicesImplements();
    @Test
    public void registerUserTest() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("user name");
        registerRequest.setPassword("1111");
        diaryServices.register(registerRequest);
        assertEquals(1L, diaryServices.getNumberOfUsers());
    }
    @Test
    public void registerTwoUserNameTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("user name");
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
        registerRequest.setUserName("user name");
        registerRequest.setPassword("1111");
        diaryServices.register(registerRequest);

        RegisterRequest registerRequest2 = new RegisterRequest();
        registerRequest2.setUserName("user name");
        registerRequest2.setPassword("0000");
        assertThrows(UserAlreadyExistsException.class, ()->diaryServices.register(registerRequest2));
    }
    @Test
    public void registerOneUser_userCanLoginTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("user name");
        registerRequest.setPassword("password");
        diaryServices.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("user name");
        loginRequest.setPassword("password");
        diaryServices.login(loginRequest);

        assertTrue(diaryServices.isLoggedIn());
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
        registerRequest.setUserName("user name");
        registerRequest.setPassword("password");
        diaryServices.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("user name");
        loginRequest.setPassword("different password");
        assertThrows(IncorrectPasswordException.class,()->diaryServices.login(loginRequest));
    }
    @Test
    public void registerUser_userLogin_userLogoutTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("user name");
        registerRequest.setPassword("password");
        diaryServices.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("user name");
        loginRequest.setPassword("password");
        diaryServices.login(loginRequest);

        assertTrue(diaryServices.isLoggedIn());

    }


}