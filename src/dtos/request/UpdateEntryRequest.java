package dtos.request;

public class UpdateEntryRequest {
    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String title;
    private String body;
    private String author;
    private int id;

}