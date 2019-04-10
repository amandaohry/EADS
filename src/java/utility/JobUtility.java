/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import entity.Service;
import entity.Assignment;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
    public static ArrayList<Assignment> sendJobs(ArrayList<Assignment> assignmentList, ArrayList<String> assignmentIDList) throws MalformedURLException, IOException{
        ArrayList<Assignment> assignments = new ArrayList<>();
        URL blackbox = new URL("http://127.0.0.1:8080/sendJobs");
        HttpURLConnection conn = (HttpURLConnection) blackbox.openConnection();
        conn.setRequestMethod("POST");
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        JsonObject json = new JsonObject();
        try (JsonWriter writer = new JsonWriter(out)) {
                writer.setIndent("    ");
                writer.beginObject();
                writer.name("Cancel");
                writeCancelList(writer, assignmentIDList);
                writer.name("Assign");
                writeAssignList(writer, assignmentList);
                writer.endObject();
                writer.close();
                System.out.println(writer.isHtmlSafe());
        }
        
        return assignments;
    }
    
    public static void writeCancelList(JsonWriter writer, ArrayList<String> assignmentIDList) throws IOException {
        writer.beginArray();
        for (String assignmentID: assignmentIDList){
            writer.beginObject();
            writer.name("AssignmentID");
            writer.value(assignmentID);
            writer.endObject();
            writer.endObject();
        }
        writer.endArray();
   }
    public static void writeAssignList(JsonWriter writer, ArrayList<Assignment> assignmentList) throws IOException {
        
        writer.beginArray();
        for (Assignment asst: assignmentList){
            writer.beginObject();
            writer.name("MMSI");
            writer.value(asst.getMMSI());
            writer.name("Action");
            writer.value(asst.getAction());
            writer.name("Destination");
            writer.value(asst.getDestination());
            writer.name("DepartTime");
            writer.value(asst.getDepartTime());
            if (asst.getFuel()!=0){
                writer.name("fuel");
                writer.value(asst.getFuel());
            }
            writer.endObject();
        }
        writer.endArray();
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
