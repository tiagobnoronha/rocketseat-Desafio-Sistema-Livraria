import models.Author;
import models.Book;
import models.Library;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        Integer option;
        Library library = new Library();
        do {
            option = mainMenu();

            switch (option) {
                case 1 -> newAuthor(library);
                case 2 -> newBook(library);
                case 3 -> borrow(library);
                case 4 -> giveBack(library);
            }
        } while (option != 0);
    }

    public static Integer mainMenu() {
        System.out.println("""
                ==================
                =   Main Menu    =
                ==================
                1) Add a new Author
                2) Add a new Book
                3) Borrow a Book
                4) Give back a Book
                0) Exit
                
                Select an option:
                """);

        Integer option = null;


        do {
            if (option != null) {
                System.err.println("Invalid option! Select an option of main menu.");
            }
            option = Integer.valueOf(scan.nextLine());

        } while (option < 0 || option > 4);

        return option;
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

                if(tries++ == 3) break;

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

            if(tries++ == 3) break;

            System.out.println("Select a book by ID: ");
            long id = Long.parseLong(scan.nextLine());

            selectedBook = books.stream()
                    .filter(book -> book.getId().equals(id))
                    .findFirst().orElse(null);

        } while (selectedBook == null);

        return selectedBook;
    }

    private static void borrow(Library library) {
        Book book = selectAvailableBook(library);

        if (book == null) {
            System.err.println("No book have been selected!");
            return;
        }

        System.out.print("Enter user's name: ");
        String user = scan.nextLine();

        library.borrow(book, user);
    }

    private static void giveBack(Library library) {
        System.out.println("GIVE BACK");
    }
}