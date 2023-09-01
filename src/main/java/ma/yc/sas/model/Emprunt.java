package ma.yc.sas.model;

import java.util.Date;
import java.util.List;

public class Emprunt {
    private Long id;
    private Member member;
    private Date dateEmprunt;
    private Date dateRoutour;
    private List<Book> books;



    public Emprunt() {
    }

    public Emprunt(Long id, Member member, List<Book> books) {
        this.id = id;
        this.member = member;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public  Emprunt add(){return  null ;};
    public  Book delete(){return  null ;};
    public  Book update(){return  null ;};
    public  Book modify(){return  null ;};
    public List<Book> getAll(){return  null;};
    public List<Emprunt> searchByIsbn(Long  isbn){return  null;}

}
