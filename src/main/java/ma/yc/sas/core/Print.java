package ma.yc.sas.core;

import ma.yc.sas.model.Book;
import pl.mjaron.etudes.Obj;
import pl.mjaron.etudes.Table;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Print<T> {

//    T objectToPrint ;
//
//    public Print(T objectToPrint) {
//        this.objectToPrint = objectToPrint;
//    }

    public static <T> void log(T objectToPrint){
        System.out.println(objectToPrint);
    }
}

