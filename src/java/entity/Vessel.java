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
public class Vessel {
    //static information
    public String mmsi;
    public float Beam;
    public float CruiseSpd;
    public float Draft;
    public float LengthOfVessel;
    public String vesselName;
    public int Weight;
    public float[] location;
    public String locName;
    public float speed;
    public float direction;
    public float ETA;
    public String destination;
    public String status;
    public int capacity;
    public int pumpRate;
    public int refuelRate;
    
    //constructors
    public Vessel(String mmsi, String vesselName){
        this.mmsi = mmsi;
        this.vesselName = vesselName;
    }
    
    public Vessel(String mmsi, String vesselName, float Beam,  float CruiseSpd, float Draft, float LengthOfVessel, int Weight){
        this.mmsi = mmsi;
        this.vesselName = vesselName;
        this.Beam = Beam;
        this.CruiseSpd = CruiseSpd;
        this.Draft = Draft;
        this.LengthOfVessel = LengthOfVessel;
        this.Weight = Weight;
        
    }
    
    public Vessel(String mmsi, float[] location, String locName, float speed, float direction, float ETA, String destination, String status){
        this.mmsi = mmsi;
        this.location = location;
        this.locName = locName;
        this.speed = speed;
        this.direction = direction;
        this.ETA = ETA;
        this.destination = destination;
        this.status = status;
    }
    
    public Vessel(String mmsi, int capacity, int pumpRate){
        this.mmsi = mmsi;
        this.capacity = capacity;
        this.pumpRate = pumpRate;
        this.refuelRate = pumpRate*2;
    }
    
    //getters for vessel status
    public float[] getLocation(){
        return this.location;
    }
    public String getLocName(){
        return this.locName;
    }
    public float getSpeed(){
        return this.speed;
    }
    public float direction(){
        return this.direction;
    }
    public float getETA(){
        return this.ETA;
    }
    public String getDestination(){
        return this.destination;
    }
    public String getStatus(){
        return this.status;
    }
    
    //getters
    //<editor-fold defaultstate="collapsed" desc="UNFOLD TO SEE GETTERS">
    public String getMMSI(){
        return this.mmsi;
    }
    public String getVesselName(){
        return this.vesselName;
    }
   public float getBeam(){
        return this.Beam;
    }
    
    public float getCruiseSpd(){
        return this.CruiseSpd;
    }
   
    public float getDraft(){
        return this.Draft;
    }
    public float getLengthOfVessel(){
        return this.LengthOfVessel;
    }
     public float getWeight(){
        return this.Weight;
    }
    
//</editor-fold>
    
    //setters
    //<editor-fold defaultstate="collapsed" desc="UNFOLD TO SEE SETTERS">
   
   
 
//</editor-fold>
}
