package ma.yc.sas.database;

import ma.yc.sas.mapper.DatabaseObjectMapper;
import ma.yc.sas.model.Book;

import javax.management.Query;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class Mysql_JDbC {

    private Connection connection ;
    public ResultSet resultSet ;


    public Mysql_JDbC(String url ,String user , String password)throws Exception {
        System.out.println("MySQL JDBC Connection Testing ~");
        this.connection = DriverManager.getConnection(url,user,password);
    }




    public void query(String statement )throws Exception{
        this.resultSet =this.connection.prepareStatement(statement).executeQuery();
    }




}

