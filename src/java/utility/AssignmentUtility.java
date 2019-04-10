/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import dao.*;
import entity.*;
import java.util.*;
import entity.Service;

/**
 *
 * @author aquil
 */
public class AssignmentUtility {
            /*
		pseudocode
		1. sort the service by requestedFuel
		2. sort the SV by currentHold
		3. 
		
		
		constraints
		1. maximum 2 SV assigned to 1 service
		
		variables
		1. waiting time = max(service time1 + travel time1, service time2 + travel time2) - (expected start time of service)
		2. travel time = travel time 1 + travel time 2
		3. service time = fuelReceived * pumpRate
		
		
		*/
    public static void assign(){
        ServiceDAO serviceDAO = new ServiceDAO();
        ServiceVesselDAO serviceVesselDAO = new ServiceVesselDAO();

        ArrayList<Service> serviceList = serviceDAO.getServiceDetail();
        serviceList = serviceDAO.mapToList(serviceDAO.getServiceStatus());
        
        ArrayList<ServiceVessel> serviceVesselList = serviceVesselDAO.getServiceVesselDetail();
        serviceVesselList = serviceVesselDAO.mapToList(serviceVesselDAO.getServiceVesselStatus());
        serviceVesselList = serviceVesselDAO.mapToList(serviceVesselDAO.getServiceVesselStatistics());

        Collections.sort(serviceList, new ServiceComparator());
        Collections.sort(serviceVesselList, new ServiceVesselComparator());
        
    }

    
}
class ServiceComparator implements Comparator<Service>{
    public int compare(Service s1, Service s2){
        int difference = -(s1.getRequestedFuel() - s2.getRequestedFuel());
        return difference;
    }
}
class ServiceVesselComparator implements Comparator<ServiceVessel>{
    public int compare(ServiceVessel s1, ServiceVessel s2) {
        return Double.compare(s1.getCurrentCapacity(), s2.getCurrentCapacity());
    }
}