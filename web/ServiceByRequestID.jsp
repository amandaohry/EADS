<%-- 
    Document   : ServiceByReqestID
    Created on : Apr 8, 2019, 3:35:08 PM
    Author     : aquil
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="entity.Service"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Service Request by ID</title>
    </head>
    <body>
        <h3>Service Request by ID List 1 (getServiceDetailByRID)</h3>
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
            int counter = 0;
            ArrayList<Service> sList = new ArrayList<>();
            if (request.getAttribute("serviceByRequestIDList") != null) {
                sList = (ArrayList<Service>) request.getAttribute("serviceByRequestIDList");
            }
            if (sList.size()!=0){
                for(Service s: sList){
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
            }
        %>
        </table>
        <h3>Service Request by ID List 2 (getServiceStatusByRID)</h3>
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
            int counter2 = 0;
            ArrayList<Service> sList2 = new ArrayList<>();
            if (request.getAttribute("serviceByRequestIDList2") != null) {
                sList2 = (ArrayList<Service>) request.getAttribute("serviceByRequestIDList2");
            }
            if (sList.size()!=0){
                for(Service s: sList){
                    counter2++;
        %>
            <tr>
                <td><%=counter2%></td>
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
            }
        %>
        </table>
    </body>
</html>
