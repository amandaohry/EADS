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
<% String time = "00:00:00"; %>
<!DOCTYPE html>
<html>
    <head><title>SCO-BlackBox</title></head>
    <body>
        <h1>SCO-BlackBox</h1>
        <style>
            table, td {
                border: 1px solid black;
            }
        </style>
    </head>

    <h2>Start Running Model</h2>
    <p>Time Interval: 1 min</p>

    <p>Show Current Time</p>
    <button id="ctbotton" onclick="setInterval(getCurrentTime, 60000);">Generate Current Time</button>
    
    <p>Show Travel Time Time</p>
    <button id = "ttbutton" onclick="setInterval(getTravelTime, 60000;)">Generate Travel Time</button>

    <h2>Data Retrieval Actions</h2>
    <%
        if (request.getAttribute("time") != null) {
            time = (String) request.getAttribute("time");
        }
    %> 
    <p>Simulation Start time: <%= TimeUtility.getSimpleDateFormat().format(TimeUtility.getSimulationStartTime())%></p>
    <form action='CurrentTimeServlet' method='get' name ="CurrentTimeServlet">
        <h3>Get Current Time</h3>
        <p>provides the current simulation time at the time of inquiry.</p>
        <button id="getct" type="submit" class="btn btn-amber">Get Current Time</button>
    </form>
    <%
        if (!time.equals("")) {
    %>
    <h4 class="text-center red-text"><%=time%></h4>
    <%
        }
    %>

    <%
        int travelTime = 0;
        if (request.getAttribute("travelTime") != null) {
            travelTime = (int) request.getAttribute("travelTime");
        }
    %> 
    <form action='TravelTimeServlet' method='get' name ="TravelTimeServlet">
        <h3>Get Travel Time</h3>
        <p>provides predicted vessel travel time from source, destination and vessel information. Source and destination may be provided as <Longitude>,<Latitude> or <Location Name> or <Service Request ID>. Vessel information is used for routing, taking into account vessel properties when calculating travel time.</p>
                        Source: <input type="text" id="source" class="form-control" name='source'><br>
                        Destination: <input type="text" id="destination" class="form-control" name='destination'><br>
                        MMSI: <input type="text" id="mmsi" class="form-control" name='mmsi'><br>
                        <button id="gettt" type="submit" class="btn btn-amber">Get Travel Time</button>
                        </form>
                        <%
                            if (travelTime != 0) {
                        %>
                        <h4 class="text-center red-text"><%=travelTime%></h4>
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
                        <script>
                            function getCurrentTime() {
                                //setInterval(function () {document.getElementById("getct").click();}, 120000);
                                document.getElementById("getct").click();
                                var x = document.createElement("TABLE");
                                x.setAttribute("id", "currentTable");
                                document.body.appendChild(x);
                                document.getElementById("currentTable").deleteTHead();

                                var y = document.createElement("TR");
                                document.getElementById("currentTable").appendChild(y);
                                
                                var t = document.createTextNode("<%=time%>");
                                y.appendChild(t);
                                document.getElementById("currentTable").appendChild(y);
                                
                                var table = document.getElementById("currentTable");
                                var header = table.createTHead();
                                var row = header.insertRow(0);
                                row.innerHTML = "<b>Current Time Output</b>";
                                
                                //setInterval(function () {document.getElementById("ctbutton").click();}, 10000); //update output, time interval
                            }
                            function getTravelTime() {
                                document.getElementById("gettt").click();
                                var a = document.createElement("TABLE");
                                a.setAttribute("id", "travelTable");
                                document.body.appendChild(a);
                                document.getElementById("travelTable").deleteTHead();

                                var b = document.createElement("TR");
                                document.getElementById("travelTable").appendChild(b);

                                var c = document.createTextNode("<%=travelTime%>");
                                b.appendChild(c);
                                document.getElementById("travelTable").appendChild(b);
                                
                                var table = document.getElementById("travelTable");
                                var header = table.createTHead();
                                var row = header.insertRow(0);
                                //setInterval(function () {document.getElementById("ttbutton").click();}, 10000);// update time interval for travel tiem
                            }
                        </script>


                        </body>
                        </html>
