package ma.yc.sas.dao;

import ma.yc.sas.model.Book;

import java.util.Optional;

public interface BookDao {

    Optional<Book>finBookByAuthor(String authorname);
    Optional<Book> findBookByTitle(String title);
}
