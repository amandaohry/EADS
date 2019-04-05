/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

//import java.net.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
/**
 *
 * @author aquil
 */
public class CurrentTimeDAO {
    private String currentTime;
    
    public String getCurrentTime() throws FileNotFoundException, IOException{
        //Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject jsonOutput = new JsonObject();
        URL blackbox = new URL("http://127.0.0.1:8080/getCurrentTime");
        URLConnection conn = blackbox.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        String time = ""; //default time;
        try (JsonReader jsonReader = new JsonReader(in)) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
                System.out.println("line 38: name = " + name);
                if (name.equals("Result")) {
                    
                    time = read(jsonReader, time);
                    System.out.println("time: " + time);
                }
                if (name.equals("Status")){
                    System.out.println(jsonReader.nextString());
                }
                if (name.equals("Warnings")){
                    readWarnings(jsonReader);
                }
            }
                
            
            jsonReader.endObject();
        }
       return time;
    }
    
    public static String read(JsonReader jsonReader, String time) throws IOException{
        jsonReader.beginObject();
         while (jsonReader.hasNext()) {
             String name = jsonReader.nextName();
             System.out.println(name);
             if (name.equals("CurrentTime")){
                time = jsonReader.nextString();
                System.out.println("real time: " + time);
                break;
             }

         }
         jsonReader.endObject();
         System.out.println("about to return the time");
        return time;
    }
    
    public void readWarnings(JsonReader rd) throws IOException{
        rd.beginArray();
        while (rd.hasNext()){
            System.out.println(rd.nextString());
        }
        rd.endArray();
        
    }
//    
//    public Time getCurrentTime() throws IOException {
         //creating gson object for output 
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        JsonObject jsonOutput = new JsonObject();
//        URL blackbox = new URL("http://127.0.0.1:8080/getCurrentTime");
//        URLConnection conn = blackbox.openConnection();
//        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
////        String inputLine;
////        
////        while ((inputLine = in.readLine()) != null) 
////            
////            System.out.println(inputLine);
////            
////        in.close();
////        return inputLine;
//        Time output = new Time("2000-01-01 00:00:01"); //default time
//        //String output = "";
//        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
//
//			// Convert JSON to Java Object
//            //output = gson.fromJson(in, Time.class);
//            //System.out.println(output.getTime());
//            
//            // Convert JSON to JsonElement, and later to String
//            JsonElement json = gson.fromJson(in, JsonElement.class);
//            String jsonInString = gson.toJson(json);
//            System.out.println(jsonInString);
//            output= gson.fromJson(jsonInString, Time.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return output;
//    }
}
