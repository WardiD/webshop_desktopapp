package Customer;

import connectors.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents contact
 */
public class Contact {
    private int id_contact;
    private int phone_number;
    private String email;

    /**
     * Creates contact
     * @param id_client ID of client in database
     */
    Contact(int id_client) {
        try{
            ResultSet result = GetContactFromDatabase(id_client);
            this.id_contact = result.getInt(1);
            if(result.getObject(2) != null)
                this.phone_number = result.getInt(2);
            else
                this.phone_number = 0;
            this.email = result.getString(3);
            System.out.println("Contact constructed :\nid contact = "+this.id_contact+", phone number =");
        } catch (SQLException ex){
            ex.printStackTrace();
        }

    }
    /**
     * Gets record from database, which represent Contact
     * @param id_client ID of client in database
     * @return ResultSet set of data which is return from database
     */
    private ResultSet GetContactFromDatabase(int id_client){
        System.out.println("Contact - GetContactFromDatabase");
        PreparedStatement preparedStatement = null;

        ResultSet result = null;

        String sqlQuery = "SELECT co.id_contact,co.phone_number, co.email FROM Contact co, Client cl "
                + "WHERE co.id_contact = cl.id_contact AND cl.id_client = ?";

        try{
            preparedStatement = Database.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id_client);

            result = preparedStatement.executeQuery();

            if(result.next()){
                System.out.println("Contact - SELECT returns contact of id_client = "+id_client);
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
     * @return id of contact
     */
    public int getId_contact() {
        return id_contact;
    }

    /**
     *
     * @return phone number
     */
    public int getPhone_number() {
        return phone_number;
    }

    /**
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }
}
