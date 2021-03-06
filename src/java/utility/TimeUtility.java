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
import java.util.concurrent.TimeUnit;

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
    public static Date getCurrentTime(){
        String time = null; //default time;
        URL blackbox;
        BufferedReader in = null;
        Date dateTime = null;
		try {
			blackbox = new URL("http://127.0.0.1:8080/getCurrentTime");
		
	        URLConnection conn = blackbox.openConnection();
	        in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        try (JsonReader jsonReader = new JsonReader(in)) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
//                System.out.println("line 38: name = " + name);
                if (name.equals("Result")) {
                    
                    time = read(jsonReader, time);
//                    System.out.println("time: " + time);
                }
                if (name.equals("Status")){
                    jsonReader.nextString();
                }
                if (name.equals("Warnings")){
                    readWarnings(jsonReader);
                }
            }
                
            
            jsonReader.endObject();
        } catch (IOException e) {
			e.printStackTrace();
		}
		try {
			dateTime = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
       return dateTime;
    }
    
    
    
    //get serviceTime
//    public static int getServiceTime(int pumpRate, int fuelPumped){
//        int timeTakenInMinutes = fuelPumped / pumpRate * 60;
//        Date finishTime = addMinutes(startTime, timeTakenInMinutes);
//        return finishTime;
//    }
    
    //add minutes to a date
    public static Date addMinutes(Date startTime, int minutes){
        Date finishTime = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, minutes);
//        System.out.println("start time: " +  startTime);
        try {
            finishTime = format.parse(format.format(cal.getTime()));
        } catch (ParseException e){
            e.printStackTrace();
        }
//        System.out.println("finish time: " +  finishTime);
        return finishTime;
    }
    
    //getDateDiff(date1,date2,TimeUnit.MINUTES);
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
    
    //<editor-fold defaultstate="collapsed" desc="getCurrentTime helper methods">
    public static String read(JsonReader jsonReader, String time) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
//            System.out.println(name);
            if (name.equals("CurrentTime")){
                time = jsonReader.nextString();
//                System.out.println("real time: " + time);
                break;
            }
            
        }
        jsonReader.endObject();
//        System.out.println("about to return the time");
        return time;
    }
    
    public static void readWarnings(JsonReader rd) throws IOException{
        rd.beginArray();
        while (rd.hasNext()){
            rd.nextString();
        }
        rd.endArray();
        
    }
//</editor-fold>
    
    //getTravelTime?src=<Source>&dst=<Destination>&vsl=<MMSI>
    public static int getTravelTime(String source, String destination, String mmsi){
        URL blackbox;
        int minutes = 0;
        BufferedReader in = null;
		try {
			blackbox = new URL("http://127.0.0.1:8080/getTravelTime?src=" + source + "&dst=" + destination + "&vsl=" + mmsi);
		
	        URLConnection conn = blackbox.openConnection();
	        in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        try (JsonReader jsonReader = new JsonReader(in)) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
//                System.out.println("name = " + name);
                if (name.equals("Result")) {
                    
                    minutes = readTravelTime(jsonReader, minutes);
//                    System.out.println("time: " + minutes);
                }
                if (name.equals("Status")){
                    jsonReader.nextString();
                }
                if (name.equals("Warnings")){
                    readWarnings(jsonReader);
                }
            }
                
            
            jsonReader.endObject();
        } catch (IOException e) {
			e.printStackTrace();
		}
        return minutes;
    }
    
    public static int readTravelTime(JsonReader jsonReader, int minutes) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
//            System.out.println(name);
            if (name.equals("TravelTime")){
                minutes = jsonReader.nextInt();
//                System.out.println("travel time: " + minutes);
            } else {
                jsonReader.nextString();
            }
        }
        jsonReader.endObject();
//        System.out.println("about to return the time");
        return minutes;
    }
    
    public static int getTravelTimeBySpeed(float[] source, String destination, double speed){
        URL blackbox;
        int minutes = 0;
        String sourceToString = source[0] + "," + source[1];
        BufferedReader in = null;
		try {
			blackbox = new URL("http://127.0.0.1:8080/getTravelTime?src=" + sourceToString + "&dst=" + destination + "&spd=" + speed);
		
	        URLConnection conn = blackbox.openConnection();
	        in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        try (JsonReader jsonReader = new JsonReader(in)) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
//                System.out.println("name = " + name);
                if (name.equals("Result")) {
                    
                    minutes = readTravelTime(jsonReader, minutes);
//                    System.out.println("time: " + minutes);
                }
                if (name.equals("Status")){
                    jsonReader.nextString();
                }
                if (name.equals("Warnings")){
                    readWarnings(jsonReader);
                }
            }
                
            
            jsonReader.endObject();
        } catch (IOException e) {
			e.printStackTrace();
		}
        return minutes;
    }
}
