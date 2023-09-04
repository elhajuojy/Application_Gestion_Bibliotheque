package ma.yc.sas.GUI;

import ma.yc.sas.core.Print;

import java.util.Scanner;

public class MemberUseCase implements UserInterface{
    @Override
    public int displayOptions(Scanner scanner) {
        Print.log("\t 1- CREATE MEMBER");
        Print.log("\t 2- UPDATE MEMBER");
        Print.log("\t 3- DELETE MEMBER");
        Print.log("\t 4- GET ALL MEMBER");
        Print.log("\t 5- FIND MEMBER BY ISBN");
        Print.log("\t 6- RETURN");
        return scanner.nextInt();
    }
}
