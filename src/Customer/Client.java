package Customer;

import connectors.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents client
 */
public class Client {
    private int id_client;
    private String firstName;
    private String lastName;
    private String password;
    private double money;

    /**
     * Creates client
     * @param id_client ID of client in database
     */
    Client(int id_client){
        try {
            ResultSet result = GetClientFromDatabase(id_client);
            this.id_client = result.getInt(1);
            this.firstName = result.getString(4);
            this.lastName = result.getString(5);
            this.password = result.getString(6);
            this.money = result.getDouble(7);

            System.out.println("Client constructed :\nid client = " + this.id_client);
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Gets record from database, which represent Client
     * @param id_client ID of client id database
     * @return ResultSet set of data which is return from database
     */
    private ResultSet GetClientFromDatabase(int id_client){

        PreparedStatement preparedStatement = null;

        ResultSet result = null;

        String sqlQuery = "SELECT * FROM Client cl WHERE cl.id_client = ?";

        try{
            preparedStatement = Database.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id_client);

            result = preparedStatement.executeQuery();

            if(result.next()){
                System.out.println("Contact - SELECT returns client of id_client = "+id_client);
                return result;
            }
            System.out.println("Contact - GetContactFromDatabase - something goes wrong!");
            return null;
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * It can represents amount of money which may you spent in store
     * @return money value
     */
    public double getMoney() {
        return money;
    }
}
