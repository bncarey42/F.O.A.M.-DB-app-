/*
 * Copyright 2016, Ralph A. Foy. All rights Reserved
 * 
 *  Students at Saint Paul College are free to modify this code
 *  and make it their own for homework assignments.
 */
package model;

import edu.saintpaul.csci2466.foam.model.Athlete;
import java.time.LocalDate;

/**
 *
 * @author rfoy
 */
public class AthleteBean implements Athlete {

    @Override
    public String toString() {
        return "AthleteBean{" + "age=" + getAge() + ", dateOfBirth=" + dateOfBirth + ", firstName=" + firstName + ", nationalID=" + nationalID + ", lastName=" + lastName + '}';
    }


    private LocalDate dateOfBirth;
    private String firstName;
    private String nationalID;
    private String lastName;

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public int getAge() {
        if (dateOfBirth == null || dateOfBirth.isAfter(OLYMPIC_DATE)) {
            return INVALID_DATE;
        } else {
            return dateOfBirth.until(OLYMPIC_DATE).getYears();
        }
    }

    @Override
    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public String getNationalID() {
        return this.nationalID;
    }

}
