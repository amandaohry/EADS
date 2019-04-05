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
import java.net.MalformedURLException;
import java.util.*;

/**
 *
 * @author aquil
 */
public class OceangoingVesselDAO {
    //public ArrayList<OceangoingVessel> ogvList;
    
    public static ArrayList<OceangoingVessel> getVessels() throws MalformedURLException, IOException{
        ArrayList<OceangoingVessel> ogvList = new ArrayList<>();
        
        URL blackbox = new URL("http://127.0.0.1:8080/getVesselDetail");
        URLConnection conn = blackbox.openConnection();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
        }
        return ogvList;
    }
}
