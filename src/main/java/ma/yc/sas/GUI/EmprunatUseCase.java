package ma.yc.sas.GUI;

import ma.yc.sas.Enums.Availability;
import ma.yc.sas.core.Print;
import ma.yc.sas.dao.BookExampleDao;
import ma.yc.sas.dao.CrudDao;
import ma.yc.sas.dao.impl.BookExampleDaoImpl;
import ma.yc.sas.model.BookExample;
import pl.mjaron.etudes.Table;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class EmprunatUseCase implements UserInterface{

    private Scanner scanner ;
    private BookExampleDao bookExample = new BookExampleDaoImpl();
    private CrudDao<BookExample> bookExampleDaoCrudDao = new BookExampleDaoImpl();

    public EmprunatUseCase() throws SQLException {
    }

    @Override
    public int displayOptions(Scanner scanner) {
        this.scanner = scanner;
        Print.log("\t 1- LOAN BOOK");
        Print.log("\t 2- RETURN BOOK");
        Print.log("\t 3- déclarer Qu'un livre est perdu ");
        Print.log("\t 4- RETURN");
        int choice =  scanner.nextInt();
        switch (choice) {
            case 1 ->
                // CREATE BOOK
                    this.loanBook();
            case 2 ->
                // UPDATE BOOK
                    this.returnBook();
            case 3 ->
                    this.declareBookLost();
            default ->
                // RETURN
                    new MainGui().displayOptions(this.scanner);

        }
        return  0;
    }
    public  void loanBook(){

        //if you want to loan
            // need book
                // CHECK IF THE BOOK IS AVAILABLE
                    // SHOW MESSAGE
                //
            // NEED MEMBER
                // CHECK IF THE MEMBER EXISTS
            // DATE LOAN
                //DATE NOW



    }
    public void returnBook(){

    }

    public void declareBookLost(){
        //if you want to declare book lost
        // first ask for isbn than show all book example and than
        // ask for the book id to make it lost ()

        Print.log("=== DECLARE BOOK AS LOST ===");
        scanner.nextLine();
        System.out.print("ISBN : ");
        long ISBN = scanner.nextLong();
        List<BookExample> bookExamples = this.bookExample.findAllBooksExampleByIsbn(ISBN);
        Table.render(bookExamples,BookExample.class).run();
        //than ask for book id and change the book status to lost
        System.out.println("ENTRE BOOK EXAMPLE ID SO YOU CAN CHANGE IT TO LOST ");
        System.out.print("ID : ");
        long id= scanner.nextLong();
        BookExample bookExample1 = new BookExample();
        String[] params={
                "AVAILABILITY",Availability.LOST.toString()
        };
        bookExample1.setId(id);
        this.bookExampleDaoCrudDao.update(bookExample1,params);
        this.displayOptions(scanner);





    }
}
