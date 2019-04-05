<%-- 
    Document   : index
    Created on : Apr 3, 2019, 7:07:14 PM
    Author     : aquil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head><title>SCO-BlackBox</title></head>
  <body>
        <%
            String time = "";
            if (request.getAttribute("time") != null) {
                time = (String) request.getAttribute("time");
            }

        %> 
    <h1>SCO-BlackBox</h1>
    <h2>Data Retrieval Actions</h2>
        <form action='CurrentTimeServlet' method='get' name ="CurrentTimeServlet">
       
            <h3>Get Current Time</h3>
            <p>provides the current simulation time at the time of inquiry.</p>
            <button type="submit" class="btn btn-amber">Get Current Time</button>

        </form>
  		
        <%                            
            if (!time.equals("")) {
        %>
        <h4 class="text-center red-text"><%=time%></h4>
        <%
            }
        %>

  </body>
</html>
