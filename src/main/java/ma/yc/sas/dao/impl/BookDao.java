package ma.yc.sas.dao.impl;

import ma.yc.sas.core.Print;
import ma.yc.sas.database.DatabaseConnection;
import ma.yc.sas.dao.CrudDao;
import ma.yc.sas.mapper.Mapper;
import ma.yc.sas.mapper.impl.BookMapperImpl;
import ma.yc.sas.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class BookDao implements CrudDao<Book> {
    List<Book> books = new ArrayList<>();
    Connection databaseConnection = DatabaseConnection.getInstance().getConnection() ;
    Mapper<Book> bookMapper = new BookMapperImpl() ;
    ResultSet resultSet ;

    public BookDao() throws SQLException {

    }

    @Override
    public Optional<Book> get(long id) throws SQLException {
        String QUERY = "SELECT * FROM BOOK WHERE ISBN = ?";
        PreparedStatement statement = databaseConnection.prepareStatement(QUERY);
        statement.setLong(1,id);
        resultSet = statement.executeQuery();
        while (resultSet.next()){
            return Optional.of(bookMapper.toClassObject(resultSet));
        }
        resultSet.close();
        return Optional.empty();
    }

    @Override
    public List<Book> getAll() {
        String QUERY = "SELECT * FROM BOOK ";
        try{
            PreparedStatement statement = databaseConnection.prepareStatement(QUERY);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                books.add(bookMapper.toClassObject(resultSet));
            }
            return  this.books;

        }catch (SQLException e){
            Print.log(e.toString());
        }
        return null;
    }



    @Override
    public Book save(Book book) {
        String QUERY = "INSERT INTO BOOK ( ISBN , QUANTITE, TITRE, AUTHOR) VALUES (?, ?, ?,?) ;";
        try{
            PreparedStatement statement = bookMapper.PreparedStatement(book,databaseConnection.prepareStatement(QUERY));
            int saveRecored = statement.executeUpdate();
            if (saveRecored == 1){
                return  book;
            }

        }catch (Exception e){
            Print.log(e.toString());
        }
        return  null;
    }

    @Override
    public Book update(Book book, String[] params) {
        if (params == null || params.length % 2 != 0) {
            // Ensure params is not null and contains an even number of elements (key-value pairs)
            throw new IllegalArgumentException("Invalid params array");
        }

        StringBuilder queryBuilder = new StringBuilder("UPDATE BOOK SET ");
        List<Object> values = new ArrayList<>();

        for (int i = 0; i < params.length; i += 2) {
            if (i > 0) {
                queryBuilder.append(", "); // Add comma separator for multiple updates
            }
            String key = params[i];
            String value = params[i + 1];

            queryBuilder.append("`").append(key).append("` = ?");
            values.add(value);
        }

        queryBuilder.append(" WHERE `ISBN` = ?");
        values.add(book.getISBN());

        try{
            PreparedStatement statement = databaseConnection.prepareStatement(queryBuilder.toString());
            for (int i = 0; i < values.size(); i++) {
                statement.setObject(i + 1, values.get(i));
            }
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 1){
                return book;
            }
        }catch (SQLException e){
            Print.log(e.toString());
        }

        // You can handle the result and return the updated book object or null if needed.

        return null;

    }

    @Override
    public Book delete(Book book) {
        String QUERY = "DELETE FROM BOOK WHERE ISBN = ?";
        try{
            PreparedStatement statement = databaseConnection.prepareStatement(QUERY);
            statement.setLong(1,book.getISBN());
            statement.executeUpdate();
        }catch (SQLException e){
            Print.log(e.getMessage());
        }
        return null;
    }
}
