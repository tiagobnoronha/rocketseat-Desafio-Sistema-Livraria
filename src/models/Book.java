package models;

import java.time.LocalDate;

public class Book {

    private static Long lastId = 0L;
    private final Long id;
    private String title;
    private Author author;
    private boolean available;
    private final LocalDate dateCreation;
    private LocalDate dateLastUpdate;

    public Book(String title, Author author) {
        this.id = ++lastId;
        this.title = title;
        this.author = author;
        available = true;
        dateCreation = dateLastUpdate = LocalDate.now();
    }

    public String getAuthorName(){
        return author.getName();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public LocalDate getDateLastUpdate() {
        return dateLastUpdate;
    }

    public void setTitle(String title) {
        this.title = title;
        refreshDateLastUpdate();
    }

    public void setAuthor(Author author) {
        this.author = author;
        refreshDateLastUpdate();
    }

    public void borrow() {
        available = false;
        refreshDateLastUpdate();
    }

    public void giveBack() {
        available = true;
        refreshDateLastUpdate();
    }

    private void refreshDateLastUpdate() {
        dateLastUpdate = LocalDate.now();
    }
}
