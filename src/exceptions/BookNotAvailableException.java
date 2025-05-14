package exceptions;

import models.Book;

public class BookNotAvailableException extends RuntimeException {

    private final Book book;
    public BookNotAvailableException(Book book) {
        super(String.format("Book entitled '%s' by %s is not available.", book.getTitle(), book.getAuthor().getName()));
        this.book=book;
    }

    public Book getBook() {
        return book;
    }
}
