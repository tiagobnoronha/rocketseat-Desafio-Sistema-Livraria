import models.Author;
import models.Book;
import models.Borrowing;
import models.Library;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import infra.enums.MenuItem;

public class Main {

    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        MenuItem option;
        Library library = new Library();
        do {
            option = mainMenu();


            switch (option) {
                case NEW_AUTHOR -> newAuthor(library);
                case NEW_BOOK -> newBook(library);
                case BORROW -> borrow(library);
                case LIST_ACTIVE_BORROWINGS -> listActiveBorrows(library);
                case GIVE_BACK -> giveBack(library);
                case LIST_ALL_BORROWINGS -> listAllBorrowings(library);
                case EXIT -> exit();
                case null -> System.err.println("Invalid option! Try again.");
            }


        } while (option != MenuItem.EXIT);
    }

    private static MenuItem mainMenu() {
        pause(3000);
        System.out.println("""
                ==================
                =   Main Menu    =
                ==================
                """);

        for (MenuItem item : MenuItem.values()) {
            System.out.printf("%d) %s%n", item.optionValue, item.label);
        }

        System.out.print("\nSelect an option:");

        Integer option = null;

        do {
            if (option != null) {
                System.err.println("Invalid option! Select an option of main menu.");
            }
            option = Integer.valueOf(scan.nextLine());

        } while (option < 0 || option > MenuItem.values().length);

        for (MenuItem item : MenuItem.values()) {
            if (item.optionValue.equals(option)) {
                return item;
            }
        }

        return null;
    }

    private static void listActiveBorrows(Library library) {

        List<Borrowing> borrowings = library.getActiveBorrowings();

        if (borrowings.isEmpty()) {
            System.out.println("There is no active borrowing. Come back later!");
            return;
        }
        System.out.println("List of Active Borrowings:\n---");
        System.out.printf("%-5s %-20s %-20s %-20s %-11s%n", "ID", "Title", "Author", "User", "Date");
        borrowings.forEach(borrowing -> System.out.printf(
                        "%-5s %-20s %-20s %-20s %-11s%n",
                        borrowing.getId(),
                        borrowing.getBook().getTitle(),
                        borrowing.getBook().getAuthorName(),
                        borrowing.getUser(),
                        borrowing.getDateBorrow()
                )
        );
    }

    private static void listAllBorrowings(Library library) {

        List<Borrowing> borrowings = library.getAllBorrowings();

        if (borrowings.isEmpty()) {
            System.out.println("There is no borrowing in history. Come back later!");
            return;
        }
        System.out.println("List of All Borrowings:\n---");
        System.out.printf("%-5s %-20s %-20s %-20s %-15s %-15s%n", "ID", "Title", "Author", "User", "Borrowed in", "Given Back in");
        borrowings.forEach(borrowing -> System.out.printf(
                        "%-5s %-20s %-20s %-20s %-15s %-15s%n",
                        borrowing.getId(),
                        borrowing.getBook().getTitle(),
                        borrowing.getBook().getAuthorName(),
                        borrowing.getUser(),
                        borrowing.getDateBorrow(),
                        borrowing.getDateGiveBack() != null ? borrowing.getDateGiveBack() : "Active"
                )
        );
    }


    private static Author newAuthor(Library library) {
        String name;
        LocalDate dateOfBirth;
        Author author;

        System.out.print("Enter author's name: ");
        name = scan.nextLine();

        System.out.print("Enter author's birth (yyyy-mm-dd): ");
        dateOfBirth = LocalDate.parse(scan.nextLine());

        author = new Author(name, dateOfBirth);
        library.addAuthor(author);

        System.out.println("Author has been successfully created!");


        return author;
    }

    private static Author selectAuthor(Library library) {
        List<Author> authors = library.getAuthors();
        Author selectedAuthor = null;
        int tries = 0;

        do {
            long id;
            if (!authors.isEmpty()) {
                System.out.println("List of Authors:\n---");
                System.out.printf("%-5s %-20s %-11s%n", "ID", "Name", "Birth");
                authors.forEach(author -> System.out.printf(
                                "%-5s %-20s %-11s%n",
                                author.getId(),
                                author.getName(),
                                author.getDateOfBirth()
                        )
                );

                if (tries > 0) {
                    System.err.println("Error: Invalid ID! Try again.");
                }

                if (tries++ == 3) break;

                System.out.println("Select an author by ID (0 to create a new author): ");
                id = Long.parseLong(scan.nextLine());
            } else {
                System.err.println("No author cadastred. Create a new author first.");
                id = 0;
            }

            if (id == 0) {
                selectedAuthor = newAuthor(library);
            } else {
                selectedAuthor = authors.stream()
                        .filter(author -> author.getId().equals(id))
                        .findFirst().orElse(null);
            }
        } while (selectedAuthor == null);

        return selectedAuthor;
    }

    private static void newBook(Library library) {
        Author author = selectAuthor(library);

        if (author == null) {
            System.err.println("No author have been selected. Book creation will be aborted.");
            return;
        }

        System.out.print("Enter book's title:");
        String title = scan.nextLine();

        library.addBook(new Book(title, author));
        System.out.println("Book has been successfully created!");

    }

    private static Book selectAvailableBook(Library library) {
        List<Book> books = library.getAvailableBooks();
        Book selectedBook = null;
        int tries = 0;

        do {
            System.out.println("List of Available Book:\n---");
            System.out.printf("%-5s %-20s %-20s %-11s %-11s%n", "ID", "Title", "Author", "Created", "Updated");
            books.forEach(book -> System.out.printf(
                            "%-5s %-20s %-20s %-11s %-11s%n",
                            book.getId(),
                            book.getTitle(),
                            book.getAuthorName(),
                            book.getDateCreation(),
                            book.getDateLastUpdate()
                    )
            );

            if (tries > 0) {
                System.err.println("Error: Invalid ID! Try again.");
            }

            if (tries++ == 3) break;

            System.out.println("Select a book by ID: ");
            long id = Long.parseLong(scan.nextLine());

            selectedBook = books.stream()
                    .filter(book -> book.getId().equals(id))
                    .findFirst().orElse(null);

        } while (selectedBook == null);

        return selectedBook;
    }

    private static void borrow(Library library) {

        if (library.getAvailableBooks().isEmpty()) {
            System.out.println("There is no book available. Come back later!");
            return;
        }

        Book book = selectAvailableBook(library);

        if (book == null) {
            System.err.println("No book have been selected!");
            return;
        }

        System.out.print("Enter user's name: ");
        String user = scan.nextLine();

        library.borrow(book, user);
        System.out.println("Borrowing has been successfully registered!");

    }

    private static Borrowing selectBorrowning(Library library) {
        List<Borrowing> borrowings = library.getActiveBorrowings();
        Borrowing selectedBorrowing = null;
        int tries = 0;

        do {
            listActiveBorrows(library);

            if (tries > 0) {
                System.err.println("Error: Invalid ID! Try again.");
            }

            if (tries++ == 3) break;

            System.out.println("Select a borrowing by ID: ");
            long id = Long.parseLong(scan.nextLine());

            selectedBorrowing = borrowings.stream()
                    .filter(book -> book.getId().equals(id))
                    .findFirst().orElse(null);

        } while (selectedBorrowing == null);

        return selectedBorrowing;

    }

    private static void giveBack(Library library) {

        if (library.getActiveBorrowings().isEmpty()) {
            System.out.println("There is no active borrowing. Come back later!");
            return;
        }

        Borrowing borrowing = selectBorrowning(library);

        if (borrowing == null) {
            System.err.println("No borrowing have been selected. Give back process will be aborted.");
            return;
        }

        borrowing.giveBack();
        System.out.println("Book has been successfully given back.");
    }

    private static void exit() {
        System.out.println("Good bye!");
        pause(5000);
    }


    public static void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}