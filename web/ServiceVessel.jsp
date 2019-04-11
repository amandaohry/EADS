<%-- 
    Document   : ServiceVessel
    Created on : Apr 8, 2019, 4:49:52 PM
    Author     : aquil
--%>

<%@page import="entity.ServiceVessel"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Service Vessel</title>
    </head>
    <body>
        <h3 class="text-center red-text"><%="Service Vessel List 1 (getServiceVesselDetail)"%></h3>
        <%
            ArrayList<ServiceVessel> serviceVesselList = null;
            if (request.getAttribute("serviceVesselList") != null) {
                serviceVesselList = (ArrayList<ServiceVessel>) request.getAttribute("serviceVesselList");
            }
            ArrayList<ServiceVessel> serviceVesselList2 = null;
            if (request.getAttribute("serviceVesselList2") != null) {
                serviceVesselList2 = (ArrayList<ServiceVessel>) request.getAttribute("serviceVesselList2");
            }
            ArrayList<ServiceVessel> serviceVesselList3 = null;
            if (request.getAttribute("serviceVesselList3") != null) {
                serviceVesselList3 = (ArrayList<ServiceVessel>) request.getAttribute("serviceVesselList3");
            }
     
            
        %> 
        <table border=1>
            <th>index</th>
            <th>MMSI</th>
            <th>Pump Rate</th>
            <th>Capacity</th>
            <th>current hold</th>
            <th>status</th>
            <th>distance</th>
            <th>DriftTime</th>
            <th>DriftCost</th>
            <th>FuelDelivered</th>
            <th>GrossProfit</th>
            <th>OperCost</th>
        <%                    
            int counter = 0;
            if (!serviceVesselList.isEmpty()) {
                for(ServiceVessel s: serviceVesselList){
                    counter++;
        %>
        
            <tr>
                <td><%=counter%></td>
                <td><%=s.getMMSI()%></td>
                <td><%=s.getPumpRate()%></td>
                <td><%=s.getCapacity()%></td>
                <td><%=s.getCurrentCapacity()%></td>
                <td><%=s.getStatus()%></td>
                <td><%=s.getDistance()%></td>
                <td><%=s.getDriftTime()%></td>
                <td><%=s.getDriftCost()%></td>
                <td><%=s.getFuelDelivered()%></td>
                <td><%=s.getGrossProfit()%></td>
                <td><%=s.getOperCost()%></td>
            </tr>
       
        <p class="text-center red-text"></p>
        <%
                }
            }
        %>
         </table>
        <h3 class="text-center red-text"><%="Service Vessel List 2 (getServiceVesselStatus)"%></h3>
        <table border=1>
            <th>MMSI</th>
            <th>Pump Rate</th>
            <th>Capacity</th>
            <th>current hold</th>
            <th>status</th>
            <th>distance</th>
            <th>DriftTime</th>
            <th>DriftCost</th>
            <th>FuelDelivered</th>
            <th>GrossProfit</th>
            <th>OperCost</th>
        <%                
            int counter2 = 0;
            if (!serviceVesselList2.isEmpty()) {
                for(ServiceVessel s: serviceVesselList2){
                    counter2++;
        %>
            <tr>
                <td><%=counter2%></td>
                <td><%=s.getMMSI()%></td>
                <td><%=s.getPumpRate()%></td>
                <td><%=s.getCapacity()%></td>
                <td><%=s.getCurrentCapacity()%></td>
                <td><%=s.getStatus()%></td>
                <td><%=s.getDistance()%></td>
                <td><%=s.getDriftTime()%></td>
                <td><%=s.getDriftCost()%></td>
                <td><%=s.getFuelDelivered()%></td>
                <td><%=s.getGrossProfit()%></td>
                <td><%=s.getOperCost()%></td>
            </tr>
        <%
                }
            }
        %>
        </table>
        
        <h3 class="text-center red-text"><%="Service Vessel List 3 (getServiceVesselStatistics)"%></h3>
        <table border=1>
            <th>index</th>
            <th>MMSI</th>
            <th>Pump Rate</th>
            <th>Capacity</th>
            <th>current hold</th>
            <th>status</th>
            <th>distance</th>
            <th>DriftTime</th>
            <th>DriftCost</th>
            <th>FuelDelivered</th>
            <th>GrossProfit</th>
            <th>OperCost</th>
        <%                            
            int counter3 = 0;
            if (!serviceVesselList3.isEmpty()) {
                for(ServiceVessel s: serviceVesselList3){
                    counter3++;
        %>
            <tr>
                <td><%=counter3%></td>
                <td><%=s.getMMSI()%></td>
                <td><%=s.getPumpRate()%></td>
                <td><%=s.getCapacity()%></td>
                <td><%=s.getCurrentCapacity()%></td>
                <td><%=s.getStatus()%></td>
                <td><%=s.getDistance()%></td>
                <td><%=s.getDriftTime()%></td>
                <td><%=s.getDriftCost()%></td>
                <td><%=s.getFuelDelivered()%></td>
                <td><%=s.getGrossProfit()%></td>
                <td><%=s.getOperCost()%></td>
            </tr>
        <%
                }
            }
        %>
        </table>
    </body>
</html>
