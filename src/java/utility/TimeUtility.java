package utility;


import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import entity.Service;
import entity.ServiceVessel;
import java.net.MalformedURLException;
import java.util.Calendar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aquil
 */
public class TimeUtility{
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//"2020-01-01 00:08:00"

    //getSimulationStartTime
    public static Date getSimulationStartTime(){
        Date date = new Date();
        try {
            date = format.parse("2020-01-01 00:00:00");
        } catch(ParseException e){
            e.printStackTrace();
        }
        return date;
    }
    
    //get SimpleDateFormat
    public static SimpleDateFormat getSimpleDateFormat(){
        return format;
    }
    
    //get the current time in the simulation
    public static Date getCurrentTime() throws FileNotFoundException, IOException, ParseException{
        URL blackbox = new URL("http://127.0.0.1:8080/getCurrentTime");
        URLConnection conn = blackbox.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        String time = ""; //default time;
        try (JsonReader jsonReader = new JsonReader(in)) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
                System.out.println("name = " + name);
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
        Date dateTime = format.parse(time);
       return dateTime;
    }
    
    
    
    //get serviceTime
    public static Date getServiceTime(ServiceVessel s, Date startTime, int fuelPumped){
        int pumpRate = s.getPumpRate();
        int timeTakenInMinutes = fuelPumped / pumpRate * 60;
        Date finishTime = addMinutes(startTime, timeTakenInMinutes);
        return finishTime;
    }
    
    //add minutes to a date
    public static Date addMinutes(Date startTime, int minutes){
        Date finishTime = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, minutes);
        System.out.println("start time: " +  startTime);
        try {
            finishTime = format.parse(format.format(cal.getTime()));
        } catch (ParseException e){
            e.printStackTrace();
        }
        System.out.println("finish time: " +  finishTime);
        return finishTime;
    }
    
    //<editor-fold defaultstate="collapsed" desc="getCurrentTime helper methods">
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
    
    public static void readWarnings(JsonReader rd) throws IOException{
        rd.beginArray();
        while (rd.hasNext()){
            System.out.println(rd.nextString());
        }
        rd.endArray();
        
    }
//</editor-fold>
    
    //getTravelTime?src=<Source>&dst=<Destination>&vsl=<MMSI>
    public static int getTravelTime(String source, String destination, String mmsi) throws MalformedURLException, IOException{
        URL blackbox = new URL("http://127.0.0.1:8080/getTravelTime?src=" + source + "&dst=" + destination + "&vsl=" + mmsi);
        URLConnection conn = blackbox.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        int minutes = 0;
        try (JsonReader jsonReader = new JsonReader(in)) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
                System.out.println("name = " + name);
                if (name.equals("Result")) {
                    
                    minutes = readTravelTime(jsonReader, minutes);
                    System.out.println("time: " + minutes);
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
        return minutes;
    }
    
    public static int readTravelTime(JsonReader jsonReader, int minutes) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            System.out.println(name);
            if (name.equals("TravelTime")){
                minutes = jsonReader.nextInt();
                System.out.println("travel time: " + minutes);
            }
        }
        jsonReader.endObject();
        System.out.println("about to return the time");
        return minutes;
    }
}
