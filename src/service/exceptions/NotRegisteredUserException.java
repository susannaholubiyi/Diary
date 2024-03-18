package service.exceptions;

public class NotRegisteredUserException extends RuntimeException{
    public NotRegisteredUserException(String message){
        super(message);
    }
}
