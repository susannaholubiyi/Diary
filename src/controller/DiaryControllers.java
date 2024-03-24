package controller;

import dtos.request.CreateEntryRequest;
import dtos.request.LoginRequest;
import dtos.request.RegisterRequest;
import service.DiaryServices;
import service.DiaryServicesImplements;

public class DiaryControllers {
    private DiaryServices diaryServices = new DiaryServicesImplements();

    public String signUpUser(RegisterRequest registerRequest){
        try {
            diaryServices.register(registerRequest);
            return String.format("%s, you have successfully signed in", registerRequest.getUserName());
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
    public String signInUser(LoginRequest loginRequest){
        try {
            diaryServices.login(loginRequest);
            return String.format("%s, you have successfully signed in", loginRequest.getUserName());
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
    public String createEntry(CreateEntryRequest createEntryRequest){
        try {
            diaryServices.createEntry(createEntryRequest);
            return String.format("%s, your entry has successfully  been created", createEntryRequest.getAuthor());
        }
        catch (Exception e){
            return e.getMessage();
        }
    }


}
