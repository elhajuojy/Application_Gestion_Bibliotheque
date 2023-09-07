package ma.yc.sas.dao.impl;

import ma.yc.sas.core.Print;
import ma.yc.sas.dao.CrudDao;
import ma.yc.sas.database.DatabaseConnection;
import ma.yc.sas.mapper.Mapper;
import ma.yc.sas.mapper.impl.BookMapperImpl;
import ma.yc.sas.mapper.impl.EmpruntMapperImpl;
import ma.yc.sas.model.Book;
import ma.yc.sas.model.Emprunt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmpruntDao implements CrudDao<Emprunt> {
    List<Emprunt> emprunts = new ArrayList<>();
    Connection databaseConnection = DatabaseConnection.getInstance().getConnection() ;
    Mapper<Emprunt> empruntMapper = new EmpruntMapperImpl() ;
    ResultSet resultSet ;

    public EmpruntDao() throws SQLException {
    }

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
        String QUERY = "INSERT INTO EMPRUNT ( NUMERO_MEMBRE , BOOK_EXAMPLE , DATE_EMPRUNT , DATE_ROUTOUR ) VALUES (?, ?, ?,?) ;";
        try{
            PreparedStatement statement = empruntMapper.PreparedStatement(emprunt,databaseConnection.prepareStatement(QUERY));
            int saveRecored = statement.executeUpdate();
            if (saveRecored == 1){
                return  emprunt;
            }

        }catch (Exception e){
            Print.log(e.toString());
        }
        return  null;
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
