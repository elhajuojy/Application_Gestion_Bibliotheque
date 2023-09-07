package ma.yc.sas.model;

import lombok.*;
import ma.yc.sas.core.Util;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Book  {


    private String titre ;
    @NonNull
    private int quantite ;
    @NonNull
    private Long ISBN;
    @NonNull
    private String author;

    private List<BookExample> bookExamples = null;


    public void GenerateIsbn(){
        this.ISBN = Util.generatedLong();
    }

}
