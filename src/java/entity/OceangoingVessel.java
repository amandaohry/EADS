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
    public int mmsi;
    public String vesselName;
    
    //dynamic information
    public float[] location;
    public float currentSpeed;
    public float[] currentDestination;
    public float eta;
    public String status;
    
    //constructors
    public OceangoingVessel(int mmsi, String vesselName){
        this.mmsi = mmsi;
        this.vesselName = vesselName;
    }
    
    public OceangoingVessel(int mmsi, String vesselName, float[] location, float currentSpeed, float[] currentDestnation, float eta, String status){
        this.mmsi = mmsi;
        this.vesselName = vesselName;
        this.location = location;
        this.currentSpeed = currentSpeed;
        this.currentDestination = currentDestination;
        this.eta = eta;
        this.status = status;
    }
    
    //getters
    //<editor-fold defaultstate="collapsed" desc="UNFOLD TO SEE GETTERS">
    public int getMMSI(){
        return this.mmsi;
    }
    public String getVesselName(){
        return this.vesselName;
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
    
    //setters
    //<editor-fold defaultstate="collapsed" desc="UNFOLD TO SEE SETTERS">
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
