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
public class Service {
    //static variables
    public int mmsi;
    public String requestID;
    public String requestTime; //time of request
    public float[] location; //service location in longitude, latitude
    public String time; //requested service time: Ei
    public int requestedFuel; //bunker fuel requested in tons
    
    //dynamic information
    public float timeDelay; //estimated time delay
    public float fuelReceived; //bunker fuel received in tons
    public String status; //current status of servicing: "not started" / "waiting" / "on-going" / "completed"
    
    //constructor
    public Service(int mmsi, String requestID, String requestTime, float[] location, String time, int requestedFuel){
        this.mmsi = mmsi;
        this.requestID = requestID;
        this.requestTime = requestTime;
        this.location = location;
        this.time = time;
        this.requestedFuel = requestedFuel;
    }
    
    public Service(int mmsi, String requestID, String requestTime, float[] location, String time, int requestedFuel, float timeDelay, float fuelReceived, String status){
        this.mmsi = mmsi;
        this.requestID = requestID;
        this.requestTime = requestTime;
        this.location = location;
        this.time = time;
        this.requestedFuel = requestedFuel;
        this.timeDelay = timeDelay;
        this.fuelReceived = fuelReceived;
        this.status = status;
    }
    
    //getters
    public int getMMSI(){
        return this.mmsi;
    }
    public String getRequestID(){
        return this.requestID;
    }
    public String getRequestTime(){
        return this.requestTime;
    }
    public float[] getLocation(){
        return this.location;
    }
    public String getTime(){
        return this.time;
    }
    public int getRequestedFuel(){
        return this.requestedFuel;
    }
    public float getTimeDelay(){
        return this.timeDelay;
    }
    public float getFuelReceived(){
        return this.fuelReceived;
    }
    public String getStatus(){
        return this.status;
    }
    
    //setters
    public void setTimeDelay(float timeDelay){
        this.timeDelay = timeDelay;
    }
    public void setFuelReceived(float fuelReceived){
        this.fuelReceived = fuelReceived;
    }
    public void setStatus(String status){
        this.status = status;
    }
}
