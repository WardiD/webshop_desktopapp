package NewCustomer;

import connectors.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckingFormulas {

    static public boolean checkGreaterThanZero(String number) throws Exception{
        double money = Double.parseDouble(number);
        if(money > 0.0)
            return true;
        return false;
    }

    static public boolean checkLength(int Length, String string){
        if(string.length() ==  Length)
            return true;
        return false;
    }

    static public boolean checkTooLong(int maxLength, String string){
        if(string.length() >  maxLength)
            return true;
        return false;
    }

    static public boolean checkTooShort(int minLength, String string){
        if(string.length() <  minLength)
            return true;
        return false;
    }

    static public boolean checkOnlyLetters(String string){
        if(string.matches("\\D+"))
            return true;
        return false;
    }

    static public boolean checkOnlyDigits(String string){
        if(string.matches("[0-9]+") || string.matches("[0-9]+.[0-9]+"))
            return true;
        return false;
    }

    static public boolean checkContainSpace(String string){
        if(string.contains(" "))
            return true;
        return false;
    }

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

    static public boolean checkContainChar(char searchedChar, String string){
        if(string.contains(Character.toString(searchedChar)))
            return true;
        return false;
    }

    static public boolean checkEmailAddressFormat(String string){
        if(string.matches(".+@\\w+\\.\\D+"))
            return true;
        return false;
    }

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

    static public boolean checkIsStreetAddress(String string){
        if(string.matches(".* \\d+"))
                    return true;
        return false;
    }

}
