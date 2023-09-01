package ma.yc.sas.model;

import java.util.List;

public class Member {

    private long numero_membre;
    private String nom ;
    private String prenom;

    public Member(long numero_membre, String nom, String prenom) {
        this.numero_membre = numero_membre;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Member() {
    }

    public long getNumero_membre() {
        return numero_membre;
    }

    public void setNumero_membre(long numero_membre) {
        this.numero_membre = numero_membre;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public  Member add(){return  null ;};
}
