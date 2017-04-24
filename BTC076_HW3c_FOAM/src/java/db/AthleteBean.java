/*
 * Copyright 2016, Ralph A. Foy. All rights Reserved
 * 
 *  Students at Saint Paul College are free to modify this code
 *  and make it their own for homework assignments.
 */
package db;

/**
 *
 * @author rfoy
 */
public class AthleteBean extends model.AthleteBean {
    
    private int athleteID;

    /**
     * Get the value of athleteID
     *
     * @return the value of athleteID
     */
    public int getAthleteID() {
        return athleteID;
    }

    /**
     * Set the value of athleteID
     *
     * @param athleteID new value of athleteID
     */
    public void setAthleteID(int athleteID) {
        this.athleteID = athleteID;
    }

}
