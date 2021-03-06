/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ServiceVesselDAO;
import entity.ServiceVessel;
import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aquil
 */
@WebServlet(name = "ServiceVesselServlet", urlPatterns = {"/ServiceVesselServlet"})
public class ServiceVesselServlet extends HttpServlet {

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
        ServiceVesselDAO serviceVesselDAO = new ServiceVesselDAO();
        ArrayList<ServiceVessel> serviceVesselList = serviceVesselDAO.getServiceVesselDetail(); //Calling getServiceDetail() function

        
        ArrayList<ServiceVessel> serviceVesselList2 = serviceVesselDAO.getServiceVesselStatus(); //Calling getServiceStatus() function

        ArrayList<ServiceVessel> serviceVesselList3 = serviceVesselDAO.getServiceVesselStatistics(); //Calling getServiceStatus() function

        request.setAttribute("serviceVesselList", serviceVesselList);
        request.setAttribute("serviceVesselList2", serviceVesselList2);
        request.setAttribute("serviceVesselList3", serviceVesselList3);
        
        
        request.getRequestDispatcher("/ServiceVessel.jsp").forward(request, response);
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
