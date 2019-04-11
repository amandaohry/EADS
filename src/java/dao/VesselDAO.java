/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Vessel;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import java.net.MalformedURLException;
import java.util.*;

/**
 *
 * @author aquil
 */
public class VesselDAO {
    //public ArrayList<OceangoingVessel> ogvList;
    public static ArrayList<Vessel> vesselList;
    
    public VesselDAO(){
        this.vesselList = new ArrayList<>();
    }
    
    public ArrayList<Vessel> getVesselDetail() throws MalformedURLException, IOException{
        
        URL blackbox = new URL("http://127.0.0.1:8080/getVesselDetail");
        URLConnection conn = blackbox.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
        
        try(JsonReader jsonReader = new JsonReader(in)){
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
                System.out.println("name = " + name);
                if (name.equals("Result")) {
                    
                    vesselList = readOceanGoingVesselDetail(jsonReader, vesselList);
                }
                if (name.equals("Status")){
                    String status = jsonReader.nextString();
                }
                if (name.equals("Warnings")){
                    readWarnings(jsonReader);
                }
                jsonReader.endObject();
            }


            return vesselList;
        }
        
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
    
   public ArrayList<Vessel> getVesselStatus() throws MalformedURLException, IOException{
        
        URL blackbox = new URL("http://127.0.0.1:8080/getVesselStatus");
        URLConnection conn = blackbox.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
        
        try(JsonReader jsonReader = new JsonReader(in)){
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
                System.out.println("name = " + name);
                if (name.equals("Result")) {
                    
                    vesselList = readOceanGoingVesselDetail(jsonReader, vesselList);
                }
                if (name.equals("Status")){
                    String status = jsonReader.nextString();
                }
                if (name.equals("Warnings")){
                    readWarnings(jsonReader);
                }
                jsonReader.endObject();
        }
         
         
        return vesselList;
    }
        
        
    }
    
   
   
    public ArrayList<Vessel> readOceanGoingVesselDetail(JsonReader jsonReader, ArrayList<Vessel> vesselList) throws  IOException{
        jsonReader.beginObject();
        while(jsonReader.hasNext()){
            String mmsi = jsonReader.nextName();
            System.out.println(mmsi);
            jsonReader.beginObject();
            String vesselName = "";
            float beam = 0;
            float cruiseSpd = 0;
            float draft = 0;
            float lengthOfVessel = 0;
            int weight = 0;
            
            while(jsonReader.hasNext()){
                String name = jsonReader.nextName();
                System.out.println("name " + name);
                if(name.equals("Beam")){
                    beam = (float) jsonReader.nextDouble();
                }
                if(name.equals("CruiseSpd")){
                    cruiseSpd = (float) jsonReader.nextDouble();
                }
                if(name.equals("Draft")){
                    draft = (float) jsonReader.nextDouble();
                }
                if(name.equals("LOA")){
                    lengthOfVessel = (float) jsonReader.nextDouble();
                }
                if(name.equals("VslName")){
                    vesselName = jsonReader.nextString();
                }
                if(name.equals("Weight")){
                    weight = jsonReader.nextInt();
                }
            }
            Vessel oceangoingVessel  = new Vessel(mmsi, vesselName, beam, cruiseSpd, draft, lengthOfVessel, weight );
            vesselList.add(oceangoingVessel);
            jsonReader.endObject();
        }
        jsonReader.endObject();
        return vesselList;
    }
    
    public ArrayList<Vessel> readOceanGoingVesselStatus(JsonReader jsonReader, ArrayList<Vessel> vesselList) throws  IOException{
        jsonReader.beginObject();
        while(jsonReader.hasNext()){
            String mmsi = jsonReader.nextName();
            System.out.println(mmsi);
            jsonReader.beginObject();
            float[] location = new float[2];
            String locName = "";
            float speed = 0;
            float direction = 0;
            float eta = 0;
            String destination = "";
            String status = "";
            
            while(jsonReader.hasNext()){
                String name = jsonReader.nextName();
                System.out.println("name " + name);
                if(name.equals("Location")){
                    location = readLocation(jsonReader);
                }
                if(name.equals("LocName")){
                    locName = jsonReader.nextString();
                }
                if(name.equals("Speed")){
                    speed = (float) jsonReader.nextDouble();
                }
                if(name.equals("Direction")){
                    direction = (float) jsonReader.nextDouble();
                }
                if(name.equals("ETA")){
                    eta = (float)jsonReader.nextDouble();
                }
                if(name.equals("Destination")){
                    destination = jsonReader.nextString();
                }
                if(name.equals("Status")){
                    status = jsonReader.nextString();
                }
            }
            Vessel vessel  = new Vessel(mmsi, location, locName, speed, direction, eta, destination, status );
            vesselList.add(vessel);
            
            jsonReader.endObject();
        }
        jsonReader.endObject();
        return vesselList;
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
        System.out.println("-------------------");
        if (location==null){
            
            System.out.println("location is null!");
        } else {
            System.out.println("current location:");
            System.out.println(location[0]);
            System.out.println(location[1]);
        }
        System.out.println("-------------------");
        return location;
    }
}
