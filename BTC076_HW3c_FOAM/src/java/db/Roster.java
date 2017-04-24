/*
 * Copyright 2016, Ralph A. Foy. All rights Reserved
 * 
 *  Students at Saint Paul College are free to modify this code
 *  and make it their own for homework assignments.
 */
package db;

import edu.saintpaul.csci2466.foam.model.Athlete;
import edu.saintpaul.csci2466.foam.roster.RosterException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import model.Validator;

/**
 *
 * @author rfoy
 */
public class Roster {

    private DataSource ds = null;

    private Roster() {
        try {
            ds = getFOAM();
        } catch (NamingException ex) {
            Logger.getLogger(Roster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a reference to the singleton roster.
     *
     * @return reference to the roster
     */
    public static Roster getInstance() {
        return RosterHolder.INSTANCE;
    }

    /**
     * Returns a list containing all athletes on the roster.
     *
     * @return List of athletes
     * @throws edu.saintpaul.csci2466.foam.roster.RosterException
     */
    public List<Athlete> findAll() throws RosterException {
        List<Athlete> athletes = new LinkedList<>();
        final String SQL_QUERY
                = "SELECT AthleteID, NationalID, FirstName, LastName, DateOfBirth"
                + " FROM Athletes ORDER BY AthleteID desc";
        try (final Connection conn = ds.getConnection();
                final PreparedStatement stmt = conn.prepareStatement(SQL_QUERY);
                final ResultSet rs = stmt.executeQuery(SQL_QUERY);) {

            while (rs.next()) {
                AthleteBean athlete = new AthleteBean();
                athlete.setAthleteID(rs.getInt("AthleteID"));
                athlete.setNationalID(rs.getString("NationalID"));
                athlete.setFirstName(rs.getString("FirstName"));
                athlete.setLastName(rs.getString("LastName"));
                athlete.setDateOfBirth(rs.getDate("DateOfBirth").toLocalDate());
                athlete.getAge();

                athletes.add(athlete);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Roster.class.getName()).log(Level.SEVERE, null, ex);
            throw new RosterException("Error accessing database", ex);
        }
        return athletes;
    }

    /**
     * find Athlete returns an Athlete from the DataBase
     *
     * @param request HTTPRequest passed from the
     * @return athlete - an Athlete Object
     */
    public AthleteBean findAthlete(HttpServletRequest request) {
        AthleteBean athlete = new AthleteBean();
        athlete.setNationalID(request.getParameter("nationalID"));
        athlete.setFirstName(request.getParameter("firstName"));
        athlete.setLastName(request.getParameter("lastName"));
        athlete.setDateOfBirth(Validator.convertDOB(request.getParameter("dateOfBirth")));

        try (Connection con = ds.getConnection();
                PreparedStatement ps = createSearchPrepStmt(con, request);
                ResultSet rs = ps.executeQuery();) {
            athlete.setAthleteID(rs.getInt("AthleteID"));
            athlete.setNationalID(rs.getString("NationalID"));
            athlete.setFirstName(rs.getString("FirstName"));
            athlete.setLastName(rs.getString("LastName"));
            athlete.setDateOfBirth(rs.getDate("DateOfBirth").toLocalDate());
        } catch (SQLException ex) {
            Logger.getLogger(Roster.class.getName()).log(Level.SEVERE, null, ex);
        }

        return athlete;
    }

    private PreparedStatement createSearchPrepStmt(Connection conn, HttpServletRequest request) throws SQLException {
        String sql = "SELECT AthleteID, NationalID, FirstName, LastName, DateOfBirth"
                + " FROM foam.Athletes WHERE NationalID = '?'";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, request.getParameter("nationalID"));

        return ps;
    }

    /**
     * NewAthlete creates a new Athlete and inserts is into the database
     *
     * @param athlete the athlete to be added
     */
    public void newAthlete(Athlete athlete) {

        try (Connection con = ds.getConnection();
                PreparedStatement ps = createInsertPrepStmt(con, athlete);) {

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Roster.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    private PreparedStatement createInsertPrepStmt(Connection con, Athlete athlete) throws SQLException {
        String sql = "INSERT INTO foam.Athletes (NationalID, FirstName, LastName, DateOfBirth) "
                + "VALUES (?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, athlete.getNationalID());
        ps.setString(2, athlete.getFirstName());
        ps.setString(3, athlete.getLastName());
        ps.setDate(4, Date.valueOf(athlete.getDateOfBirth()));

        return ps;

    }

    /**
     * Update changes the values stored in a specific athlete in the database
     *
     * @param athlete the athlete to be changed
     */
    public void update(Athlete athlete) {
        try (Connection con = ds.getConnection();
                PreparedStatement ps = createUpdatePrepStmt(con, athlete);) {

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Roster.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private PreparedStatement createUpdatePrepStmt(Connection con, Athlete athlete) throws SQLException {

        String sql = "UPDATE foam.Athletes "
                + "SET FirstName = ?, "
                + "LastName = ?, "
                + "DateOfBirth = ? "
                + "WHERE NationalID = ?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, athlete.getFirstName());
        ps.setString(2, athlete.getLastName());
        ps.setDate(3, Date.valueOf(athlete.getDateOfBirth()));
        ps.setString(4, athlete.getNationalID());

        return ps;
    }

    /**
     * delete removes an Athlete from the database
     *
     * @param athlete the athlete to be removed from the database
     */
    public void delete(Athlete athlete) {

        try (Connection con = ds.getConnection();
                PreparedStatement ps = createDeletePrepStmt(con, athlete);) {

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Roster.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private PreparedStatement createDeletePrepStmt(Connection con, Athlete athlete) throws SQLException {
        String sql = "DELETE FROM foam.Athletes where NationalID = ?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, athlete.getNationalID());

        return ps;

    }

    private static class RosterHolder {

        private static final Roster INSTANCE = new Roster();
    }

    private DataSource getFOAM() throws NamingException {
        Context c = new InitialContext();
        return (DataSource) c.lookup("java:comp/env/FOAM");
    }

}
