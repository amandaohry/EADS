<%-- 
    Document   : ServiceVesselByMMSI
    Created on : Apr 8, 2019, 3:36:59 PM
    Author     : aquil
--%>

<%@page import="entity.ServiceVessel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Service Vessel by MMSI</title>
    </head>
    <body>
        <h3>Service Vessel by MMSI</h3>
        <%
            ServiceVessel sv = null;
            if (request.getAttribute("serviceVesselByMMSI") != null) {
                sv = (ServiceVessel) request.getAttribute("serviceVesselByMMSI");
            }
        %> 
        <%                            
            if (sv!=null) {
        %>
        <h4 class="text-center red-text"><%=sv.getMMSI()%></h4>
        <%
            }
        %>
    </body>
</html>
