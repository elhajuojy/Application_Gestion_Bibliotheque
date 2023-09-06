package ma.yc.sas;

import ma.yc.sas.GUI.BookUseCase;
import ma.yc.sas.GUI.EmprunatUseCase;
import ma.yc.sas.GUI.MainGui;
import ma.yc.sas.GUI.MemberUseCase;
import ma.yc.sas.core.Print;
import ma.yc.sas.core.Util;
import ma.yc.sas.dao.CrudDao;
import ma.yc.sas.dao.impl.BookDaoImpl;
import ma.yc.sas.dao.impl.BookExampleDaoImpl;
import ma.yc.sas.dao.impl.MemberDoa;
import ma.yc.sas.database.DatabaseConnection;
import ma.yc.sas.model.Book;
import ma.yc.sas.model.BookExample;
import ma.yc.sas.model.Member;
import pl.mjaron.etudes.Table;

import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws SQLException {

        try {
            Scanner scanner = new Scanner(System.in);
            MainGui mainGui = new MainGui();
            Print.log("Library Application ");
            CrudDao<Book> BookDao = new BookDaoImpl();
            Book book = BookDao.get(9781984819194L).get();
//            book.setISBN(387943793474387834L);
//            book.setAuthor("marcus aurelius");
//            book.setTitre("Meditations: Penguin Classics");
//            book.setISBN(6L);
//            book.setQuantite(15);
//            Book book1 = BookDao.delete(book) ;
//            Print.log(book1);

            CrudDao<Member> memberDoa = new MemberDoa();
            CrudDao<BookExample> bookExampleCrudDao = new BookExampleDaoImpl();

//            if( book != null){
//                Table.render(bookExampleCrudDao.getAll(), BookExample.class).run();
//            }
                int mainChoice = 0;

            do {
                mainChoice = mainGui.displayOptions(scanner);
                switch (mainChoice) {
                    case 1 -> {
                        // Book Management
                        int bookChoice = new BookUseCase().displayOptions(scanner);
                    }
                    case 2 -> {
                        // Member Management
                        int MemberChoice = new MemberUseCase().displayOptions(scanner);
                    }
                    case 3 -> {
                        // Loan Management
                        int LoanChoice = new EmprunatUseCase().displayOptions(scanner);
                    }
                }
                Util.clearScreen();
            }while (mainChoice > 0 && mainChoice < 4);

            scanner.close();

        }catch (SQLException e){
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());

        }catch(Exception e){
            Print.log(e.toString());
        }
        finally {

            Print.log("Finally Done ");
           if (DatabaseConnection.getInstance().getConnection()!=null){
               try{
                   DatabaseConnection.getInstance().getConnection().close();
               }catch (SQLException e){
                   Print.log(e.toString());
               }
           }

            boolean isClosed =DatabaseConnection.getInstance().getConnection().isClosed();
            Print.log(isClosed);
        }


    }
}