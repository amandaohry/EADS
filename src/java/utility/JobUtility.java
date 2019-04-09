/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import entity.Service;
import entity.Assignment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import static utility.TimeUtility.readWarnings;

/**
 *
 * @author aquil
 */
public class JobUtility {
    public static HashMap<String, Assignment> map = new HashMap<>();
    
    //getTravelTime?src=<Source>&dst=<Destination>&vsl=<MMSI>
    public static void sendJobs(String mmsi, String action, String destination, String departTime, int fuel) throws MalformedURLException, IOException{
        URL blackbox = new URL("http://127.0.0.1:8080/sendJobs");
        HttpURLConnection conn = (HttpURLConnection) blackbox.openConnection();
        conn.setRequestMethod("POST");
        
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        ArrayList<ArrayList<Assignment>> jobList = new ArrayList<>();
        try (JsonReader jsonReader = new JsonReader(in)) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
                System.out.println("name = " + name);
                if (name.equals("Result")) {
                    
                    jobList = readJobs(jsonReader, jobList);
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
    }
    public static void sendAssignList(JsonWriter jw){
        String action = "";
        String departTime = "";
        String destination = "";
        String mmsi = "";
        int fuel = 0;
    }
    

    public static ArrayList<ArrayList<Assignment>> readJobs(JsonReader jsonReader, ArrayList<ArrayList<Assignment>> jobList) throws IOException{
        jsonReader.beginObject();
        ArrayList<Assignment> cancelList = new ArrayList<>();
        ArrayList<Assignment> assignList = new ArrayList<>();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            System.out.println(name);
            if (name.equals("Cancel")){
                cancelList = readCancelList(jsonReader, cancelList);
                jobList.add(cancelList);
            }
            if (name.equals("Assign")){
                assignList = readAssignList(jsonReader, assignList);
                jobList.add(assignList);
            }
            
        }
        jsonReader.endObject();
        System.out.println("about to return the time");
        return jobList;
    }
    public static ArrayList<Assignment> readCancelList(JsonReader jsonReader, ArrayList<Assignment> cancelList) throws IOException{
        jsonReader.beginArray();
        String assignmentID = "";
        ArrayList<String> notices = new ArrayList<>();
        String status  = "";
        while (jsonReader.hasNext()) {
            jsonReader.beginObject();
            String name = jsonReader.nextName();
            System.out.println(name);
            if (name.equals("AssignmentID")){
                assignmentID = jsonReader.nextString();
            }
            if (name.equals("Notices")){
                notices= readNotices(jsonReader, notices);
            } 
            if (name.equals("Status")){
                status = jsonReader.nextString();
            } 
            jsonReader.endObject();
        }
        jsonReader.endArray();
        return cancelList;
    }
    public static ArrayList<Assignment> readAssignList(JsonReader jsonReader, ArrayList<Assignment> assignList) throws IOException{
        jsonReader.beginArray();
        String assignmentID = "";
        ArrayList<String> notices = new ArrayList<>();
        String status  = "";
        while (jsonReader.hasNext()) {
            jsonReader.beginObject();
            String name = jsonReader.nextName();
            System.out.println(name);
            if (name.equals("AssignmentID")){
                assignmentID = jsonReader.nextString();
            }
            if (name.equals("Notices")){
                notices= readNotices(jsonReader, notices);
            } 
            if (name.equals("Status")){
                status = jsonReader.nextString();
            } 
            jsonReader.endObject();
        }
        jsonReader.endArray();
        return assignList;
    }
    public static ArrayList<String> readNotices(JsonReader rd, ArrayList<String> notices) throws IOException{
        rd.beginArray();
        while(rd.hasNext()){
            notices.add(rd.nextString());
        }
        rd.endArray();
        return notices;
    }
    

}
