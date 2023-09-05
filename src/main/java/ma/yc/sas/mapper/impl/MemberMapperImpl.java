package ma.yc.sas.mapper.impl;

import ma.yc.sas.core.Print;
import ma.yc.sas.mapper.Mapper;
import ma.yc.sas.model.Book;
import ma.yc.sas.model.Member;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberMapperImpl implements Mapper<Member> {
    @Override
    public Member toClassObject(ResultSet resultSet) {

       try {
             Member member = new Member();
           member.setNom(resultSet.getString("NOM"));
           member.setPrenom(resultSet.getString("PRENOM"));
           member.setNumero_membre(resultSet.getLong("NUMERO_MEMBRE"));
           return member;
       }catch (Exception e){
           Print.log(e.toString());
       }
        return null;
    }

    @Override
    public PreparedStatement PreparedStatement(Member Member, PreparedStatement preparedStatement) {
        try {
            preparedStatement.setLong(1, Member.getNumero_membre());
            preparedStatement.setString(2, Member.getPrenom());
            preparedStatement.setString(3, Member.getNom());

        }catch (Exception e){
            Print.log(e.toString());
        }

        return preparedStatement;
    }


}
