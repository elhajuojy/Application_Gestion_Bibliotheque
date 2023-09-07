package ma.yc.sas.GUI;

import ma.yc.sas.core.Print;

import java.util.Scanner;

public class StatistiqueUseCase implements UserInterface {

    @Override
    public int displayOptions(Scanner scanner) {
        Print.log("le nombre de livres disponibles");
        Print.log("le nombre de livres empruntés");
        Print.log("le nombre de livres perdus");
        Print.log("récupérer tous les livres avec le statut \"emprunté\"");
        Print.log("affiche le rapport ");

        return 0;
    }
}
