package data.model;

public class Diary {


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void unlock() {
        isLocked = false;
    }
    public boolean lock() {
        return isLocked;
    }

    public Diary(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
    public Diary(){

    }
    private String password;
    private String userName;
    private boolean isLocked = true;


}
