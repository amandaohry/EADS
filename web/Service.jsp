<%-- 
    Document   : Service
    Created on : Apr 8, 2019, 4:47:44 PM
    Author     : aquil
--%>

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
            HashMap<String, ArrayList<Service>> serviceMap = new HashMap<>();
            if (request.getAttribute("serviceMap") != null) {
                serviceMap = (HashMap<String, ArrayList<Service>>) request.getAttribute("serviceMap");
            }
            ArrayList<ArrayList<Service>> listOfServiceList = new ArrayList<>(serviceMap.values());
            
            HashMap<String, ArrayList<Service>> serviceMap2 = new HashMap<>();
            if (request.getAttribute("serviceMap2") != null) {
                serviceMap2 = (HashMap<String, ArrayList<Service>>) request.getAttribute("serviceMap2");
            }
            ArrayList<ArrayList<Service>> listOfServiceList2 = new ArrayList<>(serviceMap.values());
            
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
            if (!listOfServiceList.isEmpty()) {
                for(ArrayList<Service> serviceList: listOfServiceList){
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
                }
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
            if (!listOfServiceList2.isEmpty()) {
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
