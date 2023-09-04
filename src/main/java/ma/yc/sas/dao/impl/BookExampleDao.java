package ma.yc.sas.dao.impl;

import ma.yc.sas.dao.CrudDao;
import ma.yc.sas.model.BookExample;

import java.util.List;
import java.util.Optional;

public class BookExampleDao implements CrudDao<BookExample> {
    @Override
    public Optional<BookExample> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<BookExample> getAll() {
        return null;
    }

    @Override
    public BookExample save(BookExample bookExample) {
        return null;
    }

    @Override
    public BookExample update(BookExample bookExample, String[] params) {
        return null;
    }

    @Override
    public BookExample delete(BookExample bookExample) {
        return null;
    }
}
