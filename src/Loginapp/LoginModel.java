package Loginapp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connectors.Database;

/**
 * Login model, which checks written data in database
 */
public class LoginModel {
    /**
     * checks customer exists
     * @param email email of customer
     * @param password password of customer
     * @return true where input data is correct
     */
    public boolean isLoginCustomer(String email, String password){
        System.out.println("isLoginCustomer - email: "+email+", passwd: "+password);
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        String sqlQuery = "SELECT Client.id_client, Contact.email, Client.password FROM Client INNER JOIN Contact " +
                "ON Client.id_contact=Contact.id_contact  WHERE Contact.email LIKE ? AND Client.password LIKE ?";

        try{
            preparedStatement = Database.connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            result = preparedStatement.executeQuery();

            if(result.next()){
                return true;
            }
            return false;
        } catch (SQLException ex){
            System.out.println(" isLoginCustomer = false ( EXCEPTION )");
            ex.printStackTrace();
        }
        finally {
            try{
                preparedStatement.close();
                result.close();
            } catch ( NullPointerException | SQLException ex){
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Checks worker exists
     * @param user login of worker
     * @param password password of worker
     * @return true where input data is correct
     */
    public boolean isLoginWorker(String user, String password){
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        System.out.println("isLoginWorker - user: "+user+", passwd: "+password);
        String sqlQuery = "SELECT * FROM Administration_panel WHERE login LIKE ? AND password LIKE ?";

        try{

            preparedStatement = Database.connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);

            result = preparedStatement.executeQuery();

            if(result.next()){
                return true;
            }
            return false;

        } catch (SQLException ex){
            System.out.println(" isLoginWorker = false ( EXCEPTION )");
            ex.printStackTrace();
        }
        finally {
            try {
                preparedStatement.close();
                result.close();
            }  catch ( NullPointerException | SQLException ex){
                ex.printStackTrace();
            }
        }
        return false;
    }

}
