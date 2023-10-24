package com.SimpleSearch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    static Connection connection = null;
    public static Connection getConnection(){
        if(connection != null){
            return connection;
        }

        String user = "root";
        String password = "Saihp@2905";
        String db = "searchenginedata";
        return getConnection(user, password, db);
    }

    private static Connection getConnection(String user, String password, String database){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Driver class present in Library is going to be called
            //Driver class sets the connection
            /*The protocol name, which is jdbc
              The sub-protocol name, which is mysql
              The host name, which is localhost
              The database name, which is stored in the variable database
              The username, which is stored in the variable user
              The password, which is stored in the variable password */
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + database + "?user=" + user + "&password=" + password); // connection is establishing
        }
        catch(ClassNotFoundException classNotFoundException){
            classNotFoundException.printStackTrace();
        }

        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return connection;
    }
}
