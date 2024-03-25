package africa.semicolon.DiaryThatRemembers.dtos.request;

import lombok.Data;

@Data
public class UpdateEntryRequest {


    private String title;
    private String body;
    private String author;
    private String id;

}
