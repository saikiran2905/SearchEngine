package com.SimpleSearch;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/Search")
public class Search extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String keyword = request.getParameter("keyword");
        //Setting up connection to database
        Connection connection = DataBaseConnection.getConnection();
        try {
            //Store the Query of the user
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO History VALUES(?, ?);");
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, "http://localhost:8080/SearchEngine/Search?keyword="+keyword);
            preparedStatement.executeUpdate();

            //Getting results after running the ranking query
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT pageTitle, pageLink, (LENGTH(LOWER(pageText)) - LENGTH(REPLACE(LOWER(pageText), '" + keyword.toLowerCase() + "', '')))/LENGTH('" + keyword.toLowerCase() + "') AS OCCURENCE FROM webpage ORDER BY OCCURENCE DESC LIMIT 30;");
            ArrayList<SearchResult> results = new ArrayList<SearchResult>();
            //Transferring values from ResultSet to results ArrayList
            while (resultSet.next()) {
                SearchResult searchResult = new SearchResult();
                searchResult.setTitle(resultSet.getString("pageTitle"));
                searchResult.setLink(resultSet.getString("pageLink"));
                results.add(searchResult);
            }

            for(SearchResult result : results){
                System.out.println(result.getTitle() + "\n" + result.getLink() + "\n");
            }
            request.setAttribute("results", results);
            request.getRequestDispatcher("search.jsp").forward(request, response);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        catch (ServletException servletException){
            servletException.printStackTrace();
        }
    }
}
