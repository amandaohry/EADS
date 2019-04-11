/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.google.gson.JsonArray;
import entity.Service;
import entity.ServiceVessel;

import java.io.*;
import java.util.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import java.net.MalformedURLException;
import utility.TimeUtility;

/**
 *
 * @author aquil
 */
public class ServiceDAO {

    public static SimpleDateFormat format = new SimpleDateFormat("YYYY-mm-dd HH:MM:SS");
    private HashMap<String, ArrayList<Service>> serviceMap;
    private int SMALLEST_CAPACITY = 825;
    private ArrayList<Service> services = new ArrayList<Service>();
    private ArrayList<Service> serviceList;//sub list (for internal usage)

    //getServiceDetail()
    //<editor-fold defaultstate="collapsed" desc="getServiceDetail()">
//    public ArrayList<Service> mapToList(HashMap<String, ArrayList<Service>> serviceMap){
//	services.add(new Service("0", "0", "2020-01-01 00:00:00", null, null, 0));//add depot service
//    	for (Map.Entry<String, ArrayList<Service>> entry : serviceMap.entrySet()) {
//    	    String key = entry.getKey();
//    	    ArrayList<Service> value = entry.getValue();
//    	    for (Service s: value) {
//    	    	services.add(s);
//    	    }
//            
//    	}
//        return services;
//    }
    public ServiceDAO(){
        this.serviceList=new ArrayList<>();
    }
    
    public ArrayList<Service> getServiceDetail() {
        try {
            URL blackbox = new URL("http://127.0.0.1:8080/getServiceDetail");
            URLConnection conn = blackbox.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            try (JsonReader jsonReader = new JsonReader(in)) {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {

                    String name = jsonReader.nextName();
                    //                System.out.println("line 38: name = " + name);
                    if (name.equals("Result")) {
                        serviceList = readServiceDetail(jsonReader);
                    }
                    if (name.equals("Status")) {
                        String status = jsonReader.nextString();
                    }
                    if (name.equals("Warnings")) {
                        readWarnings(jsonReader);
                    }
                }

                jsonReader.endObject();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("is services null? " + (services == null));
        return services;
    }

    public ArrayList<Service> readServiceDetail(JsonReader jsonReader) throws IOException {
        
        jsonReader.beginObject();
        Service service;
        while (jsonReader.hasNext()) {
            String requestID = jsonReader.nextName();

            jsonReader.beginObject();
            int fuelRequired = 0;
            String locName = "";
            float[] location = new float[2];
            String mmsi = "";
            String timePosted = "";
            String timeRequested = "";
            while (jsonReader.hasNext()) {

                String name = jsonReader.nextName();
                if (name.equals("FuelRequired")) {
                    fuelRequired = jsonReader.nextInt();

                }
                if (name.equals("LocName")) {
                    locName = jsonReader.nextString();
                }
                if (name.equals("Location")) {
                    location = readLocation(jsonReader);
                }
                if (name.equals("MMSI")) {
                    mmsi = jsonReader.nextString();
                }
                if (name.equals("TimePosted")) {
                    timePosted = jsonReader.nextString();
                }
                if (name.equals("TimeRequested")) {
                    timeRequested = jsonReader.nextString();
                }

            }
            service = new Service(mmsi, requestID, timeRequested, location, timePosted, fuelRequired);
            serviceList.add(service);

            jsonReader.endObject();
        }
        jsonReader.endObject();
        return serviceList;
    }

//</editor-fold>
    //helper methods
    //<editor-fold defaultstate="collapsed" desc="unfold to see readWarnings and readLocation methods">
    public void readWarnings(JsonReader rd) throws IOException {
        ArrayList<String> warnings = new ArrayList<>();
        rd.beginArray();
        while (rd.hasNext()) {
            String nextWarning = rd.nextString();
            warnings.add(nextWarning);
        }
        rd.endArray();

    }

    public float[] readLocation(JsonReader rd) throws IOException {
        float[] location = new float[2];
        rd.beginArray();
        int counter = 0;
        while (rd.hasNext()) {
            location[counter] = (float) rd.nextDouble();
            counter++;
        }
        rd.endArray();
        return location;
    }
//</editor-fold>

    //getServiceStatus()
    //<editor-fold defaultstate="collapsed" desc="getServiceStatus()">
    public ArrayList<Service> getServiceStatus() {

        try {
            URL blackbox = new URL("http://127.0.0.1:8080/getServiceStatus");
            URLConnection conn = blackbox.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            try (JsonReader jsonReader = new JsonReader(in)) {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {

                    String name = jsonReader.nextName();
                    System.out.println("name = " + name);
                    if (name.equals("Result")) {

                        serviceList = readServiceStatus(jsonReader);
                    }
                    if (name.equals("Status")) {
                        String status = jsonReader.nextString();
                    }
                    if (name.equals("Warnings")) {
                        readWarnings(jsonReader);
                    }
                }

                jsonReader.endObject();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serviceList;
    }

    public ArrayList<Service> readServiceStatus(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String requestID = jsonReader.nextName();
//            System.out.println(requestID);
            int fuelReceived = 0;
            String status = "";
            int timeElapsed = 0;
            int timeWaited = 0;
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();
//                System.out.println("name: " + name);
                if (name.equals("FuelReceived")) {
//                    System.out.println("FuelReceived: " + fuelReceived);
                    fuelReceived = jsonReader.nextInt();
                }
                if (name.equals("Status")) {
//                    System.out.println("status : " + status);
                    status = jsonReader.nextString();
                }
                if (name.equals("TimeElapsed")) {
//                    System.out.println("timeElapsed : " + timeElapsed);
                    timeElapsed = jsonReader.nextInt();
                }
                if (name.equals("TimeWaited")) {
//                    System.out.println("timeWaited : " + timeWaited);
                    timeWaited = jsonReader.nextInt();
                }
            }
            jsonReader.endObject();
            for (Service service : serviceList) {
                service.setTimeDelay(timeWaited);
                service.setFuelReceived(fuelReceived);
                service.setStatus(status);
            }

           

        }
        jsonReader.endObject();

        return serviceList;
    }

//</editor-fold>
    //getServiceDetailByRequestID(String requestID)
    //<editor-fold defaultstate="collapsed" desc="getServiceDetailByRequestID(String requestID)">
    public ArrayList<Service> getServiceDetailByRequestID(String requestID) {
        try {
            //        System.out.println("getServiceDetailByRequestID(String requestID) is called");
            URL blackbox = new URL("http://localhost:8080/getServiceDetail?id=" + requestID);
            URLConnection conn = blackbox.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            try (JsonReader jsonReader = new JsonReader(in)) {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {

                    String name = jsonReader.nextName();
                    //                System.out.println("name = " + name);
                    if (name.equals("Result")) {
                        serviceList = readServiceDetailByRequestID(jsonReader, serviceList);
                    }
                    if (name.equals("Status")) {
                        String status = jsonReader.nextString();
                    }
                    if (name.equals("Warnings")) {
                        readWarnings(jsonReader);
                    }
                }

                jsonReader.endObject();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serviceList;
    }

    public ArrayList<Service> readServiceDetailByRequestID(JsonReader jsonReader, ArrayList<Service> service) throws IOException {
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String requestID = jsonReader.nextName();
//            System.out.println(requestID);
            jsonReader.beginObject();
            int fuelRequired = 0;
            String locName = "";
            float[] location = new float[2];
            String mmsi = "";
            String timePosted = "";
            String timeRequested = "";
            while (jsonReader.hasNext()) {

                String name = jsonReader.nextName();
//                System.out.println("name: " + name);
                if (name.equals("FuelRequired")) {
                    fuelRequired = jsonReader.nextInt();
//                    System.out.println("fuelRequired: " + fuelRequired);
                }
                if (name.equals("LocName")) {
                    locName = jsonReader.nextString();
                }
                if (name.equals("Location")) {
                    location = readLocation(jsonReader);
                }
                if (name.equals("MMSI")) {
                    mmsi = jsonReader.nextString();
                }
                if (name.equals("TimePosted")) {
                    timePosted = jsonReader.nextString();
                }
                if (name.equals("TimeRequested")) {
                    timeRequested = jsonReader.nextString();
                }

            }
            jsonReader.endObject();
            serviceList = serviceMap.get(requestID);

        }
        jsonReader.endObject();
        return serviceList;
    }

//</editor-fold>
    //getServiceStatusByRequestID(String requestID)
    //<editor-fold defaultstate="collapsed" desc="getServiceStatusByRequestID(String requestID)">
    public ArrayList<Service> getServiceStatusByRequestID(String requestID) throws IOException {
//        System.out.println("getServiceStatusByRequestID(String requestID) is called");
        URL blackbox = new URL("http://localhost:8080/getServiceStatus?id=" + requestID);
        URLConnection conn = blackbox.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        try (JsonReader jsonReader = new JsonReader(in)) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {

                String name = jsonReader.nextName();
//                System.out.println("name = " + name);
                if (name.equals("Result")) {
                    serviceList = readServiceStatusByRequestID(jsonReader, serviceList);
                }
                if (name.equals("Status")) {
                    String status = jsonReader.nextString();
                }
                if (name.equals("Warnings")) {
                    readWarnings(jsonReader);
                }
            }

            jsonReader.endObject();
        }
        return serviceList;
    }

    public ArrayList<Service> readServiceStatusByRequestID(JsonReader jsonReader, ArrayList<Service> service) throws IOException {
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String requestID = jsonReader.nextName();
//            System.out.println(requestID);
            int fuelReceived = 0;
            String status = "";
            int timeElapsed = 0;
            int timeWaited = 0;
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();
//                System.out.println("name: " + name);
                if (name.equals("FuelReceived")) {
                    System.out.println("FuelReceived: " + fuelReceived);
                    fuelReceived = jsonReader.nextInt();
                }
                if (name.equals("Status")) {
                    System.out.println("status : " + status);
                    status = jsonReader.nextString();
                }
                if (name.equals("TimeElapsed")) {
//                    System.out.println("timeElapsed : " + timeElapsed);
                    timeElapsed = jsonReader.nextInt();
                }
                if (name.equals("TimeWaited")) {
                    System.out.println("timeWaited : " + timeWaited);
                    timeWaited = jsonReader.nextInt();
                }
            }
            jsonReader.endObject();
            serviceList = serviceMap.get(requestID);

        }
        jsonReader.endObject();
        return serviceList;
    }

//</editor-fold>
}
