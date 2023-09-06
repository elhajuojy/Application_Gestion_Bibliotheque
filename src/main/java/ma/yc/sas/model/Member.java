package ma.yc.sas.model;

import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Member  {

    private long numero_membre;
    private String nom ;
    private String prenom;
    private List<Emprunt> emprunts;


}
