package model;

import edu.saintpaul.csci2466.foam.roster.Roster;
import edu.saintpaul.csci2466.foam.roster.RosterException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Vlaidator class provides methods for validation on input data from request.
 * It also converts data as needed and fetches a error message
 *
 * @author ben
 */
public class Validator {

    private static String errMsg = "";

    private static boolean validName;
    private static boolean validNatID;
    private static boolean validDate;

    public Validator() {
    }

    /**
     * getErrMsg() returns the error message built by the other methods
     *
     * @return errMsg the built error message
     */
    public static String getErrMsg() {
        return errMsg;
    }

    /**
     * isValidName() checks if name properties are empty or null and sets error
     * message accordingly and returns a boolean
     *
     * @param fName the input first name
     * @param lName the input last name
     * @return validName a boolean determining if the names on the request are
     * valid
     */
    public static boolean isValidName(String fName, String lName) {

        if (fName == null || fName.equals("")
                || lName == null || lName.equals("")) {
            errMsg += "First Name, and Last Name fields are required ";
            validName = false;
        } else {
            validName = true;
        }

        return validName;
    }

    /**
     * isValidNatID() check if national ID property matches the correct format
     * and not empty or null
     *
     * @param nationalID the input nation ID
     * @param roster
     * @return validNatID a boolean determining of the nationalID on the request
     * are valid
     * @throws edu.saintpaul.csci2466.foam.roster.RosterException
     */
    public static boolean isValidNatID(String nationalID) throws RosterException {
        String nIDFormat = "[A-z]{2}\\d{3}";

        if (nationalID == null || nationalID.equals("")) {
            errMsg += "National ID is Required ";

            validNatID = false;
        } else {
            if (!nationalID.matches(nIDFormat)) {
                errMsg += "National ID does not match correct format ";

                validNatID = false;
            } else {
                validNatID = true;
            }
        }

        return validNatID;
    }

    /**
     * test for a valid date input into the
     *
     * @param dateOfBirth
     * @return
     */
    public static boolean isValidDate(String dateOfBirth) {

        if (dateOfBirth.matches("\\d{4}-\\d{2}-\\d{2}")) {
            validDate = true;
        } else {
            validDate = false;
            errMsg += "The input date is not in the correct format";
        }

        return validDate;
    }

    /**
     * convertDOB() converts the input String dateOfBirth to a LocalDate object
     *
     * @param dateOfBirth the input date of birth in String format
     * @return the input date of birth as a LocalDate object
     */
    public static LocalDate convertDOB(String dateOfBirth) {

        String[] splitDateOfBirth = dateOfBirth.split("\\D");

        return LocalDate.of(Integer.parseInt(splitDateOfBirth[0]),
                Integer.parseInt(splitDateOfBirth[1]),
                Integer.parseInt(splitDateOfBirth[2]));

    }
}
