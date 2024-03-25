package africa.semicolon.DiaryThatRemembers.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
@Document
@Data
public class Entry {
    private String title;
    private String body;
    @Id
    private String id;
    private String author;
    private LocalDate dateCreated = LocalDate.now();


    @Override
    public String toString(){
        return String.format("Time: %s\t\nTitle: %s\t\nBody: %s\t", dateCreated, title, body);
    }
}
