package ma.yc.sas.dao.impl;

import ma.yc.sas.core.Print;
import ma.yc.sas.dao.BookDao;
import ma.yc.sas.dao.BookExampleDao;
import ma.yc.sas.database.DatabaseConnection;
import ma.yc.sas.dao.CrudDao;
import ma.yc.sas.mapper.Mapper;
import ma.yc.sas.mapper.impl.BookMapperImpl;
import ma.yc.sas.model.Book;
import ma.yc.sas.model.BookExample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class BookDaoImpl implements BookDao {
    List<Book> books = new ArrayList<>();
    Connection databaseConnection ;
    Mapper<Book> bookMapper ;
    ResultSet resultSet ;
    BookExampleDao bookExampleDao ;

    public BookDaoImpl() throws SQLException {
        this.databaseConnection = DatabaseConnection.getInstance().getConnection() ;
        this.bookMapper  = new BookMapperImpl() ;
        this.bookExampleDao = new BookExampleDaoImpl();
    }

    @Override
    public Optional<Book> get(long id)  {
        try{
            String QUERY = "SELECT * FROM BOOK WHERE ISBN = ?";
            PreparedStatement statement = databaseConnection.prepareStatement(QUERY);
            statement.setLong(1,id);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                return Optional.of(bookMapper.toClassObject(resultSet));
            }
            resultSet.close();
        }
        catch (SQLException e){
            Print.log(e.toString());
        }
        return Optional.empty();
    }

    @Override
    public List<Book> getAll() {
        String QUERY = "SELECT * FROM BOOK";
        try{
            PreparedStatement statement = databaseConnection.prepareStatement(QUERY);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                Book book = bookMapper.toClassObject(resultSet) ;
                // need to get all book bookExamples ;
                // the thing is i will use the book-example dto to getAll books with this isbn and
                //mappe  list book-examples to to book
                books.add(book);
                //

            }
            return  this.books;

        }catch (SQLException e){
            Print.log(e.toString());
        }
        return null;
    }
    @Override
    public List<Book> getAll(boolean lazyLoading) {
        String QUERY = "SELECT * FROM BOOK";
        try{
            PreparedStatement statement = databaseConnection.prepareStatement(QUERY);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                Book book = bookMapper.toClassObject(resultSet) ;
                // need to get all book bookExamples ;
                // the thing is i will use the book-example dto to getAll books with this isbn and
                //mappe  list book-examples to  book
                if (lazyLoading){
                    List<BookExample> bookExamples = this.bookExampleDao.findAllBooksExampleByIsbn(resultSet.getLong("ISBN"));
                    book.setBookExamples(bookExamples);
                }
                books.add(book);
                //

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
            int deletedRecored = statement.executeUpdate();
            if (deletedRecored == 1){
                return  book;
            }
        }catch (SQLException e){
            Print.log(e.getMessage());
        }
        return null;
    }

    @Override
    public Optional<Book> finBookByAuthor(String authorname) {
         try{
             String QUERY = "SELECT * FROM BOOK WHERE AUTHOR = ?";
             PreparedStatement statement = databaseConnection.prepareStatement(QUERY);
             statement.setString(1,authorname);
             resultSet = statement.executeQuery();
             while (resultSet.next()){
                 return Optional.of(bookMapper.toClassObject(resultSet));
             }
             resultSet.close();
         }catch (SQLException e)
         {
            Print.log(e.toString());
         }

         return Optional.empty();
    }

    @Override
    public Optional<Book> findBookByTitle(String title) {
        try{
            String QUERY = "SELECT * FROM BOOK WHERE TITRE = ?";
            PreparedStatement statement = databaseConnection.prepareStatement(QUERY);
            statement.setString(1,title);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                return Optional.of(bookMapper.toClassObject(resultSet));
            }
            resultSet.close();
        }catch (SQLException e)
        {
            Print.log(e.toString());
        }

        return Optional.empty();
    }




}
