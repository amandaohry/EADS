<%--
    Document   : Service
    Created on : Apr 8, 2019, 4:47:44 PM
    Author     : aquil
--%>

<%@page import="java.util.concurrent.ConcurrentHashMap"%>
<%@page import="java.util.*"%>
<%@page import="entity.Service"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Service</title>
    </head>
    <body>

        <%
            ArrayList<Service> serviceList = null;
            if (request.getAttribute("serviceList") != null) {
                serviceList = (ArrayList<Service>) request.getAttribute("serviceList");
            }

            ArrayList<ArrayList<Service>> listOfServiceList2 = null;
            ConcurrentHashMap<String, ArrayList<Service>> serviceMap2 = new ConcurrentHashMap<>();
            if (request.getAttribute("serviceMap2") != null) {
                serviceMap2 = (ConcurrentHashMap<String, ArrayList<Service>>) request.getAttribute("serviceMap2");
                listOfServiceList2 = new ArrayList<>(serviceMap2.values());
            }


        %>
        <h3 class="text-center red-text"><%="Service List 1 (getServiceDetail) "%></h3>
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
                    for(Service s: serviceList){
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
        </table>
        <h3 class="text-center red-text"><%="Service List 2 (getServiceStatus)"%></h3>
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
            if (listOfServiceList2!=null) {
                for(ArrayList<Service> serviceList2: listOfServiceList2){
                    for(Service s: serviceList2){
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
            }
        %>
        </table>
    </body>
</html>
