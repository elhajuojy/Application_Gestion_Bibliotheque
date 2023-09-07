package ma.yc.sas.dao.impl;

import ma.yc.sas.core.Print;
import ma.yc.sas.dao.CrudDao;
import ma.yc.sas.database.DatabaseConnection;
import ma.yc.sas.mapper.Mapper;
import ma.yc.sas.mapper.impl.BookMapperImpl;
import ma.yc.sas.mapper.impl.MemberMapperImpl;
import ma.yc.sas.model.Book;
import ma.yc.sas.model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemberDoa implements CrudDao<Member> {

    List<Member> members = new ArrayList<>();
    Connection databaseConnection = DatabaseConnection.getInstance().getConnection() ;
    Mapper<Member> memberMapper = new MemberMapperImpl() ;
    ResultSet resultSet ;

    public MemberDoa() throws SQLException {
    }

    @Override
    public Optional<Member> get(long id) {
        String QUERY = "SELECT * FROM MEMBER WHERE NUMERO_MEMBRE = ?";
        try{
            PreparedStatement statement = databaseConnection.prepareStatement(QUERY);
            statement.setInt(1,(int) id);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                return Optional.of(memberMapper.toClassObject(resultSet));
            }
            resultSet.close();
        }catch (Exception e){
            Print.log(e.toString());
        }
        return Optional.empty();
    }

    @Override
    public List<Member> getAll() {
        String QUERY = "SELECT * FROM MEMBER ;";
        try{
            PreparedStatement statement = databaseConnection.prepareStatement(QUERY);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                members.add(memberMapper.toClassObject(resultSet));
            }
            return  this.members;

        }catch (SQLException e){
            Print.log(e.toString());
        }
        return null;
    }

    @Override
    public Member save(Member member) {
        String QUERY = "INSERT INTO MEMBER ( NUMERO_MEMBRE , NOM, PRENOM ) VALUES (?, ?, ?) ;";
        try{
            PreparedStatement statement = memberMapper.PreparedStatement(member,databaseConnection.prepareStatement(QUERY));
            int saveRecored = statement.executeUpdate();
            if (saveRecored == 1){
                return  member;
            }

        }catch (Exception e){
            Print.log(e.toString());
        }
        return  null;
    }

    @Override
    public Member update(Member member, String[] params) {
        return null;
    }

    @Override
    public Member delete(Member member) {
        String QUERY = "DELETE FROM MEMEBER WHERE NUMERO_MEMBRE = ?";
        try{
            PreparedStatement statement = databaseConnection.prepareStatement(QUERY);
            statement.setLong(1,member.getNumero_membre());
            int deletedRecored = statement.executeUpdate();
            if (deletedRecored == 1){
                return  member;
            }
        }catch (SQLException e){
            Print.log(e.getMessage());
        }
        return null;
    }
}
