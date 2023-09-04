package ma.yc.sas.GUI;

import ma.yc.sas.core.Print;

import java.util.Scanner;

public class MainGui implements UserInterface{


    @Override
    public int displayOptions(Scanner scanner) {
        Print.log("1 . Book Management");
        Print.log("2 . Member Management");
        Print.log("3 . Loan Management");
        Print.log("4 . Exit");
        return scanner.nextInt();
    }
}
