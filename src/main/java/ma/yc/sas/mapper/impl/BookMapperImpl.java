package ma.yc.sas.mapper.impl;

import ma.yc.sas.core.Print;
import ma.yc.sas.mapper.Mapper;
import ma.yc.sas.model.Book;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookMapperImpl implements Mapper<Book> {
    @Override
    public Book toClassObject(ResultSet resultSet) {

       try {
             Book book = new Book();
           book.setISBN(resultSet.getLong("ISBN"));
           book.setQuantite(resultSet.getInt("QUANTITE"));
           book.setTitre(resultSet.getString("TITRE"));
           book.setAuthor(resultSet.getString("AUTHOR"));
           return book;
       }catch (Exception e){
           Print.log(e.toString());
       }
        return null;
    }

    @Override
    public PreparedStatement PreparedStatement(Book book, PreparedStatement preparedStatement) {
        try {
            preparedStatement.setLong(1, book.getISBN());
            preparedStatement.setInt(2, book.getQuantite());
            preparedStatement.setString(3, book.getTitre());
            preparedStatement.setString(4, book.getAuthor());

        }catch (Exception e){
            Print.log(e.toString());
        }

        return preparedStatement;
    }


}
