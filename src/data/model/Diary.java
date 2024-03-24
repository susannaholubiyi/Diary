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
    public void setLock(boolean locked){
        isLocked = locked;
    }
    public boolean getLock(){
        return isLocked;
    }
    public boolean isLocked(){
        return isLocked;
    }



    public Diary(){

    }
    private String password;
    private String userName;
    private boolean isLocked = true;




}
