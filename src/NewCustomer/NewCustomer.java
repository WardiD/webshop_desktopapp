package NewCustomer;

import connectors.Database;

import java.sql.*;

/**
 * Represents new customer ( from form )
 */
public class NewCustomer {
    // Client components
    String firstName;
    String lastName;
    String password;
    double wallet;
    // Contact components
    String email;
    int phoneNumber;
    // Address components
    String streetAddress;
    String apartment;
    String city;
    String country;
    int zipCode;
    int id_address;
    int id_contact;
    int id_client;
    boolean isApartmentEmpty;
    boolean isPhoneNumberEmpty;

    /**
     * Creates new customer ( from form )
     * @param firstName first name of new customer
     * @param lastName last name of new customer
     * @param password password of new customer
     * @param wallet amount of money of new customer
     * @param email email of new customer
     * @param phoneNumber phone number of new customer
     * @param streetAddress street address of new customer
     * @param apartment apartment of new customer
     * @param city city of new customer
     * @param country country of new customer
     * @param zipCode ZIP code of new customer
     * @param isApartmentEmpty boolean value about emptiness of apartment field
     * @param isPhoneNumberEmpty boolean value about emptiness of phone number field
     */
    public NewCustomer(String firstName, String lastName, String password, double wallet, String email, int phoneNumber,
                       String streetAddress, String apartment, String city, String country, int zipCode,
                       boolean isApartmentEmpty, boolean isPhoneNumberEmpty) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.wallet = wallet;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.streetAddress = streetAddress;
        this.apartment = apartment;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
        this.isApartmentEmpty = isApartmentEmpty;
        this.isPhoneNumberEmpty = isPhoneNumberEmpty;
    }

    public String toString() {
        String string = "Object NewCustomer : ";
        string += "\nfirst name : " + this.firstName;
        string += "\nlast name : " + this.lastName;
        string += "\npassword : " + this.password;
        string += "\nwallet : " + this.wallet;
        string += "\nemail : " + this.email;
        string += "\nphone number : " + this.phoneNumber;
        string += "\nstreet address : " + this.streetAddress;
        string += "\napartment : " + this.apartment;
        string += "\ncity : " + this.city;
        string += "\ncountry : " + this.country;
        string += "\nzipCode : " + this.zipCode;
        string += "\nisApartmentEmpty : " + this.isApartmentEmpty;
        string += "\nisPhoneNumberEmpty : " + this.isPhoneNumberEmpty;
        return string;
    }

    /**
     * Adds new customer to database
     * @return true when customer is added correctly
     */
    public boolean addCustomerToDatabase() {
        try{
            Database.connection.setAutoCommit(false);
            id_address = addAddressToDatabase();
            if(id_address == -1) {
                Database.connection.rollback();
                return false;
            }

            id_contact = addContactToDatabase();
            if(id_contact == -1){
                Database.connection.rollback();
                return false;
            }

            PreparedStatement preparedStatement = prepareNewClientQuery();

            int countRows = preparedStatement.executeUpdate();
            System.out.println("client - countrow = "+countRows);
            ResultSet result = preparedStatement.getGeneratedKeys();
            if (result.next()) {
                int id_client = result.getInt(1);
                System.out.println("id_client : " + id_client);
                return true;
            } else {
                Database.connection.rollback();
                return false;
            }
        }catch ( NullPointerException | SQLException ex) {
            ex.printStackTrace();
        }
        Database.setAutoCommitTrue();
        return false;
    }

    /**
     * Prepares SQL query for adding new record to Client table in database
     * @return PreparedStatement
     */
    private PreparedStatement prepareNewClientQuery(){
        PreparedStatement preparedStatement = null;
        String sqlQuery = "INSERT INTO Client (id_address, id_contact, first_name, last_name, password, money) ";
        sqlQuery += " VALUES (?,?,?,?,?,?)";
        try {
            preparedStatement = Database.connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, this.id_address);
            preparedStatement.setInt(2, this.id_contact);
            preparedStatement.setString(3, this.firstName);
            preparedStatement.setString(4, this.lastName);
            preparedStatement.setString(5, this.password);
            preparedStatement.setDouble(6, this.wallet);
            return preparedStatement;
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Prepares SQL query for adding new record to Client table in database
     * @return PreparedStatement
     */
    private PreparedStatement prepareNewContactQuery() {
        PreparedStatement preparedStatement = null;
        String sqlQuery = "INSERT INTO Contact (email,phone_number) VALUES (?,?)";
        try {
            preparedStatement = Database.connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, this.email);
            if (this.isPhoneNumberEmpty)
                preparedStatement.setNull(2, Types.INTEGER);
            else
                preparedStatement.setInt(2,this.phoneNumber);
            return preparedStatement;
        } catch ( SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    //returns id_contact

    /**
     * Adds new record to Contact table in database
     * @return int id_contact
     */
    private int addContactToDatabase(){
        PreparedStatement preparedStatement = prepareNewContactQuery();
        try {
            int countRows = preparedStatement.executeUpdate();
            System.out.println("contact - countrow = "+countRows);
            ResultSet result = preparedStatement.getGeneratedKeys();
            if (result.next()) {
                int id_contact = result.getInt(1);
                System.out.println("id_contact : " + id_contact);
                return id_contact;
            } else {
                System.out.println("Error with adding new contact");
            }
        } catch ( Exception ex){
            ex.printStackTrace();
        }
        return -1;
    }
    /**
     * Prepares SQL query for adding new record to Address table in database
     * @return PreparedStatement
     */
    private PreparedStatement prepareNewAddressQuery() {
        PreparedStatement preparedStatement = null;
        String sqlQuery = "INSERT INTO Address ";
        sqlQuery += "(street_address,apartment,zip_code,city,country) VALUES (?,?,?,?,?)";
        try{
            preparedStatement = Database.connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, this.streetAddress);
            if (this.isApartmentEmpty)
                preparedStatement.setNull(2, Types.VARCHAR);
            else
                preparedStatement.setString(2, this.apartment);
            preparedStatement.setInt(3, this.zipCode);
            preparedStatement.setString(4, this.city);
            preparedStatement.setString(5, this.country);
            return preparedStatement;
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    //returns id_address
    /**
     * Adds new record to Address table in database
     * @return int id_address
     */
    private int addAddressToDatabase(){
        PreparedStatement preparedStatement = prepareNewAddressQuery();
        try{
            int countRows = preparedStatement.executeUpdate();
            System.out.println("address - countrow = "+countRows);
            ResultSet result = preparedStatement.getGeneratedKeys();
            if (result.next()) {
                int id_address = result.getInt(1);
                System.out.println("id_address : " + id_address);
                return id_address;
            } else {
                System.out.println("Error with adding new address");
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return -1;
    }

}
