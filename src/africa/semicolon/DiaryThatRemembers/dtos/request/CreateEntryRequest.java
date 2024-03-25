package africa.semicolon.DiaryThatRemembers.dtos.request;

import lombok.Data;

@Data
public class CreateEntryRequest {
    private String title;
    private String body;
    private String author;
}
