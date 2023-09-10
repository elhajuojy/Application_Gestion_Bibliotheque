package ma.yc.sas.GUI;

import ma.yc.sas.core.Print;
import ma.yc.sas.core.Util;
import ma.yc.sas.dao.CrudDao;
import ma.yc.sas.dao.impl.MemberDoa;
import ma.yc.sas.model.Book;
import ma.yc.sas.model.Member;
import pl.mjaron.etudes.Table;

import java.sql.SQLException;
import java.util.Scanner;

public class MemberUseCase implements UserInterface{


    private CrudDao<Member> memberDao ;
    public MemberUseCase() throws SQLException {
        this.memberDao = new MemberDoa();
    }

    @Override
    public int displayOptions(Scanner scanner) {
        Print.log("\t 1- CREATE MEMBER");
        Print.log("\t 2- UPDATE MEMBER");
        Print.log("\t 3- DELETE MEMBER");
        Print.log("\t 4- GET ALL MEMBER");
        Print.log("\t 5- FIND MEMBER BY NAME");
        Print.log("\t 6- RETURN");
        int choice =  scanner.nextInt();
        switch (choice) {
            case 1 ->
                // CREATE BOOK
                    this.createMember(scanner);
            case 2 ->
                // UPDATE BOOK
                    this.updateMemeber(scanner);
            case 3 ->
                // DELETE BOOK
                    this.deleteMember(scanner);
            case 4 ->
                // GET ALL BOOKS
                    this.getAllMembers(scanner);
            case 5 ->
                // FIND BOOKS BY ISBN
                    this.findMemberByName(scanner);
            case 6 ->
                // RETURN
                    new MainGui().displayOptions(scanner);
        }
        return  0;
    }

    private void findMemberByName(Scanner scanner) {

    }

    private void getAllMembers(Scanner scanner) {
        Print.log("=== All Members ===");
        Table.render(this.memberDao.getAll(), Member.class).run();
        this.displayOptions(scanner);
    }

    private void deleteMember(Scanner scanner) {

    }

    private void updateMemeber(Scanner scanner) {
    }

    private void createMember(Scanner scanner) {
        Print.log("=== CREATE MEMBER OPERATION ====");
        String nom = Util.readString("NOM",scanner);
        String prenom = Util.readString("PRENOM",scanner);

        Member member = new Member();
        member.setNom(nom);
        member.setPrenom(prenom);

        if (memberDao.save(member) != null){
            Print.log("=== the memeber have been add with success ===");
            Print.log(member.toString());
        }
        Print.log("THIS A PROBLEM SAVING THIS MEMBER PLEASE CHECK THE INFORMATION AGAIN");
        this.displayOptions(scanner);
    }
}
