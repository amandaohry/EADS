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
    public String mmsi;
    public int capacity;
    public int pumpRate;
    public int refuelRate;
    
    //dynamic information
    
    public int currentCapacity;
    public String status;
    
    //service vessel statistics
    public int distance;
    public int driftCost;
    public int driftTime;
    public int fuelDelivered;
    public int grossProfit;
    public float operCost;
    
    //constructors
    public ServiceVessel(String mmsi, int capacity, int pumpRate){
        this.mmsi = mmsi;
        this.capacity = capacity;
        this.pumpRate = pumpRate;
        this.refuelRate = pumpRate*2;
    }
    
    
    //getters
    //<editor-fold defaultstate="collapsed" desc="UNFOLD TO SEE GETTERS">
    public String getMMSI(){
        return this.mmsi;
    }
    public int getCapacity(){
        return this.capacity;
    }
    public int getPumpRate(){
        return this.pumpRate;
    }
    public int getRefuelRate(){
        return this.refuelRate;
    }
    public float getCurrentCapacity(){
        return this.currentCapacity;
    }
    public String getStatus(){
        return this.status;
    }
    public int getDistance(){
        return this.distance;
    }
    public int getDriftTime(){
        return this.driftTime;
    }
    public int getDriftCost(){
        return this.driftCost;
    }
    public int getFuelDelivered(){
        return this.fuelDelivered;
    }
    public int getGrossProfit(){
        return this.grossProfit;
    }
    public float getOperCost(){
        return this.operCost;
    }
    //</editor-fold>
    
    //setters - you can only set dynamic attributes
    //<editor-fold defaultstate="collapsed" desc="UNFOLD TO SEE SETTERS">
    public void setCurrentCapacity(int currentCapacity){
        this.currentCapacity = currentCapacity;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public void setDistance(int distance){
        this.distance = distance;
    }
    public void setDriftCost(int driftCost){
        this.driftCost = driftCost;
    }
    public void setDriftTime(int driftTime){
        this.driftTime = driftTime;
    }
    public void setFuelDelivered(int fuelDelivered){
        this.fuelDelivered = fuelDelivered;
    }
    public void setGrossProfit(int grossProfit){
        this.grossProfit = grossProfit;
    }
    public void setOperCost(float operCost){
        this.operCost = operCost;
    }
//</editor-fold>
    
    
    
}
