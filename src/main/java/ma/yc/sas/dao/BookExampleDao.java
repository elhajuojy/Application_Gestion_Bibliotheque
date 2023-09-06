package ma.yc.sas.dao;

import ma.yc.sas.Enums.Availability;
import ma.yc.sas.model.BookExample;

import java.util.List;

public interface BookExampleDao {
    List<BookExample> findAvailableeBooks(Availability availability);
    List<BookExample> findAllBooksExampleByIsbn(long isbn);
    BookExample updateBookExampleStatus(BookExample bookExample, Availability availability);

}
