package project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bookshoping {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Book> books = new ArrayList<>();
        double totalPrice = 0;
        
        // Add books details 
        books.add(new Book("Bhagavad gita", "Maharishi vedvyas", 350));
        books.add(new Book("Ramayana", "Rishi Valmiki", 400));
        books.add(new Book("Mahabharata", "Maharishi vedvyas", 380));
        books.add(new Book("Krishna", "Shubha Vilas", 298));
        books.add(new Book("The KALKI trilogy", "Kevin Missal", 598));

        // Database connection details
        String jdbcUrl = "jdbc:mysql://localhost:3306/book_shoping";
        String username = "root";
        String password = "12345";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM books");

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                double price = resultSet.getDouble("price");
                Book book = new Book(title, author, price);
                books.add(book);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }      
        
        while (true) {
            System.out.println("Welcome to the Bookstore!");
            System.out.println("1. View available books");
            System.out.println("2. Add a book to your cart");
            System.out.println("3. View your cart");
            System.out.println("4. Checkout");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Available Books:");
                    for (int i = 0; i < books.size(); i++) {
                        System.out.println((i + 1) + ". " + books.get(i));
                    }
                    break;
                case 2:
                    System.out.print("Enter the book number to add to your cart: ");
                    int bookNumber = scanner.nextInt();
                    scanner.nextLine();

                    if (bookNumber >= 1 && bookNumber <= books.size()) {
                        Book selectedBook = books.get(bookNumber - 1);
                        totalPrice += selectedBook.getPrice();
                        System.out.println("Added to your cart: " + selectedBook);
                    } else {
                        System.out.println("Invalid book number. Please choose a valid book.");
                    }
                    break;
                case 3:
                    System.out.println("Your Shopping Cart:");
                    for (Book b : books) {
                        System.out.println(b);
                    }
                    System.out.println("Total Price: $" + totalPrice);
                    break;
                case 4:
                    if (books.isEmpty()) {
                        System.out.println("Your cart is empty. Please add books before checking out.");
                    } else {
                        System.out.println("Checking out...");
                        System.out.println("Total Price: $" + totalPrice);
                        System.out.println("Thank you for shopping with us!");
                        System.exit(0);
                    }
                    break;
                case 5:
                    System.out.println("Exiting the Bookstore. Have a great day!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

}
             class Book {
                private String title;
                private String author;
                private double price;

                public Book(String title, String author, double price) {
                this.title = title;
                this.author = author;
                this.price = price;
                }

                public double getPrice() {
                return price;
                }

               @Override
               public String toString() {
               return title + " by " + author + " - $" + price;
               }
          }


