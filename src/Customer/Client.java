package Customer;

import connectors.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Client {
    private int id_client;
    private String firstName;
    private String lastName;
    private String password;
    private double money;


    Client(int id_client) throws SQLException{
        try {
            ResultSet result = GetClientFromDatabase(id_client);
            this.id_client = result.getInt(1);
            this.firstName = result.getString(4);
            this.lastName = result.getString(5);
            this.password = result.getString(6);
            this.money = result.getDouble(7);

            System.out.println("Client constructed :\nid client = " + this.id_client);
        } catch (SQLException ex){
            throw ex;
        }
    }

    private ResultSet GetClientFromDatabase(int id_client) throws SQLException{

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
            throw new SQLException("Problem in getting data from database from CONTACT table");
        }
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public double getMoney() {
        return money;
    }
}
