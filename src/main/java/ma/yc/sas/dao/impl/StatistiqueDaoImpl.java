package ma.yc.sas.dao.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import ma.yc.sas.core.Print;
import ma.yc.sas.core.Util;
import ma.yc.sas.dao.CrudDao;
import ma.yc.sas.dao.StatistiqueDao;
import ma.yc.sas.database.DatabaseConnection;
import ma.yc.sas.enums.Availability;
import ma.yc.sas.model.Book;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StatistiqueDaoImpl implements StatistiqueDao {

    private CrudDao<Book> bookDao ;
    private List<Book> books = new ArrayList<>();
    private Connection connection ;

    public StatistiqueDaoImpl() throws SQLException {
        this.bookDao = new BookDaoImpl();
        this.books = this.bookDao.getAll();
        this.connection = DatabaseConnection.getInstance().getConnection();
//        Table.render(books,Book.class).run();
    }

    public  int countAllBooksFromList(boolean countAll,Availability... availability ){
        try{
            String query = "SELECT COUNT(*) from BOOK_EXAMPLE";
            if (!countAll){

                query = query + " WHERE AVAILABILITY= ?";
                PreparedStatement preparedStatement1 = this.connection.prepareStatement(query);;
                String availabilityString = availability[0].toString();
                preparedStatement1.setString(1, availabilityString);
                ResultSet resultSet = preparedStatement1.executeQuery();
                if (resultSet.next()) {
                    int countResult = resultSet.getInt(1); // Get the count from the first column
                    return countResult;
                }

            }

            PreparedStatement preparedStatement = this.connection.prepareStatement(query);;
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int countResult = resultSet.getInt(1); // Get the count from the first column
                return countResult;
            }





        }catch (SQLException e){
            Print.log(e.toString());
        }

        return 0;
    }

    @Override
    public void generateRapport() {
        Document document = new Document();

        try {
            // Generate a random number between 0 and 9999
            Random random = new Random();
            int randomNumber = random.nextInt(10000);

            // Define the output file path for the PDF report with the random number
            String outputPath = "rapport-output/report" + randomNumber + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));

            // Open the document for writing
            document.open();

            // Add content to the PDF report
            // You can use the document object to add text, tables, images, etc.

            // Add a title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Library Statistics Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Add a date
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Paragraph dateParagraph = new Paragraph("Date: " + dateFormat.format(currentDate));
            dateParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(dateParagraph);

            // Add a section for book statistics
            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
            Paragraph bookSection = new Paragraph("Book Statistics", sectionFont);
            bookSection.setAlignment(Element.ALIGN_LEFT);
            document.add(bookSection);

            // Add the number of total books
            int totalBooks = countAllBooks();
            Paragraph totalBooksParagraph = new Paragraph("Total Books: " + totalBooks);
            document.add(totalBooksParagraph);

            // Add the number of available books
            int availableBooks = countAvailableBooks();
            Paragraph availableBooksParagraph = new Paragraph("Available Books: " + availableBooks);
            document.add(availableBooksParagraph);

            // Add the number of not available books
            int notAvailableBooks = countNotAvailableBooks();
            Paragraph notAvailableBooksParagraph = new Paragraph("Not Available Books: " + notAvailableBooks);
            document.add(notAvailableBooksParagraph);

            // Add the number of lost books
            int lostBooks = countLostBooks();
            Paragraph lostBooksParagraph = new Paragraph("Lost Books: " + lostBooks);
            document.add(lostBooksParagraph);

            // Add more content to the report as needed
            // ...

            // Close the document when finished
            document.close();

            // Print a message indicating that the report has been generated
            System.out.println("PDF report generated successfully at " + outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int countAllBooks() {
        return this.countAllBooksFromList(true);
    }

    @Override
    public int countAvailableBooks() {
        return this.countAllBooksFromList(false,Availability.AVAILABLE);
    }

    @Override
    public int countNotAvailableBooks() {
        return this.countAllBooksFromList(false,Availability.NOT_AVAILABLE);
    }

    @Override
    public int countLostBooks() {
        return this.countAllBooksFromList(false,Availability.LOST);
    }
}
