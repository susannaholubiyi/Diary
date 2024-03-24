package controllers;


import dtos.request.*;
import exceptions.exceptions.NoEntryException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.DiaryServices;
import service.DiaryServicesImplements;

import static org.junit.jupiter.api.Assertions.*;

public class DiaryControllersTest {
    private DiaryServices diaryServices;
    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private CreateEntryRequest createEntryRequest;
    private UpdateEntryRequest updateEntryRequest;
    private DeleteEntryRequest deleteEntryRequest;
    @BeforeEach
    public void setUp(){
        diaryServices = new DiaryServicesImplements();
        registerRequest = new RegisterRequest();
        loginRequest = new LoginRequest();
        createEntryRequest = new CreateEntryRequest();
        updateEntryRequest = new UpdateEntryRequest();
        deleteEntryRequest = new DeleteEntryRequest();
    }
    @AfterEach
    public void tearDown(){
        diaryServices.clear();
    }
    @Test
    public void userCanCreateEntryTest(){
        diaryServices.clear();
        registerRequest.setUserName("susu");
        registerRequest.setPassword("1111");
        diaryServices.register(registerRequest);
        assertEquals(1, diaryServices.getNumberOfUsers());

        loginRequest.setUserName("susu");
        loginRequest.setPassword("1111");
        diaryServices.login(loginRequest);
        assertFalse(diaryServices.findBy("susu").isLocked());

        createEntryRequest.setTitle("title");
        createEntryRequest.setBody("body");
        createEntryRequest.setAuthor("susu");
        diaryServices.createEntry(createEntryRequest);
        assertEquals(1, diaryServices.findAllEntriesBy("susu").get(0).getId());
        diaryServices.clear();
    }
    @Test
    public void userCanSignUpTest(){
        registerRequest.setUserName("susu");
        registerRequest.setPassword("1111");
        diaryServices.register(registerRequest);
        assertEquals(1, diaryServices.getNumberOfUsers());
    }
    @Test
    public void userCanSignInTest(){
        registerRequest.setUserName("susu");
        registerRequest.setPassword("1111");
        diaryServices.register(registerRequest);
        assertEquals(1, diaryServices.getNumberOfUsers());

        loginRequest.setUserName("susu");
        loginRequest.setPassword("1111");
        diaryServices.login(loginRequest);
        assertFalse(diaryServices.findBy("susu").isLocked());
    }

    @Test
    public void userCanUpdateEntryTest(){
        diaryServices.clear();
        registerRequest.setUserName("susu");
        registerRequest.setPassword("1111");
        diaryServices.register(registerRequest);
        assertEquals(1, diaryServices.getNumberOfUsers());

        loginRequest.setUserName("susu");
        loginRequest.setPassword("1111");
        diaryServices.login(loginRequest);
        assertFalse(diaryServices.findBy("susu").isLocked());

        createEntryRequest.setTitle("title");
        createEntryRequest.setBody("body");
        createEntryRequest.setAuthor("susu");
        diaryServices.createEntry(createEntryRequest);
        assertEquals(1, diaryServices.findAllEntriesBy("susu").get(0).getId());

        updateEntryRequest.setAuthor("susu");
        updateEntryRequest.setId(1);
        updateEntryRequest.setTitle("new title");
        updateEntryRequest.setBody("new body");
        diaryServices.updateEntry(updateEntryRequest);
        assertEquals("new title", diaryServices.findBy(1).getTitle());
        assertEquals("new body", diaryServices.findBy(1).getBody());
        diaryServices.clear();
    }
    @Test
    public void userCanDeleteEntryTest(){
        registerRequest.setUserName("susu");
        registerRequest.setPassword("1111");
        diaryServices.register(registerRequest);
        assertEquals(1, diaryServices.getNumberOfUsers());

        loginRequest.setUserName("susu");
        loginRequest.setPassword("1111");
        diaryServices.login(loginRequest);
        assertFalse(diaryServices.findBy("susu").isLocked());

        createEntryRequest.setTitle("title");
        createEntryRequest.setBody("body");
        createEntryRequest.setAuthor("susu");
        diaryServices.createEntry(createEntryRequest);
        assertEquals(1, diaryServices.findAllEntriesBy("susu").get(0).getId());

        updateEntryRequest.setAuthor("susu");
        updateEntryRequest.setId(1);
        updateEntryRequest.setTitle("new title");
        updateEntryRequest.setBody("new body");
        diaryServices.updateEntry(updateEntryRequest);
        assertEquals("new title", diaryServices.findBy(1).getTitle());
        assertEquals("new body", diaryServices.findBy(1).getBody());

        deleteEntryRequest.setAuthor("susu");
        deleteEntryRequest.setId(1);
        diaryServices.deleteEntry(deleteEntryRequest);
        assertThrows(NoEntryException.class,()-> diaryServices.findAllEntriesBy("susu"));
    }



}
