package ma.yc.sas.dao.impl;

import ma.yc.sas.Enums.Availability;
import ma.yc.sas.core.Print;
import ma.yc.sas.dao.BookExampleDao;
import ma.yc.sas.dao.CrudDao;
import ma.yc.sas.database.DatabaseConnection;
import ma.yc.sas.mapper.Mapper;
import ma.yc.sas.mapper.impl.BookExampleMapperImpl;
import ma.yc.sas.model.BookExample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookExampleDaoImpl implements CrudDao<BookExample> , BookExampleDao {

    List<BookExample> bookExamples  = new ArrayList<>();
    Connection databaseConnection = DatabaseConnection.getInstance().getConnection() ;
    Mapper<BookExample> bookExampleMapper = new BookExampleMapperImpl() ;
    ResultSet resultSet ;

    public BookExampleDaoImpl() throws SQLException {
    }




    @Override
    public Optional<BookExample> get(long id) {
        try{
            String QUERY = "SELECT * FROM BOOK_EXAMPLE WHERE ID = ? ;";
            PreparedStatement statement = databaseConnection.prepareStatement(QUERY);
            statement.setInt(1,(int)id);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                return Optional.of(bookExampleMapper.toClassObject(resultSet));
            }
            resultSet.close();
        }
        catch (SQLException e){
            Print.log(e.toString());
        }
        return Optional.empty();
    }

    @Override
    public List<BookExample> getAll() {
        String QUERY = "SELECT * FROM BOOK_EXAMPLE ; ";
        try{
            PreparedStatement statement = databaseConnection.prepareStatement(QUERY);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                bookExamples.add(bookExampleMapper.toClassObject(resultSet));
            }
            return  this.bookExamples;

        }catch (SQLException e){
            Print.log(e.toString());
        }
        return null;
    }

    @Override
    public BookExample save(BookExample bookExample) {
        String QUERY = "INSERT INTO BOOK_EXAMPLE ( ISBN ,AVAILABILITY ) VALUES (?, ?) ;";
        try{
            PreparedStatement statement = bookExampleMapper.PreparedStatement(bookExample,databaseConnection.prepareStatement(QUERY));
            int saveRecored = statement.executeUpdate();
            if (saveRecored == 1){
                return  bookExample;
            }

        }catch (Exception e){
            Print.log(e.toString());
        }
        return  null;
    }

    @Override
    public BookExample update(BookExample bookExample, String[] params) {
        if (params == null || params.length % 2 != 0) {
            // Ensure params is not null and contains an even number of elements (key-value pairs)
            throw new IllegalArgumentException("Invalid params array");
        }
        StringBuilder queryBuilder = new StringBuilder("UPDATE BOOK_EXAMPLE SET ");
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

        queryBuilder.append(" WHERE `ID` = ?");
        values.add(bookExample.getId());
        try{
            PreparedStatement statement = databaseConnection.prepareStatement(queryBuilder.toString());
            for (int i = 0; i < values.size(); i++) {
                statement.setObject(i + 1, values.get(i));
            }
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 1){
                return bookExample;
            }
        }catch (SQLException e){
            Print.log(e.toString());
        }

        // You can handle the result and return the updated book object or null if needed.

        return null;

    }

    @Override
    public BookExample delete(BookExample bookExample) {
        String QUERY = "DELETE FROM BOOK_EXAMPLE WHERE ID = ?";
        try{
            PreparedStatement statement = databaseConnection.prepareStatement(QUERY);
            statement.setLong(1,bookExample.getId());
            int deletedRecored = statement.executeUpdate();
            if (deletedRecored == 1){
                return  bookExample;
            }
        }catch (SQLException e){
            Print.log(e.getMessage());
        }
        return null;
    }

    @Override
    public List<BookExample> findAvailableeBooks(Availability availability) {
        try{
            String QUERY = "SELECT * FROM BOOK_EXAMPLE WHERE AVAILABILITY = ? ;";
            PreparedStatement statement = databaseConnection.prepareStatement(QUERY);
            String availabilityValue = availability.name();
            statement.setString(1,availabilityValue);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                bookExamples.add(bookExampleMapper.toClassObject(resultSet));
            }
            resultSet.close();
            return  this.bookExamples;
        }
        catch (SQLException e){
            Print.log(e.toString());
        }
        return  null;
    }

    @Override
    public List<BookExample> findAllBooksExampleByIsbn(long isbn) {
        try{
            String QUERY = "SELECT * FROM BOOK_EXAMPLE WHERE ISBN = ? ;";
            PreparedStatement statement = databaseConnection.prepareStatement(QUERY);
            statement.setLong(1,isbn);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                bookExamples.add(bookExampleMapper.toClassObject(resultSet));
            }
            resultSet.close();
            return  this.bookExamples;
        }
        catch (SQLException e){
            Print.log(e.toString());
        }
        return  null;
    }

    @Override
    public BookExample updateBookExampleStatus(BookExample bookExample, Availability availability) {
        String[] params = {"AVAILABILITY", availability.toString()};
        this.update(bookExample,params);
        return null;
    }
}
