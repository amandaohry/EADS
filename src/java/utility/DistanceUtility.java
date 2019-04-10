/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import entity.Service;
import entity.ServiceVessel;

/**
 *
 * @author aquil
 */
public class DistanceUtility {
    //getdistanceBetweenTwoServices
    public static double getDistanceBetweenTwoLocations(float[] location1, float[] location2){
        float lat1 = location1[0];
        float lon1 = location1[1];
        float lat2 = location2[0];
        float lon2 = location2[1];
        
        System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, 'M') + " Miles\n");
        System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, 'K') + " Kilometers\n");
        System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, 'N') + " Nautical Miles\n");
        
        double distanceInKilometers = distance(lat1, lon1, lat2, lon2, 'K');
        System.out.println(distanceInKilometers + " Kilometers\n");
        return distanceInKilometers;
    }
    
    //<editor-fold defaultstate="collapsed" desc="distance methods">
    
    private static double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }
    
    /*::  This function converts decimal degrees to radians             :*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    
    /*::  This function converts radians to decimal degrees             :*/
    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    //</editor-fold>
}
