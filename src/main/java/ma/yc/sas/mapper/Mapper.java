package ma.yc.sas.mapper;

import java.sql.ResultSet;
import java.sql.PreparedStatement;

public interface Mapper<T> {

    T toClassObject(ResultSet resultSet);

    PreparedStatement PreparedStatement(T t,PreparedStatement preparedStatement);


}
