<%-- 
    Document   : assignment
    Created on : Apr 10, 2019, 8:10:26 PM
    Author     : aquil
--%>

<%@page import="entity.*"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<table border=1>
            <th>index</th>
            <th>MMSI</th>
            <th>requestID</th>
            <th>requestTime</th>
            <th>location</th>
            <th>time (Ei)</th>
            <th>requested fuel</th>
            <th>time delay</th>
            <th>fuel received</th>
            <th>status</th>
        <%
            //assignment
            int counter = 0;
            HashMap<Service, ArrayList<Vessel>> serviceAndVesselMap = null;
            if (request.getAttribute("serviceAndVesselMap")!=null){
                serviceAndVesselMap = (HashMap<Service, ArrayList<Vessel>>) request.getAttribute("serviceAndVesselMap");
            }
            for (Service s : serviceAndVesselMap.keySet()) {
                counter++;
        %>
            <tr>
                <td><%=counter%></td>
                <td><%=s.getMMSI()%></td>
                <td><%=s.getRequestID()%></td>
                <td><%=s.getRequestTime()%></td>
                <td><%=s.getLocation()%></td>
                <td><%=s.getTime()%></td>
                <td><%=s.getRequestedFuel()%></td>
                <td><%=s.getTimeDelay()%></td>
                <td><%=s.getFuelReceived()%></td>
                <td><%=s.getStatus()%></td>
            </tr>
        <%
            }
        %>