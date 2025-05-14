package models;

import java.time.LocalDate;

public class Author {
    private final Long id;
    private String name;
    private LocalDate dateOfBirth;

    public Author(Long id, String name, LocalDate dateOfBirth){
        this.id = id;
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
