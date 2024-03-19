package data.model;

import java.time.LocalDate;

public class Entry {
    private String title;
    private String body;
    private int id;
    private LocalDate dateCreated = LocalDate.now();

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString(){
        return String.format("Time: %s\t\nTitle: %s\t\nBody: %s\t", dateCreated, title, body);
    }
}
