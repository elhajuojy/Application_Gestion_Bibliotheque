package ma.yc.sas;

import ma.yc.sas.GUI.*;
import ma.yc.sas.core.Print;
import ma.yc.sas.core.Util;
import ma.yc.sas.database.DatabaseConnection;

import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws SQLException {

        try {
            Scanner scanner = new Scanner(System.in);
            MainGui mainGui = new MainGui();
            Print.log("Library Application ");

            int mainChoice;
            do {
                 mainChoice = mainGui.displayOptions(scanner);
                Util.clearScreen();
            }while (mainChoice > 0 && mainChoice < 4);
            scanner.close();
        } catch(Exception e){
            Print.log(e.toString());
        }
        finally {

            Print.log("Finally Done ");
            boolean isClosed = DatabaseConnection.closeConnection();
            Print.log(isClosed);
        }


    }
}