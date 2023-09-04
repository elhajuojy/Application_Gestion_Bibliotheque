package ma.yc.sas.GUI;

import ma.yc.sas.core.Print;

import java.util.Scanner;

public class EmprunatUseCase implements UserInterface{
    @Override
    public int displayOptions(Scanner scanner) {
        Print.log("\t 1- LOAN BOOK");
        Print.log("\t 2- RETURN BOOK");
        Print.log("\t 3- RETURN");
        return scanner.nextInt();
    }
}
