/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author aquil
 */
public class ServiceVessel {
    //static information
//    public static int staticMmsi;
//    public static String vesselName;
//    public static float capacity;
//    public static float pumpRate;
//    public static float cruiseSpeed;
    public int mmsi;
    public String vesselName;
    public float capacity;
    public float pumpRate;
    public float cruiseSpeed;
    
    //dynamic information
    
    public float currentCapacity;
    public float[] location;
    public float currentSpeed;
    public float[] currentDestination;
    public float eta;
    public String status;
    
    //constructors
    
    public ServiceVessel(int mmsi, String vesselName, float capacity, float pumpRate, float cruiseSpeed){
        this.mmsi = mmsi;
        this.vesselName = vesselName;
        this.capacity = capacity;
        this.pumpRate = pumpRate;
        this.cruiseSpeed = cruiseSpeed;
    }
    
    public ServiceVessel(int mmsi, String vesselName, float capacity, float pumpRate, float cruiseSpeed, float currentCapacity, 
            float[] location, float currentSpeed, float[] currentDestination, float eta, String status ){
        this.mmsi = mmsi;
        this.vesselName = vesselName;
        this.capacity = capacity;
        this.pumpRate = pumpRate;
        this.cruiseSpeed = cruiseSpeed;
        this.currentCapacity = currentCapacity;
    }
    
    //getters
    //<editor-fold defaultstate="collapsed" desc="UNFOLD TO SEE GETTERS">
    public int getMMSI(){
        return this.mmsi;
    }
    public float getCapacity(){
        return this.capacity;
    }
    public float getPumpRate(){
        return this.pumpRate;
    }
    public float getCruiseSpeed(){
        return this.cruiseSpeed;
    }
    public float getCurrentCapacity(){
        return this.currentCapacity;
    }
    public float[] getLocation(){
        return this.location;
    }
    public float getCurrentSpeed(){
        return this.currentSpeed;
    }
    public float[] getCurrentDestination(){
        return this.currentDestination;
    }
    public float getEta(){
        return this.eta;
    }
    public String getStatus(){
        return this.status;
    }
//</editor-fold>
    
    //setters - you can only set dynamic attributes
    
    //<editor-fold defaultstate="collapsed" desc="UNFOLD TO SEE SETTERS">
    public void setCurrentCapacity(float currentCapacity){
        this.currentCapacity = currentCapacity;
    }
    public void setLocation(float[] location){
        this.location = location;
    }
    public void setCurrentSpeed(float currentSpeed){
        this.currentSpeed = currentSpeed;
    }
    public void setCurrentDestination(float[] currentDestination){
        this.currentDestination = currentDestination;
    }
    public void setEta(float eta){
        this.eta = eta;
    }
    public void setStatus(String status){
        this.status = status;
    }
//</editor-fold>
    
    
    
}
