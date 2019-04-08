/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.google.gson.JsonArray;
import entity.Service;
import java.io.*;
import java.util.*;
import java.net.URL;
import java.net.URLConnection;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import java.net.MalformedURLException;
import utility.TimeUtility;

/**
 *
 * @author aquil
 */
public class ServiceDAO {
    private static HashMap<String, ArrayList<Service>> serviceMap = new HashMap<String, ArrayList<Service>>();
    final private static int SMALLEST_CAPACITY = 825;
    private static ArrayList<Service> serviceList;

    //getServiceDetail()
    //<editor-fold defaultstate="collapsed" desc="getServiceDetail()">
    
    public static HashMap<String, ArrayList<Service>> getServiceDetail() throws MalformedURLException, IOException{
        //Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject jsonOutput = new JsonObject();
        URL blackbox = new URL("http://127.0.0.1:8080/getServiceDetail");
        URLConnection conn = blackbox.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        try (JsonReader jsonReader = new JsonReader(in)) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
                System.out.println("line 38: name = " + name);
                if (name.equals("Result")) {
                    serviceMap = readServiceDetail(jsonReader, serviceMap);
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
        return serviceMap;
    }
    
    public static HashMap<String, ArrayList<Service>> readServiceDetail(JsonReader jsonReader, HashMap<String, ArrayList<Service>> serviceMap) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String requestID = jsonReader.nextName();
            System.out.println(requestID);
            jsonReader.beginObject();
            int fuelRequired = 0;
            String locName = "";
            float[] location = new float[2];
            String mmsi = "";
            String timePosted = "";
            String timeRequested = "";
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
                System.out.println("name: " + name);
                if (name.equals("FuelRequired")){
                    fuelRequired = jsonReader.nextInt();
                    System.out.println("fuelRequired: " + fuelRequired);
                }
                if (name.equals("LocName")){
                    locName = jsonReader.nextString();
                }
                if (name.equals("Location")){
                    location = readLocation(jsonReader);
                }
                if (name.equals("MMSI")){
                    mmsi = jsonReader.nextString();
                }
                if (name.equals("TimePosted")){
                    timePosted = jsonReader.nextString();
                }
                if (name.equals("TimeRequested")){
                    timeRequested = jsonReader.nextString();
                }
                
            }
            //split up a single request into a few services based on the smallest fuel capacity
            int numServices = Math.floorDiv(fuelRequired, SMALLEST_CAPACITY);
            System.out.println("numServices: " + numServices);
            int fuelRequiredOfLastService = fuelRequired % SMALLEST_CAPACITY;
            System.out.println("fuelRequiredOfLastService: " + fuelRequiredOfLastService);
            
            ArrayList<Service> serviceList = new ArrayList<>();
            for (int i = 0; i < numServices; i++){
                Service service = new Service(mmsi, requestID, timeRequested, location, timePosted, SMALLEST_CAPACITY);
                serviceList.add(service);
            }
            Service lastService = new Service(mmsi, requestID, timeRequested, location, timePosted, fuelRequiredOfLastService);
            serviceList.add(lastService);
            serviceMap.put(requestID, serviceList);
            jsonReader.endObject();
        }
        jsonReader.endObject();
        return serviceMap;
    }
    
//</editor-fold>
    
    //helper methods
    //<editor-fold defaultstate="collapsed" desc="unfold to see readWarnings and readLocation methods">
    public static void readWarnings(JsonReader rd) throws IOException{
        ArrayList<String> warnings = new ArrayList<>();
        rd.beginArray();
        while (rd.hasNext()){
            String nextWarning = rd.nextString();
            warnings.add(nextWarning);
        }
        rd.endArray();
        
    }
    
    public static float[] readLocation(JsonReader rd) throws IOException{
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
//</editor-fold>
    
    //getServiceStatus()
    //<editor-fold defaultstate="collapsed" desc="getServiceStatus()">
    
    public static HashMap<String, ArrayList<Service>> getServiceStatus() throws MalformedURLException, IOException{
        System.out.println("getServiceStatus is called");
        URL blackbox = new URL("http://127.0.0.1:8080/getServiceStatus");
        URLConnection conn = blackbox.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        try (JsonReader jsonReader = new JsonReader(in)) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
                System.out.println("name = " + name);
                if (name.equals("Result")) {
                    
                    serviceMap = readServiceStatus(jsonReader);
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
        return serviceMap;
    }
    
    public static HashMap<String, ArrayList<Service>> readServiceStatus(JsonReader jsonReader) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String requestID = jsonReader.nextName();
            System.out.println(requestID);
            int fuelReceived = 0;
            String status = "";
            int timeElapsed = 0;
            int timeWaited = 0;
            jsonReader.beginObject();
            while(jsonReader.hasNext()){
                String name = jsonReader.nextName();
                System.out.println("name: " + name);
                if(name.equals("FuelReceived")){
                    System.out.println("FuelReceived: " + fuelReceived);
                    fuelReceived = jsonReader.nextInt();
                }
                if(name.equals("Status")){
                    System.out.println("status : " + status);
                    status = jsonReader.nextString();
                }
                if(name.equals("TimeElapsed")){
                    System.out.println("timeElapsed : " + timeElapsed);
                    timeElapsed = jsonReader.nextInt();
                }
                if(name.equals("TimeWaited")){
                    System.out.println("timeWaited : " + timeWaited);
                    timeWaited = jsonReader.nextInt();
                }
            }
            jsonReader.endObject();
            ArrayList<Service> serviceList = serviceMap.get(requestID);
            int numServicesWithFuelReceived = Math.floorDiv(fuelReceived, SMALLEST_CAPACITY);
            for (Service service: serviceList){
                service.setTimeDelay(timeWaited);
                service.setFuelReceived(fuelReceived);
                service.setStatus(status);
            }
            //Possible status: Pending, Waiting, Bunkering, Completed
//            for (Service service: serviceList){
//                String serviceStatus = service.getStatus();
//                Date startTime = TimeUtility.getServiceTime(s, startTime, fuelReceived)
//                if (serviceStatus.equals("Waiting") && ){
//                    service.setStatus("Bunkering");
//                    break;
//                }
//            }
            serviceMap.put(requestID, serviceList);
            
        }
        jsonReader.endObject();
        return serviceMap;
    }
    
//</editor-fold>
    
    //getServiceDetailByRequestID(String requestID)
    //<editor-fold defaultstate="collapsed" desc="getServiceDetailByRequestID(String requestID)">
    public static ArrayList<Service> getServiceDetailByRequestID(String requestID) throws IOException{
        System.out.println("getServiceDetailByRequestID(String requestID) is called");
        URL blackbox = new URL("http://localhost:8080/getServiceDetail?id=" + requestID);
        URLConnection conn = blackbox.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        try (JsonReader jsonReader = new JsonReader(in)) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
                System.out.println("name = " + name);
                if (name.equals("Result")) {
                    serviceList = readServiceDetailByRequestID(jsonReader, serviceList);
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
        return serviceList;
    }
    public static ArrayList<Service> readServiceDetailByRequestID(JsonReader jsonReader, ArrayList<Service> service) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String requestID = jsonReader.nextName();
            System.out.println(requestID);
            jsonReader.beginObject();
            int fuelRequired = 0;
            String locName = "";
            float[] location = new float[2];
            String mmsi = "";
            String timePosted = "";
            String timeRequested = "";
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
                System.out.println("name: " + name);
                if (name.equals("FuelRequired")){
                    fuelRequired = jsonReader.nextInt();
                    System.out.println("fuelRequired: " + fuelRequired);
                }
                if (name.equals("LocName")){
                    locName = jsonReader.nextString();
                }
                if (name.equals("Location")){
                    location = readLocation(jsonReader);
                }
                if (name.equals("MMSI")){
                    mmsi = jsonReader.nextString();
                }
                if (name.equals("TimePosted")){
                    timePosted = jsonReader.nextString();
                }
                if (name.equals("TimeRequested")){
                    timeRequested = jsonReader.nextString();
                }
                
            }
            jsonReader.endObject();
            serviceList = serviceMap.get(requestID);
            
            
        }
        jsonReader.endObject();
        return serviceList;
    }
    
//</editor-fold>
    
    //getServiceStatusByRequestID(String requestID)
    //<editor-fold defaultstate="collapsed" desc="getServiceStatusByRequestID(String requestID)">
    
    public static ArrayList<Service> getServiceStatusByRequestID(String requestID) throws IOException{
        System.out.println("getServiceStatusByRequestID(String requestID) is called");
        URL blackbox = new URL("http://localhost:8080/getServiceStatus?id=" + requestID);
        URLConnection conn = blackbox.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      
        try (JsonReader jsonReader = new JsonReader(in)) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
                System.out.println("name = " + name);
                if (name.equals("Result")) {
                    serviceList = readServiceStatusByRequestID(jsonReader, serviceList);
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
        return serviceList;
    }
    public static ArrayList<Service> readServiceStatusByRequestID(JsonReader jsonReader, ArrayList<Service> service) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String requestID = jsonReader.nextName();
            System.out.println(requestID);
            int fuelReceived = 0;
            String status = "";
            int timeElapsed = 0;
            int timeWaited = 0;
            jsonReader.beginObject();
            while(jsonReader.hasNext()){
                String name = jsonReader.nextName();
                System.out.println("name: " + name);
                if(name.equals("FuelReceived")){
                    System.out.println("FuelReceived: " + fuelReceived);
                    fuelReceived = jsonReader.nextInt();
                }
                if(name.equals("Status")){
                    System.out.println("status : " + status);
                    status = jsonReader.nextString();
                }
                if(name.equals("TimeElapsed")){
                    System.out.println("timeElapsed : " + timeElapsed);
                    timeElapsed = jsonReader.nextInt();
                }
                if(name.equals("TimeWaited")){
                    System.out.println("timeWaited : " + timeWaited);
                    timeWaited = jsonReader.nextInt();
                }
            }
            jsonReader.endObject();
            serviceList = serviceMap.get(requestID);
            
            
            
        }
        jsonReader.endObject();
        return serviceList;
    }
    
//</editor-fold>
}

    
