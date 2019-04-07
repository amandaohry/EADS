/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.OceangoingVessel;
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
public class OceangoingVesselDAO {
    //public ArrayList<OceangoingVessel> ogvList;
    //public static ArrayList<OceangoingVessel> oceanGoingVessel;
    
    public ArrayList<OceangoingVessel> getVessels() throws MalformedURLException, IOException{
        ArrayList<OceangoingVessel> ogv = new ArrayList<>();
        
        URL blackbox = new URL("http://127.0.0.1:8080/getVesselDetail");
        URLConnection conn = blackbox.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
        ArrayList<OceangoingVessel> oceangoingVessel = new ArrayList<>();
        
        try(JsonReader jsonReader = new JsonReader(in)){
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
                System.out.println("name = " + name);
                if (name.equals("Result")) {
                    
                    oceangoingVessel = readOceanGoingVesselDetail(jsonReader, oceangoingVessel);
                }
                if (name.equals("Status")){
                    String status = jsonReader.nextString();
                }
                if (name.equals("Warnings")){
                    readWarnings(jsonReader);
                }
                jsonReader.endObject();
        }
         
         
        return ogv;
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
    
    public ArrayList<OceangoingVessel> readOceanGoingVesselDetail(JsonReader jsonReader, ArrayList<OceangoingVessel> oceanGoingVessel) throws  IOException{
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
            OceangoingVessel oceangoingVessel  = new OceangoingVessel(mmsi, vesselName, beam, cruiseSpd, draft, lengthOfVessel, weight );
            oceanGoingVessel.add(oceangoingVessel);
            jsonReader.endObject();
        }
        jsonReader.endObject();
        return oceanGoingVessel;
    }
}
