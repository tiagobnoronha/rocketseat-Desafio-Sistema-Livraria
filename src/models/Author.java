package models;

import java.time.LocalDate;

public class Author {
    private static Long lastId = 0L;
    private final Long id;
    private String name;
    private LocalDate dateOfBirth;

    public Author(String name, LocalDate dateOfBirth){
        this.id = ++lastId;
        this.name = name;
        this.dateOfBirth=dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
