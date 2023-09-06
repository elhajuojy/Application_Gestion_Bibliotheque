package ma.yc.sas.GUI;

import ma.yc.sas.core.Print;

import java.util.Scanner;

public class BookExampleUseCase implements UserInterface {
    @Override
    public int displayOptions(Scanner scanner) {
        Print.log("=== OPERATION  ===");
        Print.log("\t\t 1- FIND BOOK BY ISBN");
        Print.log("\t\t 2- FIND BOOK BY TITRE");
        Print.log("\t\t 3- FIND BOOK BY AUTHOR");
        return 0;
    }
}
