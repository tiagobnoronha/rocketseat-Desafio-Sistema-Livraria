package models;

import java.util.ArrayList;
import java.util.List;

public class Library {

    private final List<Book> books = new ArrayList<>();
    private final List<Author> authors = new ArrayList<>();
    private final List<Borrowing> borrowings = new ArrayList<>();

    public void addBook(Book book){
        books.add(book);
    }

    public void addAuthor(Author author){
        authors.add(author);
    }

    public List<Book> getAvailableBooks(){
        return books.stream().filter((book)->book.isAvailable()).toList();
    }

    public List<Author> getAuthors(){
        return new ArrayList<>(authors);
    }

}
