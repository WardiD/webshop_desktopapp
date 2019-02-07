package NewCustomer;

import connectors.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Provides methods to check input data
 */
public class CheckingFormulas {
    /**
     * @param number String with number value
     * @return true when input data is greater than 0.0
     */
    static public boolean checkGreaterThanZero(String number){
        double money = Double.parseDouble(number);
        if(money > 0.0)
            return true;
        return false;
    }

    /**
     * @param Length required length
     * @param string string to check
     * @return true when input data length is same as 'Length'
     */
    static public boolean checkLength(int Length, String string){
        if(string.length() ==  Length)
            return true;
        return false;
    }

    /**
     * @param maxLength expected max value
     * @param string string to check
     * @return true if input data length is greater than 'maxLength'
     */
    static public boolean checkTooLong(int maxLength, String string){
        if(string.length() >  maxLength)
            return true;
        return false;
    }

    /**
     * @param minLength expected min value
     * @param string string to check
     * @return true when is input data length is less than 'minLength'
     */
    static public boolean checkTooShort(int minLength, String string){
        if(string.length() <  minLength)
            return true;
        return false;
    }

    /**
     *
     * @param string string to check
     * @return true when input data contains only letters
     */
    static public boolean checkOnlyLetters(String string){
        if(string.matches("\\D+"))
            return true;
        return false;
    }

    /**
     * @param string string to check
     * @return true when input data contains only digits
     */
    static public boolean checkOnlyDigits(String string){
        if(string.matches("[0-9]+") || string.matches("[0-9]+.[0-9]+"))
            return true;
        return false;
    }

    /**
     * @param string string to check
     * @return true when input data contains space
     */
    static public boolean checkContainSpace(String string){
        if(string.contains(" "))
            return true;
        return false;
    }

    /**
     *
     * @param string string to check
     * @return true when input data contains both - letters and digits
     */
    static public boolean checkContainDigitsAndLetters(String string){
        String digitsRegex = ".*[0-9].*";
        String lettersRegex = ".*[a-zA-Z].*";
        if(string.matches(digitsRegex)){
            if(string.matches(lettersRegex)){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param searchedChar expected char value
     * @param string string to check
     * @return true when input data contains searched char
     */
    static public boolean checkContainChar(char searchedChar, String string){
        if(string.contains(Character.toString(searchedChar)))
            return true;
        return false;
    }

    /**
     *
     * @param string string to check
     * @return true when input data suits to email format
     */
    static public boolean checkEmailAddressFormat(String string){
        if(string.matches(".+@\\w+\\.\\D+"))
            return true;
        return false;
    }

    /**
     *
     * @param newEmailAddress string to check
     * @return true when provided email appears in database
     */
    static public boolean checkIsEmailUsed(String newEmailAddress){
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        String sqlQuery ="SELECT * FROM Contact WHERE Contact.email = ?";

        try{
            preparedStatement =  Database.connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,newEmailAddress);
            result = preparedStatement.executeQuery();

            if(result.next()) {
                return true;
            } else {
                return false;
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }
        // true as default cause it's one of reasons why we cannot add new customer
        return true;
    }

    /**
     *
     * @param string string to check
     * @return true when input data suits to street address format
     */
    static public boolean checkIsStreetAddress(String string){
        if(string.matches(".* \\d+"))
                    return true;
        return false;
    }

}
