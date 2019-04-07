<%-- 
    Document   : index
    Created on : Apr 3, 2019, 7:07:14 PM
    Author     : aquil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.Service"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
  <head><title>SCO-BlackBox</title></head>
  <body>
        
    <h1>SCO-BlackBox</h1>
    <h2>Data Retrieval Actions</h2>
        <%
            String time = "";
            if (request.getAttribute("time") != null) {
                time = (String) request.getAttribute("time");
            }
        %> 
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
        
        
        <%
            HashMap<String, Service> serviceMap = new HashMap<>();
            if (request.getAttribute("serviceMap") != null) {
                serviceMap = (HashMap<String, Service>) request.getAttribute("serviceMap");
            }
            ArrayList<Service> serviceList = new ArrayList<>(serviceMap.values());
            
            HashMap<String, Service> serviceMap2 = new HashMap<>();
            if (request.getAttribute("serviceMap2") != null) {
                serviceMap2 = (HashMap<String, Service>) request.getAttribute("serviceMap2");
            }
            ArrayList<Service> serviceList2 = new ArrayList<>(serviceMap.values());
            
        %> 
        <form action='ServiceServlet' method='get' name ="ServiceServlet">
       
            <h3>Get Service List</h3>
            <p>provides dynamic details of all service requests</p>
            <button type="submit" class="btn btn-amber">Get Service List</button>

        </form>
        <%                            
            if (!serviceList.isEmpty()) {
                for(Service s: serviceList){
        %>
        <p class="text-center red-text"><%=s.getMMSI()%></p>
        <%
                }
            }
        %>
        <h2 class="text-center red-text"><%="Service List 2"%></h2>
        <%                            
            if (!serviceList2.isEmpty()) {
                for(Service s: serviceList2){
        %>
        <p class="text-center red-text"><%=s.getMMSI()%></p>
        <%
                }
            }
        %>
  </body>
</html>
