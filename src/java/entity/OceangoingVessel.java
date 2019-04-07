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
public class OceangoingVessel {
    //static information
    public String mmsi;
    public float Beam;
    public float CruiseSpd;
    public float Draft;
    public float LengthOfVessel;
    public String vesselName;
    public int Weight;
    
    
    //constructors
    public OceangoingVessel(String mmsi, String vesselName){
        this.mmsi = mmsi;
        this.vesselName = vesselName;
    }
    
    public OceangoingVessel(String mmsi, String vesselName, float Beam,  float CruiseSpd, float Draft, float LengthOfVessel, int Weight){
        this.mmsi = mmsi;
        this.vesselName = vesselName;
        this.Beam = Beam;
        this.CruiseSpd = CruiseSpd;
        this.Draft = Draft;
        this.LengthOfVessel = LengthOfVessel;
        this.Weight = Weight;
        
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
