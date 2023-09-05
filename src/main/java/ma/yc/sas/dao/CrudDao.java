package ma.yc.sas.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {
    
    Optional<T> get(long id) ;
    
    List<T> getAll();
    
    T save(T t);
    
    T update(T t, String[] params);
    
    T delete(T t);
}