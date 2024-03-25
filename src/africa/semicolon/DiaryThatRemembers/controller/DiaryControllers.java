package africa.semicolon.DiaryThatRemembers.controller;

import africa.semicolon.DiaryThatRemembers.dtos.request.CreateEntryRequest;
import africa.semicolon.DiaryThatRemembers.dtos.request.LoginRequest;
import africa.semicolon.DiaryThatRemembers.dtos.request.RegisterRequest;
import africa.semicolon.DiaryThatRemembers.service.DiaryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DiaryControllers {
    @Autowired
    private DiaryServices diaryServices;

    @PostMapping("/sign-up")
    public String signUpUser(@RequestBody RegisterRequest registerRequest){
        try {
            diaryServices.register(registerRequest);
            return String.format("%s, you have successfully signed in", registerRequest.getUserName());
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
    @PatchMapping("/sign-in")
    public String signInUser(@RequestBody LoginRequest loginRequest){
        try {
            diaryServices.login(loginRequest);
            return String.format("%s, you have successfully signed in", loginRequest.getUserName());
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
    @PostMapping("/create-entry")
    public String createEntry(@RequestBody CreateEntryRequest createEntryRequest){
        try {
            diaryServices.createEntry(createEntryRequest);
            return String.format("%s, your entry has successfully  been created", createEntryRequest.getAuthor());
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
    @PatchMapping("/sign-out/{name}")
    public String signOutUser(@PathVariable("name") String username){
        try {
            diaryServices.logout(username);
            return "logout successfull";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    @GetMapping("/find-all-entries/{name}")
    public List<?> getAllEntriesFor(@PathVariable("name") String author) {
        try {
            return diaryServices.findAllEntriesBy(author);
        }
        catch (Exception e) {
            return List.of(e.getMessage());
        }
    }


}
