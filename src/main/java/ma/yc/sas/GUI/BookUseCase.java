package ma.yc.sas.GUI;

import lombok.*;
import ma.yc.sas.Enums.Availability;
import ma.yc.sas.core.Print;
import ma.yc.sas.core.Util;
import ma.yc.sas.dao.BookDao;
import ma.yc.sas.dao.CrudDao;
import ma.yc.sas.dao.impl.BookDaoImpl;
import ma.yc.sas.dao.impl.BookExampleDaoImpl;
import ma.yc.sas.model.Book;
import ma.yc.sas.model.BookExample;
import pl.mjaron.etudes.Table;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Data
@AllArgsConstructor
@Builder
@ToString
public class BookUseCase implements UserInterface {

    private CrudDao<Book> bookCrud ;
    private BookDao bookDao ;
    private Book book = null;
    private BookExampleDaoImpl bookExampleDao;

    public BookUseCase() throws SQLException {
        this.bookCrud = new BookDaoImpl();
        this.bookDao = new BookDaoImpl();
        this.bookExampleDao = new BookExampleDaoImpl();
    }

    @Override
    public int displayOptions(Scanner scanner) {
        Print.log("\t 1- CREATE BOOK");
        Print.log("\t 2- UPDATE BOOK");
        Print.log("\t 3- UPDATE BOOK STATUS");
        Print.log("\t 4- DELETE BOOK");
        Print.log("\t 5- GET ALL BOOKS");
        Print.log("\t 6- FIND BOOKS BY ISBN , AUTHOR OU TITRE ");
        Print.log("\t 7- SHOW ALL AVAILABLE BOOKS ");
        Print.log("\t 8- FIND LOST BOOKS  ");
        Print.log("\t 9- RETURN");
        int choice =  scanner.nextInt();
        switch (choice) {
            case 1 ->
                // CREATE BOOK
                    this.createBook(scanner);
            case 2 ->
                // UPDATE BOOK
                    this.updateBook(scanner);
            case 3 ->
                    this.showAvailableBooks(scanner,Availability.AVAILABLE);
            case 4 ->
                // DELETE BOOK
                    this.deleteBook(scanner);
            case 5 ->
                // GET ALL BOOKS
                    this.getAllBooks(scanner);
            case 6 ->
                // FIND BOOKS BY ISBN
                    this.search(scanner);
            case 7 ->
                // FIND BOOKS BY ISBN
                    this.findBookByIdISbn(scanner);
            case 8 ->
                // RETURN
                    this.showAvailableBooks(scanner,Availability.LOST);
            case 9 ->
                // RETURN
                    new MainGui().displayOptions(scanner);
        }
        return  0;
    }

    private void findLostBooks(Scanner scanner) {
    }

    private void updateBookStatus(Scanner scanner) {
        //
    }

    private void showAvailableBooks(Scanner scanner , Availability availability) {
        //affiche les livre disponible
        Print.log("=== LIST OF BOOKS  "+ availability.toString()+ " ===");
        Table.render(this.getBookExampleDao().findAvailableeBooks(availability), BookExample.class).run();
    }

    private void search(Scanner scanner) {
        Print.log("=== OPERATION RECHERCHE ===");
        Print.log("\t\t 1- FIND BOOK BY ISBN");
        Print.log("\t\t 2- FIND BOOK BY TITRE");
        Print.log("\t\t 3- FIND BOOK BY AUTHOR");
        Print.log("\t\t 4- RETURN");
        int choice =  scanner.nextInt();
        switch (choice){
            case 1->
                this.findBookByIdISbn(scanner);
            case 2 ->
                this.findBookBytitre(scanner);
            case 3 ->
                this.findyBookByAuthor(scanner);
            default ->
                    this.displayOptions(scanner);
        }


    }

    private void findyBookByAuthor(Scanner scanner) {
        Print.log("=== FIND BOOK BY AUTHOR ");
        String author = Util.readString("AUTHOR ",scanner);
        Optional<Book> bookOptional =  bookDao.finBookByAuthor(author);
        if (bookOptional.isEmpty()){
            Print.log("THIS BOOK DOESN'T EXIST IN THE DATABASE ");
        }else {
            Table.render(new Book[]{bookOptional.get()},Book.class).run();
            Util.readString("Click Done ",scanner);
            this.displayOptions(scanner);
        }

    }

    private void findBookBytitre(Scanner scanner) {
        Print.log("=== FIND BOOK BY TITRE ");
        String titre = Util.readString("TITRE ",scanner);
        Optional<Book> bookOptional =  bookDao.findBookByTitle(titre);
        if (bookOptional.isEmpty()){
            Print.log("THIS BOOK DOESN'T EXIST IN THE DATABASE ");
        }else {
            Table.render(new Book[]{bookOptional.get()},Book.class).run();
            Util.readString("Click Done ",scanner);
            this.displayOptions(scanner);
        }

    }


    public void createBook(Scanner scanner){
        Print.log("=== CREATE BOOK OPERATION ====");
        String titre = Util.readString("titre",scanner);
        System.out.print("Quantite : ");
        int quantite = scanner.nextInt();
        // Consume the newline character left in the buffer
        scanner.nextLine();
        System.out.print("ISBN : ");
        long ISBN = scanner.nextLong();
        // Consume the newline character left in the buffer
        String author = Util.readString("Author",scanner);
        //create book
        book = new Book();
        book.setISBN(ISBN);
        book.setAuthor(author);
        book.setTitre(titre);
        book.setQuantite(quantite);


        if ( bookCrud.save(book) != null){
            Print.log("=== the book have been add with success ===");
            Print.log(book.toString());
            //Save Books examples with available status as defualt
            BookExample bookExample = new BookExample();
            bookExample.setBook(book);
            bookExample.setAvailability(Availability.AVAILABLE);
            for (int i=0;i<quantite;i++){
                this.bookExampleDao.save(bookExample);
            }
        }
        Print.log("THIS A PROBLEM SAVING THIS BOOK PLEASE CHECK THE INFORMATION AGAIN");
        this.displayOptions(scanner);
    }

    private void findBookByIdISbn(Scanner scanner) {
        Print.log("=== FIND BOOK BY ISBN ");
        scanner.nextLine();
        System.out.print("ISBN : ");
        long ISBN = scanner.nextLong();
        Optional<Book> bookOptional =  bookCrud.get(ISBN);
        if (bookOptional.isEmpty()){
            Print.log("THIS BOOK DOESN'T EXIST IN THE DATABASE ");
        }else {
            Table.render(new Book[]{bookOptional.get()},Book.class).run();
            Util.readString("Click Done ",scanner);
            this.displayOptions(scanner);
        }

    }

    private void getAllBooks(Scanner scanner) {
        Print.log("=== GET ALL BOOKS === ");
        List<Book> books = this.bookCrud.getAll();
        Table.render(books, Book.class).run();
        Util.readString("Click Done ",scanner);
        this.displayOptions(scanner);
    }

    private void deleteBook(Scanner scanner) {
        Print.log("=== DELETE BOOK BY ISBN ===");
        scanner.nextLine();
        System.out.print("ISBN : ");
        long ISBN = scanner.nextLong();
        this.book.setISBN(ISBN);
        this.book = this.bookCrud.delete(this.book);
        if (this.book == null){
            Print.log("CAN NOT DELETE THIS BOOK MAKE SURE THIS BOOK EXISTS OR PROVIDE THE CORRECT ISBN");
        }
        Print.log("THE BOOK HAVE BEEN DELETED SUCCESSFULLY");
    }

    private void updateBook(Scanner scanner) {
        Print.log("=== UPDATE BOOK ===");
        scanner.nextLine();
        System.out.print("ISBN : ");
        long ISBN = scanner.nextLong();
        Optional<Book> bookOptional =  bookCrud.get(ISBN);
        if (bookOptional.isEmpty()){
            Print.log("THIS BOOK DOESN'T EXIST IN THE DATABASE ");
        }else {
           //START ASK ABOUT UPDATE BOOK
            String titre = Util.readString("titre",scanner);
            System.out.print("Quantite : ");
            int quantite = scanner.nextInt(bookOptional.get().getQuantite());
            String author = Util.readString("Author",scanner);
            // CREATE BOOK
            book = new Book();
            book.setISBN(ISBN);
            String[] params = {
                    "TITRE", titre ,
                    "AUTHOR", author,
                    "QUANTITE" , String.valueOf(quantite)
            };
            Book book1 = this.bookCrud.update(book,params);

            if (book1!=null){
                Print.log("THE BOOK HAS BEEN UPDATE SUCCESSFULLY ");
            }else{
                Print.log("THERE'S A PROBLEM UPDATE THIS BOOK CHECK THE INFORMATION OR THE WRITE ISBN ");
            }
            this.displayOptions(scanner);
        }

    }
}
