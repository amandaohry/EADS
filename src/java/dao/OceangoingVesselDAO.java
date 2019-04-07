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
    
    public static ArrayList<OceangoingVessel> getVessels() throws MalformedURLException, IOException{
        ArrayList<OceangoingVessel> ogvList = new ArrayList<>();
        
        URL blackbox = new URL("http://127.0.0.1:8080/getVesselDetail");
        URLConnection conn = blackbox.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
        ArrayList<OceangoingVessel> oceanGoingVessel = new ArrayList<>();
        
        try(JsonReader jsonReader = new JsonReader(in)){
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
                System.out.println("name = " + name);
                if (name.equals("Result")) {
                    
                    oceanGoingVessel = readOceanGoingVesselDetail(jsonReader, oceanGoingVessel);
                }
                if (name.equals("Status")){
                    String status = jsonReader.nextString();
                }
                if (name.equals("Warnings")){
                    readWarnings(jsonReader);
                }
        }
         
         
        return ogvList;
    }
        
        
    }
    
    public ArrayList<OceangoingVessel> readOceanGoingVesselDetail(JsonReader jsonReader, ArrayList<OceangoingVessel> oceanGoingVessel) throws IOException{
        jsonReader.beginObject();
        return oceanGoingVessel;
    }
}
