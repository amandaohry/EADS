<%-- 
    Document   : index
    Created on : Apr 3, 2019, 7:07:14 PM
    Author     : aquil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.Service"%>
<%@page import="entity.ServiceVessel"%>
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
        <h3 class="text-center red-text"><%="Service List 1 (getServiceDetail) "%></h3>
        <%                            
            if (!serviceList.isEmpty()) {
                for(Service s: serviceList){
        %>
        <p class="text-center red-text"><%=s.getRequestID()%></p>
        <%
                }
            }
        %>
        <h3 class="text-center red-text"><%="Service List 2 (getServiceStatus) "%></h3>
        <%                            
            if (!serviceList2.isEmpty()) {
                for(Service s: serviceList2){
        %>
        <p class="text-center red-text"><%=s.getStatus()%></p>
        <%
                }
            }
        %>
        
        <%
            HashMap<String, ServiceVessel> serviceVesselMap = new HashMap<>();
            if (request.getAttribute("serviceVesselMap") != null) {
                serviceVesselMap = (HashMap<String, ServiceVessel>) request.getAttribute("serviceVesselMap");
            }
            ArrayList<ServiceVessel> serviceVesselList = new ArrayList<>(serviceVesselMap.values());
            
            HashMap<String, ServiceVessel> serviceVesselMap2 = new HashMap<>();
            if (request.getAttribute("serviceVesselMap2") != null) {
                serviceVesselMap2 = (HashMap<String, ServiceVessel>) request.getAttribute("serviceVesselMap2");
            }
            ArrayList<ServiceVessel> serviceVesselList2 = new ArrayList<>(serviceVesselMap2.values());
            
            HashMap<String, ServiceVessel> serviceVesselMap3 = new HashMap<>();
            if (request.getAttribute("serviceVesselMap3") != null) {
                serviceVesselMap3 = (HashMap<String, ServiceVessel>) request.getAttribute("serviceVesselMap3");
            }
            ArrayList<ServiceVessel> serviceVesselList3 = new ArrayList<>(serviceVesselMap3.values());
            
        %> 
        <form action='ServiceVesselServlet' method='get' name ="ServiceVesselServlet">
       
            <h3>Get Service Vessel Detail</h3>
            <p>provides static details of all bunker barges.</p>
            <button type="submit" class="btn btn-amber">Get Service Vessel Detail</button>

        </form>
        <h3 class="text-center red-text"><%="Service Vessel List 1 (getServiceVesselDetail)"%></h3>
        <%                            
            if (!serviceVesselList.isEmpty()) {
                for(ServiceVessel s: serviceVesselList){
        %>
        <p class="text-center red-text"><%=s.getPumpRate()%></p>
        <%
                }
            }
        %>
        <h3 class="text-center red-text"><%="Service Vessel List 2 (getServiceVesselStatus)"%></h3>
        <%                            
            if (!serviceVesselList2.isEmpty()) {
                for(ServiceVessel s: serviceVesselList2){
        %>
        <p class="text-center red-text"><%=s.getCurrentCapacity()%></p>
        <%
                }
            }
        %>
        <h3 class="text-center red-text"><%="Service Vessel List 3 (getServiceVesselStatistics)"%></h3>
        <%                            
            if (!serviceVesselList3.isEmpty()) {
                for(ServiceVessel s: serviceVesselList3){
        %>
        <p class="text-center red-text"><%=s.getDriftCost()%></p>
        <%
                }
            }
        %>
  </body>
</html>
