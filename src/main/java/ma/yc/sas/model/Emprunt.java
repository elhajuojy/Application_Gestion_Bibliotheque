package ma.yc.sas.model;

import lombok.*;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Emprunt {
    private Member member;
    private Date dateEmprunt;
    private Date dateRoutour;
    private List<BookExample> bookExamples;




}
