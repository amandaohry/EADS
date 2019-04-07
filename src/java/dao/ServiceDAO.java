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

/**
 *
 * @author aquil
 */
public class ServiceDAO {
    private static ArrayList<Service> serviceList;
    
    public ArrayList<Service> getServiceDetail() throws MalformedURLException, IOException{
         //Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject jsonOutput = new JsonObject();
        URL blackbox = new URL("http://127.0.0.1:8080/getServiceDetail");
        URLConnection conn = blackbox.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        ArrayList<Service> serviceList = new ArrayList<Service>();
        try (JsonReader jsonReader = new JsonReader(in)) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
                System.out.println("line 38: name = " + name);
                if (name.equals("Result")) {
                    
                    serviceList = read(jsonReader, serviceList);
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
        this.serviceList = serviceList;
       return serviceList;
    }
    
    //getServiceDetail helper methods
    //<editor-fold defaultstate="collapsed" desc="unfold to see getServiceDetail() helper methods">
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
    
    public ArrayList<Service> read(JsonReader jsonReader, ArrayList<Service> servicesList) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String requestID = jsonReader.nextName();
            System.out.println(requestID);
            jsonReader.beginObject();
            int fuelRequired = 0;
            String locName = "";
            float[] location = new float[2];
            int mmsi = 0;
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
                    mmsi = jsonReader.nextInt();
                }
                if (name.equals("TimePosted")){
                    timePosted = jsonReader.nextString();
                }
                if (name.equals("TimeRequested")){
                    timeRequested = jsonReader.nextString();
                }
                
            }
            Service service = new Service(mmsi, requestID, timeRequested, location, timePosted, fuelRequired);
            servicesList.add(service);
            jsonReader.endObject();
        }
        jsonReader.endObject();
        return servicesList;
    }
    
//</editor-fold>
    
 
}

    
