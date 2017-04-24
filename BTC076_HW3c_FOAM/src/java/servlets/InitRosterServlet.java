/*
 * Copyright 2016, Ralph A. Foy. All rights Reserved
 * 
 *  Students at Saint Paul College are free to modify this code
 *  and make it their own for homework assignments.
 */
package servlets;

import db.Roster;
import edu.saintpaul.csci2466.foam.roster.RosterException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AthleteBean;

/**
 *
 * @author rfoy
 */
public class InitRosterServlet extends HttpServlet {

    private static final long SEED = 20160920001L;
    private static final Random RAND = new Random(SEED);
    private static final int DAYS_IN_YEAR = 365;
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

        final int NUM_ATHLETES = 5;
        final int MIN_AGE = 16;
        final int MAX_AGE = 32;
        try {
            Roster roster = Roster.getInstance();
            
            request.setAttribute("athletes", roster.findAll());
            
        } catch (RosterException ex) {
            Logger.getLogger(InitRosterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.getRequestDispatcher("Roster.jsp").forward(request, response);
        
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

    // returns some number of days between MIN and MAX _AGE
    private long randBetween(int MIN_AGE, int MAX_AGE) {
        return ((MIN_AGE + RAND.nextInt(MAX_AGE - MIN_AGE)) * DAYS_IN_YEAR) + RAND.nextInt(DAYS_IN_YEAR) ;
    }

}
