package dtos.request;

public class RegisterRequest {


    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;
    private String userName;

    public RegisterRequest(){

    }




}
