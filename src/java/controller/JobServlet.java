/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Assignment;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utility.JobUtility;
import utility.TimeUtility;

/**
 *
 * @author aquil
 */
@WebServlet(name = "JobServlet", urlPatterns = {"/JobServlet"})
public class JobServlet extends HttpServlet {

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
        Assignment a1 = new Assignment("Idle", "2022-12-12 15:12:12", "SEAE", "" );
        Assignment a2 = new Assignment("Resupply", "2022-12-12 15:13:13", "ASPLU", "");
        Assignment a3 = new Assignment("Idle","2022-12-12 15:16:16", "MRM", "");
        Assignment a4 = new Assignment("Resupply", "2022-12-12 15:15:15", "ASPLU", "");
        Assignment a5 = new Assignment("Resupply", "2022-12-12 15:17:17", "MRM", "");        
        ArrayList<Assignment> assignmentList = new ArrayList<>();
        assignmentList.add(a1);
        assignmentList.add(a2);
        assignmentList.add(a3);
        assignmentList.add(a4);
        assignmentList.add(a5);
        ArrayList<String> assignmentIDList = new ArrayList<String>();
        ArrayList<Assignment> travelTime = JobUtility.sendJobs(assignmentList, assignmentIDList);
        
        request.setAttribute("travelTime", travelTime); //with setAttribute() you can define a "key" and value pair so that you can get it in future using getAttribute("key")
        
        request.getRequestDispatcher("/index.jsp").forward(request, response);//RequestDispatcher is used to send the control to the invoked page.
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
