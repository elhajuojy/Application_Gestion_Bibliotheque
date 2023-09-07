package ma.yc.sas.model;

import lombok.*;
import ma.yc.sas.enums.Availability;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BookExample {

    private Long id ;
    private Availability availability;
    private Book book ;


}
