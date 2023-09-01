package ma.yc.sas.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Print {

    public static void  log(String... values){
        Arrays.stream(values).toList().forEach((value)->{
            System.out.println(value);
        });
    }
    public  static void table(Collection<Object> table){

    }
}
