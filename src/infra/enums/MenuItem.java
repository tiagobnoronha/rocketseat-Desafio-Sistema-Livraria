package infra.enums;

public enum MenuItem {
    NEW_AUTHOR(1, "Add a new Author"),
    NEW_BOOK(2, "Add a new Book"),
    BORROW(3, "Borrow a book"),
    LIST_ACTIVE_BORROWINGS(4, "List Active Borrowings"),
    GIVE_BACK(5, "Give back a book"),
    LIST_ALL_BORROWINGS(6, "List all borrowings"),
    EXIT(0, "Exit");

    public final Integer optionValue;
    public final String label;

    MenuItem(Integer optionValue, String label){
        this.optionValue=optionValue;
        this.label=label;
    }
}
