package africa.semicolon.DiaryThatRemembers.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Diary {
    private String password;
    @Id
    private String userName;
    private boolean isLocked = true;

}
