package ma.yc.sas;

import ma.yc.sas.core.Print;
import ma.yc.sas.core.QueryBuilder;
import ma.yc.sas.database.Mysql_JDbC;
import ma.yc.sas.model.Book;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Application {
    public static void main(String[] args) {

        System.out.println("Library Application ");
        try {
            Collection<Book> books = new ArrayList<>();
            Book book = new Book();
            Mysql_JDbC mysqlJDbC = new Mysql_JDbC("jdbc:mysql://127.0.0.1:3306/library", "user", "password");
            String query = QueryBuilder.select("BOOK","*");
            Print.log(query);
            mysqlJDbC.query(query);
            while (mysqlJDbC.resultSet.next()){
                book.toObject(mysqlJDbC.resultSet);
                books.add(book);

            }
        }catch (SQLException e){
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());

        }catch(Exception e){
           Print.log("simple Catch");
           Print.log(e.toString());
        }
        finally {

            Print.log("==========================");
            Print.log("Finally");
        }
    }
}