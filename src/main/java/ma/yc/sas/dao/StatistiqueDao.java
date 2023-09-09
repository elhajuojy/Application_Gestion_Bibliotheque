package ma.yc.sas.dao;

import ma.yc.sas.model.BookExample;

import java.util.List;

public interface StatistiqueDao {

    public void generateRapport();
    public int countAllBooks();
    public int  countAvailableBooks();
    public  int countNotAvailableBooks();
    public int countLostBooks();

}
