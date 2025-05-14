package models;

import java.time.LocalDate;

public class Borrowing {

    private static Long lastId = 0L;
    private final Long id;
    private Book book;
    private String user;
    private LocalDate dateBorrow;
    private LocalDate dateGiveBack;

    public Borrowing(Book book, String user){
        id = ++lastId;
        this.book = book;
        this.user = user;
        dateBorrow = LocalDate.now();
        dateGiveBack = null;
        book.borrow();
    }

    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public String getUser() {
        return user;
    }

    public LocalDate getDateBorrow() {
        return dateBorrow;
    }

    public LocalDate getDateGiveBack() {
        return dateGiveBack;
    }

    public boolean isActive(){
        return  dateGiveBack==null;
    }

    public void giveBack(){
        dateGiveBack = LocalDate.now();
        book.giveBack();
    }


}
