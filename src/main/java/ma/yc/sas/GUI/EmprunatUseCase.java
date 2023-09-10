package ma.yc.sas.GUI;

import ma.yc.sas.core.Util;
import ma.yc.sas.dao.impl.BookDaoImpl;
import ma.yc.sas.dao.impl.EmpruntDao;
import ma.yc.sas.dao.impl.MemberDoa;
import ma.yc.sas.enums.Availability;
import ma.yc.sas.core.Print;
import ma.yc.sas.dao.BookExampleDao;
import ma.yc.sas.dao.CrudDao;
import ma.yc.sas.dao.impl.BookExampleDaoImpl;
import ma.yc.sas.model.Book;
import ma.yc.sas.model.BookExample;
import ma.yc.sas.model.Emprunt;
import ma.yc.sas.model.Member;
import pl.mjaron.etudes.Table;

import javax.sound.midi.MetaMessage;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

public class EmprunatUseCase implements UserInterface{

    private Scanner scanner ;
    private BookExampleDao bookExample;
    private CrudDao<Book> bookCrudDao ;
    private CrudDao<Member> memberCrudDao ;
    private CrudDao<BookExample> bookExampleDaoCrudDao ;
    private CrudDao<Emprunt> empruntCrudDao ;
    private   List<BookExample> bookExamples = new ArrayList<>();

    public EmprunatUseCase() throws SQLException {
        this.bookExample   = new BookExampleDaoImpl();
        this.bookCrudDao  = new BookDaoImpl();
        this.memberCrudDao = new MemberDoa();
        this.bookExampleDaoCrudDao= new BookExampleDaoImpl();
        this.empruntCrudDao = new EmpruntDao();
    }

    @Override
    public int displayOptions(Scanner scanner) {
        this.scanner = scanner;
        Print.log("\t 1- LOAN BOOK");
        Print.log("\t 2- RETURN BOOK");
        Print.log("\t 3- déclarer Qu'un livre est perdu ");
        Print.log("\t 4- RETURN");
        int choice =  scanner.nextInt();
        switch (choice) {
            case 1 ->
                // CREATE BOOK
                    this.loanBook();
            case 2 ->
                // UPDATE BOOK
                    this.returnBook();
            case 3 ->
                    this.declareBookLost();
            default ->
                // RETURN
                    new MainGui().displayOptions(this.scanner);

        }
        return  0;
    }
    private   void loanBook(){
        Print.log(" === EMPRUNT LIVRE ===");
        //if you want to loan
            // need book
        scanner.nextLine();
        System.out.print("ISBN : ");
        long ISBN = scanner.nextLong();
        Optional<Book> bookOptional =  bookCrudDao.get(ISBN);
        if (bookOptional.isEmpty()){
            Print.log("THIS BOOK DOESN'T EXIST IN THE DATABASE ");
        }else {
            Table.render(new Book[]{bookOptional.get()},Book.class).run();
            //check if book is available first
            //select all books copies where ISBN = ISBN WE PROVIDE
            try{
                BookExampleDao bookExampleDao = new BookExampleDaoImpl();

                if (this.bookExamples.size()==0){
                    this.bookExamples =  bookExampleDao.findAvailableeBooks(ISBN,Availability.AVAILABLE);
                }
                Table.render(bookExamples,BookExample.class).run();
                // check if there's available book
                if (bookExamples.isEmpty()){
                    //if all books are not_available of lost return that the member can't loan the books
                    Print.log("books copies are not available  sorry ");
                    return;
                }
                //else ask for user information and than and only than loan the book
                Print.log("=== PLEASE ENTRE UR MEMBERSHIP ID :");
//                NUMERO_MEMBRE
                int numero_membre = scanner.nextInt();
                Optional<Member> member = this.memberCrudDao.get(numero_membre);
                if (member.isEmpty()){
                    Print.log("THIS MEMBER DOESN'T EXISTS IN DATABASE MAKE SURE TO WRITE THE GOOD ID ");
                    return;
                }

                Print.log(member.toString());
                // now i  have book example and user id
                // insert user id and book id in emprunt table  database
                // return date always should be after 15 jour
                Emprunt emprunt = new Emprunt();
                emprunt.setBookExamples(List.of(bookExamples.get(0)));
                emprunt.setMember(member.get());
                Calendar calendar = Calendar.getInstance();
                // Add 15 days to the current date
                calendar.add(Calendar.DAY_OF_MONTH, 15);
                // Get the new date
                Date newDate = calendar.getTime();
                emprunt.setDateEmprunt(null);
                emprunt.setDateRoutour(null);
                Emprunt emprunt1 = this.empruntCrudDao.save(emprunt);
                this.bookExample.updateBookExampleStatus(bookExamples.get(0),Availability.NOT_AVAILABLE);

                if (emprunt1 !=null){
                    Print.log("THANK U FOR YOUR WAITING ENJOY READING ");
                }


            }catch (SQLException e){
                Print.log(e.toString());
            }

            //now ask for user information

            this.displayOptions(scanner);
        }

    }
    private void returnBook(){
        //9780451524935
        //same as declare book but with update action to available
        this.declareBook(Availability.AVAILABLE);
    }

    private void declareBookLost(){
        this.declareBook(Availability.LOST);
    }

    private void declareBook(Availability availability){
        //if you want to declare book lost
        // first ask for isbn than show all book example and than
        // ask for the book id to make it lost ()
        Print.log("=== DECLARE BOOK AS "+availability.toString()+" ===");
        scanner.nextLine();
        System.out.print("ISBN : ");
        long ISBN = scanner.nextLong();
            if (this.bookExamples.size()==0){
                this.bookExamples  = this.bookExample.findAllBooksExampleByIsbn(ISBN);
            }
        Table.render(bookExamples,BookExample.class).run();
        //than ask for book id and change the book status to lost
        if(bookExamples.size() >0 ){
            System.out.println("ENTRE BOOK EXAMPLE ID SO YOU CAN CHANGE IT TO "+availability.toString()+" ");
            System.out.print("ID : ");
            long id= scanner.nextLong();
            BookExample bookExample1 = new BookExample();
            String[] params={
                    "AVAILABILITY",availability.toString()
            };
            bookExample1.setId(id);
            this.bookExampleDaoCrudDao.update(bookExample1,params);
            Print.log("THE BOOK "+availability.toString()+"  WITH ID :"+ id);

        }else{
            System.out.println("THIS BOOK DOESN'T HAVE EXAMPLES IN ");
        }


        this.displayOptions(scanner);





    }

}
