<%-- 
    Document   : assignment
    Created on : Apr 10, 2019, 8:10:26 PM
    Author     : aquil
--%>

<%@page import="controller.AssignmentServlet"%>
<%@page import="entity.*"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<p><%=AssignmentServlet.result[0]%></p>
<p><%=AssignmentServlet.result[1]%></p>
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
            <th>vessel 1</th>
            <th>vessel 2</th>
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
                <td><%out.println(serviceAndVesselMap.get(s).get(0).mmsi);%></td>
        <%
            if (serviceAndVesselMap.get(s).size()==2){
        %>
                <td><%out.println(serviceAndVesselMap.get(s).get(1).mmsi);%></td>
        <%
            } else {
        %>
        <td></td>
        <%
            }
        %>
            </tr>
        <%
            }
        %>