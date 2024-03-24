package dtos.request;

public class DeleteEntryRequest {
    public String getAuthor() {
        return author;
    }

    private String author;

    public int getId() {
        return id;
    }

    private int id;

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setId(int id) {
        this.id = id;
    }
}
