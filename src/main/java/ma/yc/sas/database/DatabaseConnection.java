package ma.yc.sas.database;

import ma.yc.sas.core.Print;

import java.sql.*;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/LIBRARY";
    private String username = "root";
    private String password = "password";
    private String driver = "com.mysql.cj.jdbc.Driver";


    private DatabaseConnection() throws SQLException {
        try {
            Class.forName(driver);
            this.connection = DriverManager.getConnection(url, username, password);
            Print.log("Connection established.");
        } catch (ClassNotFoundException ex) {
           Print.log("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }

        return instance;
    }

    public static boolean closeConnection(){

        if (instance ==null){
            return false;
        }else {
            try {
                instance.getConnection().close();
                instance = null;
                return true;
            } catch (SQLException e) {
                Print.log(e.toString());
            }
        }
        return false;
    }


}

