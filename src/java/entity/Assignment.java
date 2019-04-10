/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;

/**
 *
 * @author aquil
 */
public class Assignment {
    public String assignmentID;
    public String action;
    public String departTime;
    public String destination;
    public String mmsi;
    //optional
    public int fuel;
    public ArrayList<String> notices;
    
    //constructors
    public Assignment(String action, String departTime, String destination, String mmsi){
        this.action = action;
        this.departTime = departTime;
        this.destination = destination;
        this.mmsi = mmsi;
    }
    //getters
    public String getAssignmentID(){
        return this.assignmentID;
    }
    public String getAction(){
        return this.action;
    }
    public String getDepartTime(){
        return this.departTime;
    }
    public String getDestination(){
        return this.destination;
    }
    public String getMMSI(){
        return this.mmsi;
    }
    public int getFuel(){
        return this.fuel;
    }
    //setters
    public void setAssignmentID(String assignmentID){
        this.assignmentID=assignmentID;
    }
    public void setAction(String action){
        this.action=action;
    }
    public void setDepartTime(String departTime){
        this.departTime=departTime;
    }
    public void setDestination(String destination){
        this.destination=destination;
    }
    public void setMMSI(String mmsi){
        this.mmsi=mmsi;
    }
    public void setFuel(int fuel){
        this.fuel=fuel;
    }
}
