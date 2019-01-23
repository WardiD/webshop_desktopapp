package Customer;

import java.sql.SQLException;

public class Customer {

    Client client;
    Address address;
    Contact contact;

    Customer(int id_client){
        try{
            //make contact by id_contact
            contact = new Contact(id_client);
            // make address by id_client
            address = new Address(id_client);
            // make client by id_client
            client = new Client(id_client);
            System.out.println("Customer created!");
            System.out.println("----------------");
        } catch (SQLException ex){
            ex.getMessage();
            ex.printStackTrace();
        }



    }

}
