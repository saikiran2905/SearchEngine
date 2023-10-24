<%@page import = "java.util.ArrayList"%>
<%@page import = "com.SimpleSearch.HistoryResult"%>
<html>
    <head>
        <link rel = "stylesheet" type ="text/css" href = "style.css">
    </head>
    <body>
        <h1> Search Engine </h1>
        <form action = "Search">
            <input type = "text" name = "keyword"></input>
            <button type = "submit">Search</button>
        </form>

        <form action = "History">
        <button type = "submit">History</button>
        </form>
        <table border = 2 class = "resultTable">
            <tr>
                <th>Keyword</th>
                <th>Link</th
            </tr>
            <%
                ArrayList<HistoryResult> results = (ArrayList<HistoryResult>)request.getAttribute("results");
                for(HistoryResult historyResult : results){
            %>
            <tr>
                <td><%out.println(historyResult.getKeyword());%></td>
                <td><a href = "<%out.println(historyResult.getLink());%>"><%out.println(historyResult.getLink());%> </a></td>
            </tr>
            <%
                }
            %>
        </table>
    </body>
</html>