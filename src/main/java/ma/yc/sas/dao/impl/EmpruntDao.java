package ma.yc.sas.dao.impl;

import ma.yc.sas.dao.CrudDao;
import ma.yc.sas.model.Emprunt;

import java.util.List;
import java.util.Optional;

public class EmpruntDao implements CrudDao<Emprunt> {
    @Override
    public Optional<Emprunt> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Emprunt> getAll() {
        return null;
    }

    @Override
    public Emprunt save(Emprunt emprunt) {
        return null;
    }

    @Override
    public Emprunt update(Emprunt emprunt, String[] params) {
        return null;
    }

    @Override
    public Emprunt delete(Emprunt emprunt) {
        return null;
    }
}
