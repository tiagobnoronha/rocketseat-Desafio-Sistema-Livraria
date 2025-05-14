package models;

import exceptions.BookNotAvailableException;

import java.util.ArrayList;
import java.util.List;

public class Library {

    private final List<Book> books = new ArrayList<>();
    private final List<Author> authors = new ArrayList<>();
    private final List<Borrowing> borrowings = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public List<Book> getAvailableBooks() {
        return books.stream().filter(Book::isAvailable).toList();
    }

    public List<Author> getAuthors() {
        return new ArrayList<>(authors);
    }

    public List<Borrowing> getAllBorrowings(){
        return new ArrayList<>(borrowings);
    }

    public List<Borrowing> getActiveBorrowings() {
        return borrowings.stream().filter(Borrowing::isActive).toList();
    }

    public void borrow(Book book, String user) throws BookNotAvailableException {
        if (!book.isAvailable()) throw new BookNotAvailableException(book);

        borrowings.add(new Borrowing(book, user));
    }


}
