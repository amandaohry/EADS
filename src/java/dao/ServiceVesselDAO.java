/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.google.gson.stream.JsonReader;
import static dao.VesselDAO.vesselList;
import entity.ServiceVessel;
import entity.Vessel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aquil
 */
public class ServiceVesselDAO {
    private ServiceVessel serviceVessel;
    //private ArrayList<ServiceVessel> serviceVesselList;
    public ArrayList<ServiceVessel> serviceVesselList;
    public int smallestCapacity = 825;
    public HashMap<String, Vessel> map;

    public ServiceVesselDAO(){
        this.serviceVessel=null;
        this.serviceVesselList = new ArrayList<ServiceVessel>();
        this.map = new HashMap<>();
        //this.smallestCapacity = findSmallestCapacity();
    }
    //getServiceVesselDetail()
    //<editor-fold defaultstate="collapsed" desc="getServiceVesselDetail()">
//    public ArrayList<ServiceVessel> mapToList(ArrayList<ServiceVessel> serviceVesselList){
//    	for (Map.Entry<String, ServiceVessel> entry : serviceVesselList.entrySet()) {
//    	    String key = entry.getKey();
//    	    ServiceVessel value = entry.getValue();
//    	    serviceVessels.add(value);
//    	}
//        return serviceVessels;
//    }
//
    public ArrayList<ServiceVessel> getServiceVesselDetail() {
        try{
            URL blackbox = new URL("http://127.0.0.1:8080/getServiceVesselDetail");
            URLConnection conn = blackbox.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            try (JsonReader jsonReader = new JsonReader(in)) {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {

                    String name = jsonReader.nextName();
    //                System.out.println("name = " + name);
                    if (name.equals("Result")) {

                        serviceVesselList = readServiceVesselDetail(jsonReader, serviceVesselList);
                    }
                    if (name.equals("Status")){
                        String status = jsonReader.nextString();
                    }
                    if (name.equals("Warnings")){
                        readWarnings(jsonReader);
                    }
                }


                jsonReader.endObject();
            }
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return serviceVesselList;
    }

    public ArrayList<ServiceVessel> readServiceVesselDetail(JsonReader jsonReader, ArrayList<ServiceVessel> serviceVesselList) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String mmsi = jsonReader.nextName();
//            System.out.println(mmsi);
            jsonReader.beginObject();
            int capacity = 0;
            int flowRate = 0;

            while (jsonReader.hasNext()) {

                String name = jsonReader.nextName();
//                System.out.println("name: " + name);
                if (name.equals("Capacity")){
                    capacity = jsonReader.nextInt();
//                    System.out.println("capacity: " + capacity);
                }
                if (name.equals("FlowRate")){
                    flowRate = jsonReader.nextInt();
//                    System.out.println("flowRate: " + flowRate);
                }

            }
            ServiceVessel serviceVessel = new ServiceVessel(mmsi, capacity, flowRate);
            serviceVesselList.add(serviceVessel);
            jsonReader.endObject();
        }
        jsonReader.endObject();

        return serviceVesselList;
    }

//</editor-fold>

    //getServiceVesselStatus()
    //<editor-fold defaultstate="collapsed" desc="getServiceVesselStatus()">

    public ArrayList<ServiceVessel> getServiceVesselStatus() {
        try{
            URL blackbox = new URL("http://127.0.0.1:8080/getServiceVesselStatus");
            URLConnection conn = blackbox.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            try (JsonReader jsonReader = new JsonReader(in)) {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {

                    String name = jsonReader.nextName();
    //                System.out.println("name = " + name);
                    if (name.equals("Result")) {

                        serviceVesselList = readServiceVesselStatus(jsonReader, serviceVesselList);
                    }
                    if (name.equals("Status")){
                        String status = jsonReader.nextString();
                    }
                    if (name.equals("Warnings")){
                        readWarnings(jsonReader);
                    }
                }


                jsonReader.endObject();
            }
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return serviceVesselList;
    }

    public ArrayList<ServiceVessel> readServiceVesselStatus(JsonReader jsonReader, ArrayList<ServiceVessel> serviceVesselList) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String mmsi = jsonReader.nextName();
            System.out.println(mmsi);
            jsonReader.beginObject();
            int currentHold = 0;
            String status = "";

            while (jsonReader.hasNext()) {

                String name = jsonReader.nextName();
//                System.out.println("name: " + name);
                if (name.equals("CurrentHold")){
                    currentHold = jsonReader.nextInt();
//                    System.out.println("currentHold: " + currentHold);
                }
                if (name.equals("Status")){
                    status = jsonReader.nextString();
//                    System.out.println("status: " + status);
                }

            }
            for (ServiceVessel serviceVessel: serviceVesselList){
                serviceVessel.setCurrentCapacity(currentHold);
                serviceVessel.setStatus(status);
            }
            jsonReader.endObject();
        }
        jsonReader.endObject();

        return serviceVesselList;
    }

//</editor-fold>

    //getServiceVesselStatistics()
    //<editor-fold defaultstate="collapsed" desc="getServiceVesselStatistics()">

    public ArrayList<ServiceVessel> getServiceVesselStatistics(){
        try {
            URL blackbox = new URL("http://127.0.0.1:8080/getServiceVesselStatistics");
            URLConnection conn = blackbox.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            try (JsonReader jsonReader = new JsonReader(in)) {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {

                    String name = jsonReader.nextName();
    //                System.out.println("name = " + name);
                    if (name.equals("Result")) {

                        serviceVesselList = readServiceVesselStatistics(jsonReader, serviceVesselList);
                    }
                    if (name.equals("Status")){
                        String status = jsonReader.nextString();
                    }
                    if (name.equals("Warnings")){
                        readWarnings(jsonReader);
                    }
                }


                jsonReader.endObject();
            }
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return serviceVesselList;
    }

    public ArrayList<ServiceVessel> readServiceVesselStatistics(JsonReader jsonReader, ArrayList<ServiceVessel> serviceVesselList) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String mmsi = jsonReader.nextName();
            System.out.println(mmsi);
            jsonReader.beginObject();
            int distance = 0;
            int driftCost = 0;
            int driftTime = 0;
            int fuelDelivered = 0;
            int grossProfit = 0;
            float operCost = 0;

            while (jsonReader.hasNext()) {

                String name = jsonReader.nextName();
//                System.out.println("name: " + name);
                if (name.equals("Distance")){
                    distance = jsonReader.nextInt();
//                    System.out.println("distance: " + distance);
                }
                if (name.equals("DriftCost")){
                    driftCost = jsonReader.nextInt();
//                    System.out.println("driftCost: " + driftCost);
                }
                if (name.equals("DriftTime")){
                    driftTime = jsonReader.nextInt();
                    System.out.println("driftTime: " + driftTime);
                }
                if (name.equals("FuelDelivered")){
                    fuelDelivered = jsonReader.nextInt();
                    System.out.println("fuelDelivered: " + fuelDelivered);
                }
                if (name.equals("GrossProfit")){
                    grossProfit = jsonReader.nextInt();
//                    System.out.println("grossProfit: " + grossProfit);
                }
                if (name.equals("OperCost")){
                    operCost = (float) jsonReader.nextDouble();
//                    System.out.println("operCost: " + operCost);
                }


            }
            for (ServiceVessel serviceVessel: serviceVesselList){
                serviceVessel.setDistance(distance);
                serviceVessel.setDriftTime(driftTime);
                serviceVessel.setDriftCost(driftCost);
                serviceVessel.setFuelDelivered(fuelDelivered);
                serviceVessel.setGrossProfit(grossProfit);
                serviceVessel.setOperCost(operCost);
            }
            jsonReader.endObject();
        }
        jsonReader.endObject();

        return serviceVesselList;
    }

//</editor-fold>
//
//    //getServiceVesselDetailByMMSI()
//    //<editor-fold defaultstate="collapsed" desc="getServiceVesselDetailByMMSI()">
//
//    public ServiceVessel getServiceVesselDetailByMMSI(String mmsi) throws MalformedURLException, IOException{
//
//        URL blackbox = new URL("http://127.0.0.1:8080/getServiceVesselDetail?mmsi=" + mmsi);
//        URLConnection conn = blackbox.openConnection();
//        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//        try (JsonReader jsonReader = new JsonReader(in)) {
//            jsonReader.beginObject();
//            while (jsonReader.hasNext()) {
//
//                String name = jsonReader.nextName();
////                System.out.println("name = " + name);
//                if (name.equals("Result")) {
//
//                    serviceVessel = readServiceVesselDetailByMMSI(jsonReader, serviceVessel);
//                }
//                if (name.equals("Status")){
//                    String status = jsonReader.nextString();
//                }
//                if (name.equals("Warnings")){
//                    readWarnings(jsonReader);
//                }
//            }
//
//
//            jsonReader.endObject();
//        }
//        return serviceVessel;
//    }
//
//    public ServiceVessel readServiceVesselDetailByMMSI(JsonReader jsonReader, ServiceVessel serviceVessel) throws IOException{
//        jsonReader.beginObject();
//        while (jsonReader.hasNext()) {
//            String mmsi = jsonReader.nextName();
////            System.out.println(mmsi);
//            jsonReader.beginObject();
//            int capacity = 0;
//            int flowRate = 0;
//
//            while (jsonReader.hasNext()) {
//
//                String name = jsonReader.nextName();
////                System.out.println("name: " + name);
//                if (name.equals("Capacity")){
//                    capacity = jsonReader.nextInt();
////                    System.out.println("capacity: " + capacity);
//                }
//                if (name.equals("FlowRate")){
//                    flowRate = jsonReader.nextInt();
////                    System.out.println("flowRate: " + flowRate);
//                }
//
//            }
//            serviceVessel = serviceVesselList.get(mmsi);
//
//            jsonReader.endObject();
//        }
//        jsonReader.endObject();
//
//        return serviceVessel;
//    }
//
//
//
//    //getServiceVesselStatusByMMSI()
//    //<editor-fold defaultstate="collapsed" desc="getServiceVesselStatusByMMSI()">
//
//    public ServiceVessel getServiceVesselStatusByMMSI(String mmsi) throws MalformedURLException, IOException{
//
//        URL blackbox = new URL("http://127.0.0.1:8080/getServiceVesselStatus?mmsi=" + mmsi);
//        URLConnection conn = blackbox.openConnection();
//        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//        try (JsonReader jsonReader = new JsonReader(in)) {
//            jsonReader.beginObject();
//            while (jsonReader.hasNext()) {
//
//                String name = jsonReader.nextName();
////                System.out.println("name = " + name);
//                if (name.equals("Result")) {
//
//                    serviceVessel = readServiceVesselStatusByMMSI(jsonReader, serviceVessel);
//                }
//                if (name.equals("Status")){
//                    String status = jsonReader.nextString();
//                }
//                if (name.equals("Warnings")){
//                    readWarnings(jsonReader);
//                }
//            }
//
//
//            jsonReader.endObject();
//        }
//        return serviceVessel;
//    }
//
//    public ServiceVessel readServiceVesselStatusByMMSI(JsonReader jsonReader, ServiceVessel serviceVessel) throws IOException{
//        jsonReader.beginObject();
//        while (jsonReader.hasNext()) {
//            String mmsi = jsonReader.nextName();
//            System.out.println(mmsi);
//            jsonReader.beginObject();
//            int capacity = 0;
//            int flowRate = 0;
//
//            while (jsonReader.hasNext()) {
//
//                String name = jsonReader.nextName();
////                System.out.println("name: " + name);
//                if (name.equals("Capacity")){
//                    capacity = jsonReader.nextInt();
////                    System.out.println("capacity: " + capacity);
//                }
//                if (name.equals("FlowRate")){
//                    flowRate = jsonReader.nextInt();
////                    System.out.println("flowRate: " + flowRate);
//                }
//
//            }
//            serviceVessel = serviceVesselList.get(mmsi);
//
//            jsonReader.endObject();
//        }
//        jsonReader.endObject();
//
//        return serviceVessel;
//    }
//
//    //</editor-fold>
//
//    //getServiceVesselStatisticsByMMSI()
//    //<editor-fold defaultstate="collapsed" desc="getServiceVesselStatisticsByMMSI()">
//
//    public ServiceVessel getServiceVesselStatisticsByMMSI(String mmsi) throws MalformedURLException, IOException{
//
//        URL blackbox = new URL("http://127.0.0.1:8080/getServiceVesselStatistics?mmsi=" + mmsi);
//        URLConnection conn = blackbox.openConnection();
//        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//        try (JsonReader jsonReader = new JsonReader(in)) {
//            jsonReader.beginObject();
//            while (jsonReader.hasNext()) {
//
//                String name = jsonReader.nextName();
////                System.out.println("name = " + name);
//                if (name.equals("Result")) {
//
//                    serviceVessel = readServiceVesselStatisticsByMMSI(jsonReader, serviceVessel);
//                }
//                if (name.equals("Status")){
//                    String status = jsonReader.nextString();
//                }
//                if (name.equals("Warnings")){
//                    readWarnings(jsonReader);
//                }
//            }
//
//
//            jsonReader.endObject();
//        }
//        return serviceVessel;
//    }
//
//    public ServiceVessel readServiceVesselStatisticsByMMSI(JsonReader jsonReader, ServiceVessel serviceVessel) throws IOException{
//        jsonReader.beginObject();
//        while (jsonReader.hasNext()) {
//            String mmsi = jsonReader.nextName();
//            System.out.println(mmsi);
//            jsonReader.beginObject();
//            int capacity = 0;
//            int flowRate = 0;
//
//            while (jsonReader.hasNext()) {
//
//                String name = jsonReader.nextName();
////                System.out.println("name: " + name);
//                if (name.equals("Capacity")){
//                    capacity = jsonReader.nextInt();
////                    System.out.println("capacity: " + capacity);
//                }
//                if (name.equals("FlowRate")){
//                    flowRate = jsonReader.nextInt();
////                    System.out.println("flowRate: " + flowRate);
//                }
//
//            }
//            serviceVessel = serviceVesselList.get(mmsi);
//
//            jsonReader.endObject();
//        }
//        jsonReader.endObject();
//
//        return serviceVessel;
//    }
//
//    //</editor-fold>
//    //</editor-fold>

    //helper methods
    //<editor-fold defaultstate="collapsed" desc="helper methods">

    public void readWarnings(JsonReader rd) throws IOException{
        ArrayList<String> warnings = new ArrayList<>();
        rd.beginArray();
        while (rd.hasNext()){
            String nextWarning = rd.nextString();
            warnings.add(nextWarning);
        }
        rd.endArray();

    }


//</editor-fold>
    public HashMap<String, Vessel> getVesselDetail() throws MalformedURLException, IOException{
        
        URL blackbox = new URL("http://127.0.0.1:8080/getVesselDetail");
        URLConnection conn = blackbox.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
        
        try(JsonReader jsonReader = new JsonReader(in)){
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
                System.out.println("name = " + name);
                if (name.equals("Result")) {
                    
                    map = readVesselDetail(jsonReader, map);
                }
                if (name.equals("Status")){
                    String status = jsonReader.nextString();
                }
                if (name.equals("Warnings")){
                    readWarnings(jsonReader);
                }
                
            }
            jsonReader.endObject();

            return map;
        }
        
    }
    
    public HashMap<String, Vessel> readVesselDetail(JsonReader jsonReader, HashMap<String, Vessel> map) throws  IOException{
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
            map.put(mmsi, oceangoingVessel);
            jsonReader.endObject();
        }
        jsonReader.endObject();
        return map;
    }
    public HashMap<String, Vessel> getVesselStatus() throws MalformedURLException, IOException{
        
        URL blackbox = new URL("http://127.0.0.1:8080/getVesselStatus");
        URLConnection conn = blackbox.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
        
        try(JsonReader jsonReader = new JsonReader(in)){
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                
                String name = jsonReader.nextName();
                System.out.println("name = " + name);
                if (name.equals("Result")) {
                    
                    map = readVesselStatus(jsonReader, map);
                }
                if (name.equals("Status")){
                    String status = jsonReader.nextString();
                }
                if (name.equals("Warnings")){
                    readWarnings(jsonReader);
                }
                
            }
            jsonReader.endObject();
         
        }
        return map;
    }
    
    public HashMap<String, Vessel> readVesselStatus(JsonReader jsonReader, HashMap<String, Vessel> map) throws  IOException{
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
            for (ServiceVessel sv: serviceVesselList){
                if (mmsi.equals(sv.getMMSI())){
                    sv.setLocName(locName);
                    sv.setSpeed(speed);
                }
            }
            
            
            jsonReader.endObject();
        }
        jsonReader.endObject();
        return map;
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
