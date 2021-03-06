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
public class ServiceVessel extends Vessel  {
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
    
    //vessel status
    public String locName;
    public float speed;
    
    //constructors 
    public ServiceVessel(String mmsi, int capacity, int pumpRate){
        super(mmsi, capacity, pumpRate);
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
    public int getCurrentCapacity(){
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
    public String getLocName(){
        return locName;
    }
    public float getSpeed(){
        return speed;
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
    public void setLocName(String locName){
        this.locName=locName;
    }
    public void setSpeed(float speed){
        this.speed=speed;
    }
    
//</editor-fold>
    
    
    
}
