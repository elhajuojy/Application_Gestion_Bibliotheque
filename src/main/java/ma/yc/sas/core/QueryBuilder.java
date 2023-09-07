package ma.yc.sas.core;

import ma.yc.sas.enums.QueryStatementVerbs;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class QueryBuilder {

    private AtomicReference<String> query = new AtomicReference<>(null);


    private  String queryBuilder(QueryStatementVerbs queryStatementVerbs ,String table, String... paramters){

        query.set(queryStatementVerbs.toString());
        for (int i=0 ;i+1<paramters.length ;i++){
            paramters[i] = paramters[i] +" , ";
        }
        Arrays.stream(paramters).toList().forEach((param)->{
            query.set(query + " " + param );
        });
        return query.get();
    }


    private List<String>  getObjectKeys(Object object){
        List<String> keys  = new ArrayList<>();
        Class<?> objectClass = object.getClass();
        Field[] fields = objectClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            keys.add(field.getName());

        }
        return keys ;
    }

    private List<String> getObjectValues(Object object) throws  Exception{
        List<String> values  = new ArrayList<>();
        Class<?> objectClass = object.getClass();
        Field[] fields = objectClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            values.add(field.get(object).toString());
        }
        return values ;
    }

    public  String select(String table ,String... parameters){

        // SELECT PARAMETERS FROM table ;
        query.set("");
        query.set(this.queryBuilder(QueryStatementVerbs.SELECT,table,parameters));
        query.set(query + " FROM "+ table +" ; " );
        return query.get() ;

    };
    public  String insert(String table , Object object) throws Exception{

//        INSERT INTO Customers(ID, FirstName, LastName)
//        VALUES ('1', 'User', 'Test');
        List<String> keys  = getObjectKeys(object);
        List<String> values =getObjectValues(object);
        query.set("");
        query.set(QueryStatementVerbs.INSERT.verb + " " + table);
        query.set(query+ " ( "  );
        for (int i=0 ; i< keys.toArray().length;i++){
            values.set(i, "'"+values.get(i)+"'");
        }

        for (int i=0 ; i+1< keys.toArray().length;i++){
            keys.set(i, keys.get(i)+" , ");
            values.set(i,values.get(i)+" , ");
        }

        for (int i=0;i<keys.toArray().length;i++){
            query.set(query +" " + keys.toArray()[i]);
        }
        query.set(query+ " ) VALUES ( "  );

        for (int i=0;i<values.toArray().length;i++){
            query.set(query +" " + values.toArray()[i]);
        }

        query.set(query+ " ) ; "  );

        return query.get();
    }


    public  String delete(String table , Object object){






        return null;
    };

    public  String update(String table , Object object , String condition){
        //        UPDATE table_name
        //        SET column1 = value1, column2 = value2, ...
        //        WHERE condition;
        query.set("");
        query.set(QueryStatementVerbs.UPDATE.verb + " " + table +" SET ");
        List<String> keys  = getObjectKeys(object);
        List<String> values = getObjectKeys(object);

        for (int i=0;i<keys.toArray().length; i++){
            keys.set(i, keys.get(i)+" = ? , ");
        }

        for (int i=0;i<keys.toArray().length;i++){
            query.set(query +" " + keys.toArray()[i]);
        }

        query.set(query + " WHERE " + condition + " ; ");

        return  query.get();
    };



}
