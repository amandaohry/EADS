/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ServiceDAO;
import dao.ServiceVesselDAO;
import entity.*;
import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utility.AssignmentUtility;
import static utility.AssignmentUtility.nextServiceBatch;
import static utility.AssignmentUtility.result;
import utility.DistanceUtility;
import utility.TimeUtility;

/**
 *
 * @author aquil
 */
@WebServlet(name = "AssignmentServlet", urlPatterns = {"/AssignmentServlet"})
public class AssignmentServlet extends HttpServlet {
    public static String[] result;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        HashMap<Service, ArrayList<Vessel>> serviceAndVesselMap = AssignmentUtility.assign();
//        request.setAttribute("serviceAndVesselMap", serviceAndVesselMap); //with setAttribute() you can define a "key" and value pair so that you can get it in future using getAttribute("key")
//        
//        request.getRequestDispatcher("/assignment.jsp").forward(request, response);//RequestDispatcher is used to send the control to the invoked page.
        
        

        ServiceDAO serviceDAO = new ServiceDAO();
        ServiceVesselDAO serviceVesselDAO = new ServiceVesselDAO();
        
        
                
        ArrayList<Service> serviceList = serviceDAO.getServiceDetail();
        serviceList = serviceDAO.getServiceStatus();
        
        
        if (nextServiceBatch !=null){
            for(Service s: nextServiceBatch){
                serviceList.add(s);
            }
        }
        
        ArrayList<ServiceVessel> serviceVesselList = serviceVesselDAO.getServiceVesselDetail();
        serviceVesselList = serviceVesselDAO.getServiceVesselStatus();
        serviceVesselList = serviceVesselDAO.getServiceVesselStatistics();
        HashMap<String, Vessel> map = serviceVesselDAO.getVesselDetail();
        map = serviceVesselDAO.getVesselStatus();

        Collections.sort(serviceList, new ServiceComparator());
        Collections.sort(serviceVesselList, new ServiceVesselComparator());


        HashMap<Service, ArrayList<ServiceVessel>> serviceAndVesselMap = new HashMap<>();
        nextServiceBatch = new ArrayList<>();
        //index of service vessels in the arraylist
        int count = 0;
        for (int i = 0; i < serviceList.size(); i++) {
            //get the demand for each service
            int serviceAmount = serviceList.get(i).getRequestedFuel();
            
            //initialise the amount we provide to the service request
            int currentSum = 0;
            
            //initialise the arraylist of vessels assigned to this service request
            ArrayList<ServiceVessel> vesselListForOneService = new ArrayList<>();
            
            //for each unassigned service vessel
            for (int j = count; j < serviceVesselList.size(); j++) {
                //get amount of fuel in the current hold of the SV
                currentSum = serviceVesselList.get(j).getCurrentCapacity();
                
                //if a SV can fulfil the request
                if (currentSum >= serviceAmount) {
                    //add the SV to the arraylist of service vessels for this service i
                    vesselListForOneService.add(serviceVesselList.get(j));
                    //put the <service, arraylist<vessel>> into the hashmap of assignments
                    serviceAndVesselMap.put(serviceList.get(i), vesselListForOneService);
                    //increase the index of SV bc we just assigned one SV
                    count++;
                    //break because req is alr fulfilled
                    break;
                //else, if this SV cannot fulfill the request
                } else {
                    //if this SV is not the last SV
                    if (j < serviceVesselList.size() - 1) {
                        //see whether the currently provided fuel + the next vessel's provided fuel will exceed the service amt
                        if (currentSum + serviceVesselList.get(j + 1).getCurrentCapacity() >= serviceAmount) {
                            //add this SV and the next, then put into map
                            vesselListForOneService.add(serviceVesselList.get(j));
                            vesselListForOneService.add(serviceVesselList.get(j + 1));
                            serviceAndVesselMap.put(serviceList.get(i), vesselListForOneService);
                            //increase count by 2 bc we assigned 2 SV
                            count = count + 2;
                            break;
                        } else {
                            //if unassigned, add to next batch
                            nextServiceBatch.add(serviceList.get(i));
                            break;
                        }
                    } else {
                        //if unassigned, add to next batch
                        nextServiceBatch.add(serviceList.get(i));
                        break;
                    }
                }
            }
        }
        
        //calculate total travel time
        double totalTravelTime = 0;
        Date currentTime = TimeUtility.getCurrentTime();

        for (Service service : serviceAndVesselMap.keySet()) {
            String sRequestID = service.getRequestID();
            ArrayList<ServiceVessel> vList = serviceAndVesselMap.get(service);
            for (int i = 0; i < vList.size(); i++) {
                ServiceVessel v = vList.get(i);
                String vLocation = v.getLocName();
                //float[] vLocation = v.getLocation();
                String mmsi = v.getMMSI();
                double distance = TimeUtility.getTravelTime(sRequestID, vLocation, mmsi);
                //double distance = DistanceUtility.getDistanceBetweenTwoLocations(sLocation, vLocation);
                totalTravelTime = totalTravelTime + distance / (v.getSpeed() / 60);
            }
        }
        
        //calculate total waiting time
        int totalWaitingTime = 0;
        for (Service service : serviceAndVesselMap.keySet()) {
            
            //get expected start time of service (Ej)
            Date expectedStartTime = new Date();
            try {
                expectedStartTime = (TimeUtility.format).parse(service.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
                
            String sRequestID = service.getRequestID();
            ArrayList<ServiceVessel> vList = serviceAndVesselMap.get(service);
            //if only one vessel is servicing this request
            if (vList.size() == 1) {
                ServiceVessel v = vList.get(0);
                String vLocation = v.getLocName();
                String mmsi = v.getMMSI();
                int distance = (int) Math.round(TimeUtility.getTravelTime(sRequestID, vLocation, mmsi));
                int travelTime = Math.round(distance / (v.getSpeed() / 60));

                //get arrival time of service vessel (Bj)
                Date arrivalTime = TimeUtility.addMinutes(currentTime, travelTime);

                int amount = service.getRequestedFuel();
                ServiceVessel sv = (ServiceVessel) v;
                int pumpRate = sv.getPumpRate();
                int timeTakenRefuelMinutes = (amount / pumpRate) * 60;
                
                //get end time of service (Lj)
                Date serviceEndTime = TimeUtility.addMinutes(arrivalTime, timeTakenRefuelMinutes);
                

                //waiting time = Lj - Ej
                int waitingTime = Math.round(TimeUtility.getDateDiff(expectedStartTime, serviceEndTime ,TimeUnit.MINUTES));
                totalWaitingTime += waitingTime;

            }
            if (vList.size() == 2) {
                ServiceVessel sv1 = vList.get(0);
                ServiceVessel sv2 = vList.get(1);
                
                String v1Location = sv1.getLocName();
                String mmsi1 = sv1.getMMSI();
                String v2Location = sv2.getLocName();
                String mmsi2 = sv2.getMMSI();
                
                int distance1 = (int) Math.round(TimeUtility.getTravelTime(sRequestID, v1Location, mmsi1));
                int distance2 = (int) Math.round(TimeUtility.getTravelTime(sRequestID, v2Location, mmsi2));

                int travelTime1 = Math.round(distance1 / (sv1.getSpeed() / 60));
                int travelTime2 = Math.round(distance2 / (sv2.getSpeed() / 60));

                Date arrivalTime1 = TimeUtility.addMinutes(currentTime, travelTime1);
                Date arrivalTime2 = TimeUtility.addMinutes(currentTime, travelTime2);

                int amount = service.getRequestedFuel();

                int pumpRate1 = sv1.getPumpRate();
                int pumpRate2 = sv2.getPumpRate();

                int timeTakenRefuelMinutes1 = (amount / pumpRate1) * 60;
                int timeTakenRefuelMinutes2 = (amount / pumpRate2) * 60;

                Date serviceEndTime1 = TimeUtility.addMinutes(arrivalTime1, timeTakenRefuelMinutes1);
                Date serviceEndTime2 = TimeUtility.addMinutes(arrivalTime2, timeTakenRefuelMinutes2);

                Date actualEndTime = new Date();
                if (serviceEndTime1.after(serviceEndTime2)){
                    actualEndTime = serviceEndTime1;
                } else {
                    actualEndTime = serviceEndTime2;
                }
                //waiting time = Lj - Ej
                int waitingTime = Math.round(TimeUtility.getDateDiff(expectedStartTime, actualEndTime ,TimeUnit.MINUTES));
                totalWaitingTime += waitingTime;

            }
        }
        result = new String[2];
        result[0] = "The total travel time is " + totalTravelTime;
        result[1] = "The total waiting time is " + totalWaitingTime;
        request.setAttribute("serviceAndVesselMap", serviceAndVesselMap);
        request.getRequestDispatcher("/assignment.jsp").forward(request, response);//RequestDispatcher is used to send the control to the invoked page.
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
class ServiceComparator implements Comparator<Service> {

    public int compare(Service s1, Service s2) {
        int difference = (s1.getRequestedFuel() - s2.getRequestedFuel());
        return difference;
    }
}

class ServiceVesselComparator implements Comparator<ServiceVessel> {

    public int compare(ServiceVessel s1, ServiceVessel s2) {
        return Double.compare(s1.getCurrentCapacity(), s2.getCurrentCapacity());
    }
}