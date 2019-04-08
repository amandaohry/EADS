<%-- 
    Document   : index
    Created on : Apr 3, 2019, 7:07:14 PM
    Author     : aquil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.Service"%>
<%@page import="entity.ServiceVessel"%>
<%@page import="java.util.*"%>
<%@page import="utility.TimeUtility"%>
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
        <p>Simulation Start time: <%= TimeUtility.getSimpleDateFormat().format(TimeUtility.getSimulationStartTime())%></p>
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

        
        <form action='ServiceServlet' method='get' name ="ServiceServlet">
       
            <h3>Get Service List</h3>
            <p>provides dynamic details of all service requests</p>
            <button type="submit" class="btn btn-amber">Get Service List</button>

        </form>
        
        <form action='ServiceVesselServlet' method='get' name ="ServiceVesselServlet">
       
            <h3>Get Service Vessel Detail</h3>
            <p>provides static details of all bunker barges.</p>
            <button type="submit" class="btn btn-amber">Get Service Vessel Detail</button>

        </form>
                <form action='ServiceByRequestIDServlet' method='get' name ="ServiceByRequestIDServlet">
       
            <h3>Get Service List by request ID</h3>
            <input type="text" id="requestID" class="form-control" name='requestID'>
            <button type="submit" class="btn btn-amber">Get Service List By request ID</button>

        </form>
        <form action='ServiceVesselByMMSIServlet' method='get' name ="ServiceVesselByMMSIServlet">
       
            <h3>Get Service Vessel List by MMSI</h3>
            <input type="text" id="mmsi" class="form-control" name='mmsi'>
            <button type="submit" class="btn btn-amber">Get Service Vessel List by MMSI</button>

        </form>
        
        
  </body>
</html>
