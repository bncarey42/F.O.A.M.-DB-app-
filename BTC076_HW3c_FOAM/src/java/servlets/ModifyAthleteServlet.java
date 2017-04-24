/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.Roster;
import edu.saintpaul.csci2466.foam.model.Athlete;
import edu.saintpaul.csci2466.foam.roster.RosterException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import model.AthleteBean;
import model.Validator;

/**
 *
 * @author ben
 */
public class ModifyAthleteServlet extends HttpServlet {

    @Resource(name = "FOAMDB")
    private DataSource FOAMDB;

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

        String action = request.getParameter("action");
        String forwardTo = "/initRoster";
        String message = request.getParameter("msg");//message needs to be passed to jsp's not happening yet
        if (!action.equals("Cancel")) {
            try {
                Roster roster = Roster.getInstance();

                //1. get request
                //set local variables of request to 
                String nationalID = request.getParameter("nationalID");
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");

                if (Validator.isValidNatID(nationalID)
                        && Validator.isValidName(firstName, lastName)) {
                    
                    AthleteBean athlete = new AthleteBean();
                    athlete.setNationalID(request.getParameter("nationalID").toUpperCase());
                    athlete.setFirstName(request.getParameter("firstName"));
                    athlete.setLastName(request.getParameter("lastName"));
                    athlete.setDateOfBirth(Validator.convertDOB(request.getParameter("dateOfBirth")));

                    try {
                        switch (action) {
                            case "Add":
                                roster.newAthlete(athlete);
                                break;
                            case "Edit":

                                roster.update(athlete);
                                break;
                            case "Delete":

                                roster.delete(athlete);
                                break;
                            default:
                                break;
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(ModifyAthleteServlet.class.getName()).log(Level.SEVERE, null, ex);

                    }

                    forwardTo = "/initRoster";

                } else {
                    forwardTo = "/ModifyAthlete.jsp";
                    
                    request.setAttribute("errMsg", Validator.getErrMsg());

                }
            } catch (RosterException ex) {
                Logger.getLogger(ModifyAthleteServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            request.setAttribute("message", message);
        }
        //5. pass control
            request.getRequestDispatcher(forwardTo).forward(request, response);
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
