package ma.yc.sas.GUI;

import ma.yc.sas.core.Print;

import java.util.Scanner;

public class MainGui implements UserInterface{


    @Override
    public int displayOptions(Scanner scanner) {
        Print.log("1 . Book Management");
        Print.log("2 . Member Management");
        Print.log("3 . Loan Management");
        Print.log("4 . Statistiques");
        Print.log("5 . Exit");
        int mainChoice = scanner.nextInt();
        try{
            switch (mainChoice) {
                case 1 ->
                    // Book Management
                        new BookUseCase().displayOptions(scanner);

                case 2 ->
                    // Member Management
                        new MemberUseCase().displayOptions(scanner);

                case 3 ->
                    // Loan Management
                        new EmprunatUseCase().displayOptions(scanner);

                case 4 ->
                        new StatistiqueUseCase().displayOptions(scanner);

                case 5 ->
                    // Exit
                        Print.log("Exit");



            }
        }catch (Exception e){
            Print.log(e.toString());
        }
        return 0;
    }
}
