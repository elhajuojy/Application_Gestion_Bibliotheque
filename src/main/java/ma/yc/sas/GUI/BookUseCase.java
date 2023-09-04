package ma.yc.sas.GUI;

import com.mysql.cj.log.Log;
import lombok.*;
import ma.yc.sas.core.Print;
import ma.yc.sas.core.Util;
import ma.yc.sas.dao.CrudDao;
import ma.yc.sas.dao.impl.BookDao;
import ma.yc.sas.model.Book;
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
    private Book book = null;


    public BookUseCase() throws SQLException {
        this.bookCrud = new BookDao();
    }

    @Override
    public int displayOptions(Scanner scanner) {
        Print.log("\t 1- CREATE BOOK");
        Print.log("\t 2- UPDATE BOOK");
        Print.log("\t 3- DELETE BOOK");
        Print.log("\t 4- GET ALL BOOKS");
        Print.log("\t 5- FIND BOOKS BY ISBN");
        Print.log("\t 6- RETURN");
        int choice =  scanner.nextInt();
        switch (choice){
            case 1:
                // CREATE BOOK
                this.createBook(scanner);
            break;
            case 2 :
                // UPDATE BOOK
                this.updateBook(scanner);
                break;
            case 3 :
                // DELETE BOOK
                this.DeleteBook(scanner);
                break;
            case 4:
                // GET ALL BOOKS
                this.GetAllBooks(scanner);
                break;
            case 5 :
                // FIND BOOKS BY ISBN
                this.findBookByIdISbn(scanner);
                break;
            case 6 :
                // RETURN
                new MainGui().displayOptions(scanner);
                break;
        }
        return  0;
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
        }
        Print.log("THIS A PROBLEM SAVING THIS BOOK PLEASE CHECK THE INFORMATION AGAIN");
        this.displayOptions(scanner);
    }

    private void findBookByIdISbn(Scanner scanner) {
        Print.log("=== FIND BOOK BY ISBN ");
        scanner.nextLine();
        System.out.print("ISBN : ");
        long ISBN = scanner.nextLong();
        try{
            Optional<Book> bookOptional =  bookCrud.get(ISBN);
            if (bookOptional.isEmpty()){
                Print.log("THIS BOOK DOESN'T EXIST IN THE DATABASE ");
            }else {
                Table.render(new Book[]{bookOptional.get()},Book.class).run();
                Util.readString("Click Done ",scanner);
                this.displayOptions(scanner);
            }

        }catch (SQLException e){
            Print.log(e.toString() + " / " + e.getSQLState() + " / " + e.getStackTrace());
        }

    }

    private void GetAllBooks(Scanner scanner) {
        Print.log("=== GET ALL BOOKS === ");
        List<Book> books = this.bookCrud.getAll();
        Table.render(books, Book.class).run();
        Util.readString("Click Done ",scanner);
        this.displayOptions(scanner);
    }

    private void DeleteBook(Scanner scanner) {
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
        try{
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

        }catch (SQLException e){
            Print.log(e.toString() + " / " + e.getSQLState() + " / " + e.getStackTrace());
        }
    }
}
