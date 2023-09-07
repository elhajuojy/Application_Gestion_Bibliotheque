package ma.yc.sas.mapper.impl;

import ma.yc.sas.core.Print;
import ma.yc.sas.dao.BookDao;
import ma.yc.sas.dao.BookExampleDao;
import ma.yc.sas.dao.CrudDao;
import ma.yc.sas.dao.impl.BookExampleDaoImpl;
import ma.yc.sas.dao.impl.MemberDoa;
import ma.yc.sas.mapper.Mapper;
import ma.yc.sas.model.BookExample;
import ma.yc.sas.model.Member;
import ma.yc.sas.model.Emprunt;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpruntMapperImpl implements Mapper<Emprunt> {
    private CrudDao<BookExample> bookExampleCrudDao = new BookExampleDaoImpl();
    private BookExampleDao bookExampleDao = new BookExampleDaoImpl();
    private CrudDao<Member> memberCrudDao = new MemberDoa();

    public EmpruntMapperImpl() throws SQLException {
    }

    @Override
    public Emprunt toClassObject(ResultSet resultSet) {

        try {
            Emprunt emprunt = new Emprunt();

            emprunt.setMember(this.memberCrudDao.get(resultSet.getInt("NUMERO_MEMBRE")).get());
            List<BookExample> bookExamples = new ArrayList<>();
            bookExamples = this.bookExampleDao.findAllBooksExampleByIsbn(resultSet.getLong("ISBN"));
            emprunt.setBookExamples(bookExamples);
            emprunt.setDateEmprunt(resultSet.getDate("DATE_EMPRUNT"));
            emprunt.setDateRoutour(resultSet.getDate("DATE_ROUTOUR"));
            return emprunt;
        }catch (Exception e){
            Print.log(e.toString());
        }
        return null;
    }

    @Override
    public PreparedStatement PreparedStatement(Emprunt emprunt, PreparedStatement preparedStatement) {
        try {
            int bookExampleid = Math.toIntExact(emprunt.getBookExamples().get(0).getId());
            preparedStatement.setLong(1, emprunt.getMember().getNumero_membre());
            preparedStatement.setInt(2, bookExampleid);
            preparedStatement.setDate(3, (Date) emprunt.getDateEmprunt());
            preparedStatement.setDate(4, (Date) emprunt.getDateRoutour());

        }catch (Exception e){
            Print.log(e.toString());
        }

        return preparedStatement;
    }
}
