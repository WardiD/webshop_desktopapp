package Customer;

import connectors.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents address
 */
public class Address {
    private int id_address;
    private String street_address;
    private String apartment;
    private int zip_code;
    private String city;
    private String country;

    /**
     * Creates address with specified data
     * @param id_address ID of address in database
     */
    Address(int id_address){
        try {
            ResultSet result = GetAddressFromDatabase(id_address);
            this.id_address = result.getInt(1);
            this.street_address = result.getString(2);
            this.apartment = result.getString(3);
            this.zip_code = result.getInt(4);
            this.city = result.getString(5);
            this.country = result.getString(6);

            System.out.println("Address constructed :\nid address = "+this.id_address);
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Gets record from database, which represent Address
     * @param id_address ID of address in database
     * @return ResultSet set of data which is return from database
     */
    private ResultSet GetAddressFromDatabase(int id_address){

        PreparedStatement preparedStatement = null;

        ResultSet result = null;

        String sqlQuery = "SELECT a.* FROM Address a, Client cl "
            + "WHERE a.id_address = cl.id_address AND cl.id_address = ?";

        try{
            preparedStatement = Database.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id_address);

            result = preparedStatement.executeQuery();

            if(result.next()){
                System.out.println("Contact - SELECT returns address of id_address = "+id_address);
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
     * @return street address
     */
    public String getStreet_address() {
        return street_address;
    }

    /**
     *
     * @return apartment address
     */
    public String getApartment() {
        return apartment;
    }

    /**
     *
     * @return zip code
     */
    public int getZip_code() {
        return zip_code;
    }

    /**
     *
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }
}
