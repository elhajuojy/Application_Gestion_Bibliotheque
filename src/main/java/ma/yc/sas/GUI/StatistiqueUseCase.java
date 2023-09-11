package ma.yc.sas.GUI;

import ma.yc.sas.Application;
import ma.yc.sas.core.Print;
import ma.yc.sas.dao.StatistiqueDao;
import ma.yc.sas.dao.impl.StatistiqueDaoImpl;

import java.sql.SQLException;
import java.util.Scanner;

public class StatistiqueUseCase implements UserInterface {
    private StatistiqueDao statistiqueDao ;

    public StatistiqueUseCase() throws Exception{
        this.statistiqueDao = new StatistiqueDaoImpl();
    }

    @Override
    public int displayOptions(Scanner scanner) {
        Print.log("1- le nombre de livres disponibles");
        Print.log("2- le nombre de livres empruntés");
        Print.log("3- le nombre total de livres");
        Print.log("4- le nombre de livres perdus");
        Print.log("5- affiche le rapport ");
        Print.log("6- RETURN");
        int choice =  scanner.nextInt();
        switch (choice) {
            case 1 ->
                // CREATE BOOK
                    this.nombreLivreDispo(scanner);
            case 2 ->
                // UPDATE BOOK
                    this.nomberLivreEmprunt(scanner);
            case 3 ->
                // DELETE BOOK
                    this.nomberTotalDeLivre(scanner);
            case 4 ->
                // GET ALL BOOKS
                    this.nomverLivrePredu(scanner);
            case 5 ->
                // FIND BOOKS BY ISBN
                    this.GenerateRapportWithAllStatistque(scanner);
            case 6 ->{
                // RETURN
                try{
                    Application.main(null);
                }catch (SQLException e){
                    Print.log(e.toString());
                }
            }

        }
        return 0;
    }

    private void GenerateRapportWithAllStatistque(Scanner scanner) {
        //generate rapport
        this.statistiqueDao.generateRapport();
        Print.log("DONE ");
        this.displayOptions(scanner);
    }
    private void nomberTotalDeLivre(Scanner scanner) {
        Print.log("=== NOMBER TOTOAL DE LIVRE ===");
        Print.log(this.statistiqueDao.countAllBooks());
        this.displayOptions(scanner);
    }

    private void nomverLivrePredu(Scanner scanner) {
        Print.log("=== NOMBER TOTOAL DE LIVRE PRREDU ===");
        Print.log(this.statistiqueDao.countLostBooks());
        this.displayOptions(scanner);
    }

    private void nomberLivreEmprunt(Scanner scanner) {
        Print.log("=== NOMBER TOTOAL DE LIVRE  EMPRUNT ===");
        Print.log(this.statistiqueDao.countNotAvailableBooks());
        this.displayOptions(scanner);
    }

    private void nombreLivreDispo(Scanner scanner) {
        Print.log("=== NOMBER TOTOAL DE LIVRE DISPONIABLE ===");
        Print.log(this.statistiqueDao.countAvailableBooks());
        this.displayOptions(scanner);
    }

}
