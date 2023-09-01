package ma.yc.sas.model;

import ma.yc.sas.Enums.Availability;
import ma.yc.sas.mapper.DatabaseObjectMapper;
import ma.yc.sas.repository.Entity;

import java.sql.ResultSet;
import java.util.List;

public class Book implements DatabaseObjectMapper , Entity {

    private Long id ;
    private String titre ;
    private Availability avilibality;
    private Long ISBN;
    private String author;


    public Book() {
    }

    public Book(Long id, String titre, Availability avilibality, Long ISBN, String author) {
        this.id = id;
        this.titre = titre;
        this.avilibality = avilibality;
        this.ISBN = ISBN;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Availability getAvilibality() {
        return avilibality;
    }

    public void setAvilibality(Availability avilibality) {
        this.avilibality = avilibality;
    }

    public Long getISBN() {
        return ISBN;
    }

    public void setISBN(Long ISBN) {
        this.ISBN = ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    @Override
    public Object toObject(ResultSet resultSet) throws Exception{
        this.setAuthor(resultSet.getString("AUTHOR"));
        this.setId(resultSet.getLong("ID"));
        this.setTitre(resultSet.getString("TITRE"));
        this.setISBN(resultSet.getLong("ISBN"));
        Availability availability = Availability.valueOf(resultSet.getString("AVAILABILITY"));
        this.setAvilibality(availability);

        return this;
    }
    @Override
    public List<Object> toListObject(ResultSet resultSet) {
        return null;
    }

    @Override
    public List<Object> fromListObject() {
        return null;
    }

    @Override
    public String fromObject() {
        return null;
    }

    @Override
    public Object add(Object object) {
        return null;
    }

    @Override
    public Object delete(Object object) {
        return null;
    }

    @Override
    public Object modify(Object object) {
        return null;
    }

    @Override
    public Object searchBy(String searchBy, String value) {
        return null;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", avilibality=" + avilibality +
                ", ISBN=" + ISBN +
                ", author='" + author + '\'' +
                '}';
    }
}
