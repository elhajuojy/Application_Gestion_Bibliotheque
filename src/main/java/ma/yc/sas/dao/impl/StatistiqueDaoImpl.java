package ma.yc.sas.dao.impl;

import ma.yc.sas.core.Print;
import ma.yc.sas.dao.CrudDao;
import ma.yc.sas.dao.StatistiqueDao;
import ma.yc.sas.database.DatabaseConnection;
import ma.yc.sas.enums.Availability;
import ma.yc.sas.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class StatistiqueDaoImpl implements StatistiqueDao {

    private CrudDao<Book> bookDao ;
    private List<Book> books = new ArrayList<>();
    private Connection connection ;

    public StatistiqueDaoImpl() throws SQLException {
        this.bookDao = new BookDaoImpl();
        this.books = this.bookDao.getAll();
        this.connection = DatabaseConnection.getInstance().getConnection();
//        Table.render(books,Book.class).run();
    }

    public  int countAllBooksFromList(boolean countAll,Availability... availability ){
        try{
            String query = "SELECT COUNT(*) from BOOK_EXAMPLE";
            if (!countAll){

                query = query + " WHERE AVAILABILITY= ?";
                PreparedStatement preparedStatement1 = this.connection.prepareStatement(query);;
                String availabilityString = availability[0].toString();
                preparedStatement1.setString(1, availabilityString);
                ResultSet resultSet = preparedStatement1.executeQuery();
                if (resultSet.next()) {
                    int countResult = resultSet.getInt(1); // Get the count from the first column
                    return countResult;
                }

            }

            PreparedStatement preparedStatement = this.connection.prepareStatement(query);;
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int countResult = resultSet.getInt(1); // Get the count from the first column
                return countResult;
            }





        }catch (SQLException e){
            Print.log(e.toString());
        }

        return 0;
    }

    @Override
    public void generateRapport() {

    }

    @Override
    public int countAllBooks() {
        return this.countAllBooksFromList(true);
    }

    @Override
    public int countAvailableBooks() {
        return this.countAllBooksFromList(false,Availability.AVAILABLE);
    }

    @Override
    public int countNotAvailableBooks() {
        return this.countAllBooksFromList(false,Availability.NOT_AVAILABLE);
    }

    @Override
    public int countLostBooks() {
        return this.countAllBooksFromList(false,Availability.LOST);
    }
}
