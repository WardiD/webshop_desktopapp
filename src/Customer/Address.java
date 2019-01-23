package Customer;

import connectors.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Address {
    private int id_address;
    private String street_address;
    private String apartment;
    private int zip_code;
    private String city;
    private String country;

    Address(int id_address) throws SQLException{
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
            throw ex;
        }
    }

    private ResultSet GetAddressFromDatabase(int id_address) throws SQLException{

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
            throw new SQLException("Problem in getting data from database from CONTACT table");
        }
    }

    public String getStreet_address() {
        return street_address;
    }

    public String getApartment() {
        return apartment;
    }

    public int getZip_code() {
        return zip_code;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
