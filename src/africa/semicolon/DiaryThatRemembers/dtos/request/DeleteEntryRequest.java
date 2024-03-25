package africa.semicolon.DiaryThatRemembers.dtos.request;

import lombok.Data;

@Data
public class DeleteEntryRequest {

    private String author;
    private String id;

}