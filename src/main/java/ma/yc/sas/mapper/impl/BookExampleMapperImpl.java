package ma.yc.sas.mapper.impl;

import ma.yc.sas.enums.Availability;
import ma.yc.sas.core.Print;
import ma.yc.sas.dao.CrudDao;
import ma.yc.sas.dao.impl.BookDaoImpl;
import ma.yc.sas.mapper.Mapper;
import ma.yc.sas.model.Book;
import ma.yc.sas.model.BookExample;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class BookExampleMapperImpl implements Mapper<BookExample> {


    @Override
    public BookExample toClassObject(ResultSet resultSet) {
        try {
            CrudDao<Book> bookCrudDao = new BookDaoImpl();
            BookExample BookExample = new BookExample();
            BookExample.setId(resultSet.getLong("ID"));
            Availability availability = Availability.valueOf(resultSet.getString("AVAILABILITY"));
            BookExample.setAvailability(availability);
            Long isbn = resultSet.getLong("ISBN");
            Optional<Book> book = bookCrudDao.get(isbn);
            if (book.isPresent()){
                BookExample.setBook(book.get());
            }
            return BookExample;
        }catch (Exception e){
            Print.log(e.toString());
        }
        return null;
    }

    @Override
    public PreparedStatement PreparedStatement(BookExample bookExample, PreparedStatement preparedStatement) {
        try {
            preparedStatement.setLong(1, bookExample.getBook().getISBN());
            String status = bookExample.getAvailability().toString();
            preparedStatement.setString(2, status);

        }catch (Exception e){
            Print.log(e.toString());
        }

        return preparedStatement;
    }
}
