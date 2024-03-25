package africa.semicolon.DiaryThatRemembers.dtos.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String password;
    private String userName;
}
