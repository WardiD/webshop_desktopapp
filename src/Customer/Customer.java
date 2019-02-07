package Customer;

/**
 * Represents customer of store and include:
 * client, address, contact
 */
public class Customer {

    Client client;
    Address address;
    Contact contact;

    /**
     * Creates customer
     * @param id_client ID of client in database
     */
    Customer(int id_client){
            //make contact by id_contact
            contact = new Contact(id_client);
            // make address by id_client
            address = new Address(id_client);
            // make client by id_client
            client = new Client(id_client);
            System.out.println("Customer created!");
            System.out.println("----------------");
    }

}
