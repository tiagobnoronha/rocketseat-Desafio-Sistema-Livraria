import models.Author;
import models.Library;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        Integer option;
        do {
            Library library = new Library();
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

    private static void newBook(Library library) {

    }

    private static void borrow(Library library) {
        System.out.println("BORROW");
    }

    private static void giveBack(Library library) {
        System.out.println("GIVE BACK");
    }
}