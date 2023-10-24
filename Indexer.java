package org.example;

import org.jsoup.nodes.Document;

import javax.print.Doc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Indexer {
    static Connection connection = null;
    Indexer(Document document, String url){
        //Select Important elements of document
        String title = document.title();
        String link = url;
        String text = document.text();
        //Save these elements in database
        try {
            connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("Insert into webpage values(?, ?, ?);");
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, link);
            preparedStatement.setString(3, text);
            preparedStatement.executeUpdate();
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
