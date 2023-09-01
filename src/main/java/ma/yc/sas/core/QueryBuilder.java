package ma.yc.sas.core;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class QueryBuilder {



    public static  String select(String table ,String... paramters){
        AtomicReference<String> query = new AtomicReference<>("SELECT ");

        for (int i=0 ;i+1<paramters.length ;i++){
            paramters[i] = paramters[i] +" , ";
        }
        Arrays.stream(paramters).toList().forEach((param)->{
            query.set(query + " " + param );
        });
        query.set(query + " FROM "+ table +" ; " );
        return query.get();

    };
    public static String insert(String table , String... paramters){
        AtomicReference<String> query = new AtomicReference<>("INSERT ");
        return query.get() ;
    }


    public static String delete(String table , String... paramters){
        AtomicReference<String> query = new AtomicReference<>("DELETE ");
        return query.get() ;
    };

    public static String update(String table , String... paramters){
        AtomicReference<String> query = new AtomicReference<>("UPDATE ");
        return query.get() ;
    };



}
