package ma.yc.sas.mapper;

import java.sql.ResultSet;
import java.util.List;

public interface DatabaseObjectMapper {
    public Object toObject(ResultSet resultSet)throws Exception;
    public List<Object> toListObject(ResultSet resultSet)throws Exception;
    public List<Object> fromListObject();
    public String fromObject();
}
