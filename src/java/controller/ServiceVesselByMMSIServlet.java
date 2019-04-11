///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package controller;
//
//import dao.ServiceDAO;
//import dao.ServiceVesselDAO;
//import entity.Service;
//import entity.ServiceVessel;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.HashMap;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// *
// * @author aquil
// */
//@WebServlet(name = "ServiceVesselByMMSIServlet", urlPatterns = {"/ServiceVesselByMMSIServlet"})
//public class ServiceVesselByMMSIServlet extends HttpServlet {
//
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String mmsi = request.getParameter("mmsi");
//        ServiceVesselDAO = new ServiceVesselDAO();
//        ServiceVessel serviceVessel = .getServiceVesselDetailByMMSI(mmsi); //Calling getServiceDetail() function
//
//        ServiceVessel serviceVessel2 = ServiceVesselDAO.getServiceVesselStatusByMMSI(mmsi); //Calling getServiceStatus() function
//
//        ServiceVessel serviceVessel3 = ServiceVesselDAO.getServiceVesselStatisticsByMMSI(mmsi); //Calling getServiceStatus() function
//
//
//        request.setAttribute("serviceVessel", serviceVessel);
//        request.setAttribute("serviceVessel2", serviceVessel2);
//        request.setAttribute("serviceVessel3", serviceVessel3);
//        
//        
//        request.getRequestDispatcher("/ServiceVesselByMMSI.jsp").forward(request, response);
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
