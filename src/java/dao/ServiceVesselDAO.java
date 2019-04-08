/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import entity.Service;
import entity.ServiceVessel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author aquil
 */
public class ServiceVesselDAO {
    private static HashMap<String, ServiceVessel> serviceVesselMap;

    public ServiceVesselDAO(HashMap<String, ServiceVessel> serviceVesselMap) {
        this.serviceVesselMap = serviceVesselMap;
    }
    
    public HashMap<String, ServiceVessel> getServiceVesselDetail() throws MalformedURLException, IOException{
        HashMap<String, ServiceVessel> serviceVesselMap = this.serviceVesselMap;
                JsonObject jsonOutput = new JsonObject();
        URL blackbox = new URL("http://127.0.0.1:8080/getServiceVesselDetail");
        URLConnection conn = blackbox.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        try (JsonReader jsonReader = new JsonReader(in)) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
                System.out.println("name = " + name);
                if (name.equals("Result")) {
                    
                    serviceVesselMap = readServiceVesselDetail(jsonReader, serviceVesselMap);
                }
                if (name.equals("Status")){
                    String status = jsonReader.nextString();
                }
                if (name.equals("Warnings")){
                    readWarnings(jsonReader);
                }
            }
                
            
            jsonReader.endObject();
        }
        return serviceVesselMap;
    }
    
    public HashMap<String, ServiceVessel> readServiceVesselDetail(JsonReader jsonReader, HashMap<String, ServiceVessel> serviceVesselMap) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String mmsi = jsonReader.nextName();
            System.out.println(mmsi);
            jsonReader.beginObject();
            int capacity = 0;
            int flowRate = 0;
            
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
                System.out.println("name: " + name);
                if (name.equals("Capacity")){
                    capacity = jsonReader.nextInt();
                    System.out.println("capacity: " + capacity);
                }
                if (name.equals("FlowRate")){
                    flowRate = jsonReader.nextInt();
                    System.out.println("flowRate: " + flowRate);
                }
                
            }
            ServiceVessel serviceVessel = new ServiceVessel(mmsi, capacity, flowRate);
            serviceVesselMap.put(mmsi, serviceVessel);
            jsonReader.endObject();
        }
        jsonReader.endObject();
        
        return serviceVesselMap;
    }
    
    public HashMap<String, ServiceVessel> getServiceVesselStatus() throws MalformedURLException, IOException{
        HashMap<String, ServiceVessel> serviceVesselMap = this.serviceVesselMap;
                JsonObject jsonOutput = new JsonObject();
        URL blackbox = new URL("http://127.0.0.1:8080/getServiceVesselStatus");
        URLConnection conn = blackbox.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        try (JsonReader jsonReader = new JsonReader(in)) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
                System.out.println("name = " + name);
                if (name.equals("Result")) {
                    
                    serviceVesselMap = readServiceVesselStatus(jsonReader, serviceVesselMap);
                }
                if (name.equals("Status")){
                    String status = jsonReader.nextString();
                }
                if (name.equals("Warnings")){
                    readWarnings(jsonReader);
                }
            }
                
            
            jsonReader.endObject();
        }
        return serviceVesselMap;
    }
    
    public HashMap<String, ServiceVessel> readServiceVesselStatus(JsonReader jsonReader, HashMap<String, ServiceVessel> serviceVesselMap) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String mmsi = jsonReader.nextName();
            System.out.println(mmsi);
            jsonReader.beginObject();
            int currentHold = 0;
            String status = "";
            
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
                System.out.println("name: " + name);
                if (name.equals("CurrentHold")){
                    currentHold = jsonReader.nextInt();
                    System.out.println("currentHold: " + currentHold);
                }
                if (name.equals("Status")){
                    status = jsonReader.nextString();
                    System.out.println("status: " + status);
                }
                
            }
            ServiceVessel serviceVessel = serviceVesselMap.get(mmsi);
            serviceVessel.setCurrentCapacity(currentHold);
            serviceVessel.setStatus(status);
            
            jsonReader.endObject();
        }
        jsonReader.endObject();
        
        return serviceVesselMap;
    }
    
    public HashMap<String, ServiceVessel> getServiceVesselStatistics() throws MalformedURLException, IOException{
        HashMap<String, ServiceVessel> serviceVesselMap = this.serviceVesselMap;
                JsonObject jsonOutput = new JsonObject();
        URL blackbox = new URL("http://127.0.0.1:8080/getServiceVesselStatistics");
        URLConnection conn = blackbox.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        try (JsonReader jsonReader = new JsonReader(in)) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
                System.out.println("name = " + name);
                if (name.equals("Result")) {
                    
                    serviceVesselMap = readServiceVesselStatistics(jsonReader, serviceVesselMap);
                }
                if (name.equals("Status")){
                    String status = jsonReader.nextString();
                }
                if (name.equals("Warnings")){
                    readWarnings(jsonReader);
                }
            }
                
            
            jsonReader.endObject();
        }
        return serviceVesselMap;
    }
    
    public HashMap<String, ServiceVessel> readServiceVesselStatistics(JsonReader jsonReader, HashMap<String, ServiceVessel> serviceVesselMap) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String mmsi = jsonReader.nextName();
            System.out.println(mmsi);
            jsonReader.beginObject();
            int distance = 0;
            int driftCost = 0;
            int driftTime = 0;
            int fuelDelivered = 0;
            int grossProfit = 0;
            float operCost = 0;
            
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
                System.out.println("name: " + name);
                if (name.equals("Distance")){
                    distance = jsonReader.nextInt();
                    System.out.println("distance: " + distance);
                }
                if (name.equals("DriftCost")){
                    driftCost = jsonReader.nextInt();
                    System.out.println("driftCost: " + driftCost);
                }
                if (name.equals("DriftTime")){
                    driftTime = jsonReader.nextInt();
                    System.out.println("driftTime: " + driftTime);
                }
                if (name.equals("FuelDelivered")){
                    fuelDelivered = jsonReader.nextInt();
                    System.out.println("fuelDelivered: " + fuelDelivered);
                }
                if (name.equals("GrossProfit")){
                    grossProfit = jsonReader.nextInt();
                    System.out.println("grossProfit: " + grossProfit);
                }
                if (name.equals("OperCost")){
                    operCost = (float) jsonReader.nextDouble();
                    System.out.println("operCost: " + operCost);
                }
                
                
            }
            ServiceVessel serviceVessel = serviceVesselMap.get(mmsi);
            serviceVessel.setDistance(distance);
            serviceVessel.setDriftTime(driftTime);
            serviceVessel.setDriftCost(driftCost);
            serviceVessel.setFuelDelivered(fuelDelivered);
            serviceVessel.setGrossProfit(grossProfit);
            serviceVessel.setOperCost(operCost);
            
            jsonReader.endObject();
        }
        jsonReader.endObject();
        
        return serviceVesselMap;
    }
    
    public void readWarnings(JsonReader rd) throws IOException{
        ArrayList<String> warnings = new ArrayList<>();
        rd.beginArray();
        while (rd.hasNext()){
            String nextWarning = rd.nextString();
            warnings.add(nextWarning);
        }
        rd.endArray();
        
    }
    
    public float[] readLocation(JsonReader rd) throws IOException{
        float[] location = new float[2];
        rd.beginArray();
        int counter = 0;
        while (rd.hasNext()){
            location[counter] = (float) rd.nextDouble();
            counter++;
        }
        rd.endArray();
        return location;
    }
    
}
