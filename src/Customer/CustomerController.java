package Customer;

import NewCustomer.CheckingFormulas;
import Product.GeneralProduct;
import Product.ProductComputerController;
import Product.ProductMobileController;
import Product.ShortcutProduct;
import connectors.Close;
import connectors.Database;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    static public int id; // id_client
    static public int id_cart; // id_cart
    private Customer customer;
    //
    //
    @FXML
    AnchorPane ap;
    // Tabs
    @FXML
    Tab catalogTab;
    @FXML
    Tab cartTab;
    @FXML
    Tab ordersTab;
    @FXML
    Tab userTab;

    // Catalog
    @FXML
    private ComboBox<String> productTypeComboBox;

    @FXML
    private TableView<GeneralProduct> productTable;

    @FXML
    private TableColumn<GeneralProduct, String> productTypeColumn, productNameColumn;
    @FXML
    private TableColumn<GeneralProduct, Double> productPriceColumn;
    @FXML
    private TableColumn<GeneralProduct, Integer> productQuantityColumn, productIDColumn;

    @FXML
    TextField quantityField;
    @FXML
    Button addToCartButton;

    @FXML
    private Button searchProductsButton;

    @FXML
    private CheckBox isAvailableCheckBox;

    @FXML
    private CheckBox inBudgetCheckBox;

    @FXML
    private Button findByIDButton;

    @FXML
    private TextField idField;

    // Cart
    @FXML
    private TableView<ShortcutProduct> cartTable;

    @FXML
    private TableColumn<ShortcutProduct, String> cartNameColumn;
    @FXML
    private TableColumn<ShortcutProduct, Integer> cartQuantityColumn, cartIDColumn;
    @FXML
    private TableColumn<ShortcutProduct, Double> cartPriceColumn;

    @FXML
    private Label cartPriceLabel;


    // Orders


    // Informations of user
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private Label streetAddressLabel;
    @FXML
    private Label apartmentLabel;
    @FXML
    private Label zipCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label countryLabel;
    @FXML
    private Label moneyLabel;


// --------------------------- GENERAL --------------------------------------------
    public void initialize(URL url, ResourceBundle rb){
        CustomerController.id_cart = -1;
        if(id == -1){
            // disable items allowed only for customers
            cartTab.setDisable(true);
            ordersTab.setDisable(true);
            userTab.setDisable(true);

            quantityField.setDisable(true);
            addToCartButton.setDisable(true);

            inBudgetCheckBox.setDisable(true);
        } else {
            customer = new Customer(id);
            showUserInformation();

            fillCartTable();
            cartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id_product"));
            cartNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
            cartQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
            cartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));

        }

        // CATALOG
        // adding data to product type combobox

        productTypeComboBox.setItems(makeProductTypeData());
        productTypeComboBox.getSelectionModel().selectFirst();
        productTypeComboBox.setOnAction((event -> {
            String selected = productTypeComboBox.getSelectionModel().getSelectedItem();
            System.out.println("GeneralProduct type combo box = wybrano : "+selected);
        }));
        // adding data to product table
        fillProductTable();

        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id_product"));
        productTypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        productQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));

        // onCloseRequest - closing SSH and database connection
        ap.sceneProperty().addListener((obs, oldScene, newScene) -> {
            Platform.runLater(() -> {
                Stage stage = (Stage) newScene.getWindow();
                stage.setOnCloseRequest(e -> {
                    Close.closeProgram();
                    //Platform.exit();
                    //System.exit(0);
                });
            });
        });

        System.out.println("Initialize - id cart = "+id_cart);
    }





// -------------------------------------- CATALOG -------------------------------------
    private ObservableList<String> makeProductTypeData(){
        ObservableList<String> data = FXCollections.observableArrayList();

        String sqlQuery = "SELECT type_name FROM product_type";

        try {

            PreparedStatement preparedStatement = Database.connection.prepareStatement(sqlQuery);
            ResultSet result = preparedStatement.executeQuery();
            data.add("All");
            while(result.next()){
                data.add(result.getString(1));
            }
            return data;
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }


    private void fillProductTable(){

        //String sqlQuery = "SELECT p.product_name, p.id_product, t.type_name, p.price, p.quantity_store FROM product p JOIN product_type t ON p.id_type = t.id_type";
        String sqlQuery = "SELECT * FROM generalproductview g";

        boolean isFilter = false;

        String type = productTypeComboBox.getSelectionModel().getSelectedItem();

        if( !type.equals("All")){
            sqlQuery += " WHERE g.type_name= \'"+type+"\'";
            isFilter = true;
        }

        if(isAvailableCheckBox.isSelected()){
            if(!isFilter)
                sqlQuery += " WHERE";
            else
                sqlQuery += " AND";
            sqlQuery += " g.quantity_store > 0";
        }

        if(inBudgetCheckBox.isSelected()){
            if(!isFilter)
                sqlQuery += " WHERE";
            else
                sqlQuery += " AND";
            sqlQuery += " g.price < "+ customer.client.getMoney();
        }

        showProductTable(sqlQuery);
    }


    public void showProductTable(String sqlQuery){
        System.out.println(sqlQuery);
        try{
            PreparedStatement preparedStatement = Database.connection.prepareStatement(sqlQuery);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                GeneralProduct product = new GeneralProduct(
                        result.getString(1), // name
                        result.getInt(2), // id_product
                        result.getString(3), // type name
                        result.getDouble(4), // price
                        result.getInt(5) // quantity
                );
                System.out.println(product);
                productTable.getItems().add(product);
            }

        }catch (SQLException ex){
            ex.printStackTrace();

        }
    }


    public void ProductFilter(){
        productTable.getItems().clear();
        fillProductTable();
    }

    public void clearFilters(){
        productTypeComboBox.getSelectionModel().selectFirst();
        isAvailableCheckBox.setSelected(false);
        inBudgetCheckBox.setSelected(false);
        productTable.getItems().clear();
        fillProductTable();
    }

    public void searchByID(){
        String errorInfo = null;
        System.out.println(idField.getText());
        if(idField.getText().isEmpty()){
            errorInfo = "ID field is empty!";
            System.out.println("empty");
        } else if(!CheckingFormulas.checkOnlyDigits(idField.getText())){
            errorInfo = "ID field gets only digits!";
            System.out.println("not digits");
        }

        if(errorInfo != null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Wrong value!");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText(errorInfo);

            errorAlert.showAndWait();
        } else {
            System.out.println("search by id -----");
            String sqlQuery = "SELECT p.product_name, p.id_product, t.type_name, p.price, p.quantity_store FROM product p JOIN product_type t ON p.id_type = t.id_type" +
                    " WHERE p.id_product = "+Integer.parseInt(idField.getText());
            productTable.getItems().clear();
            showProductTable(sqlQuery);
        }
    }

    public void showDescription(){
        try {

            if (productTable.getSelectionModel().getSelectedItem() != null) {
                String productType = productTable.getSelectionModel().getSelectedItem().getType();

                Stage descriptionStage = new Stage();
                FXMLLoader loader = null;


                switch (productType) {
                    case "Notebook":
                    case "PC":
                        ProductComputerController.id_product = productTable.getSelectionModel().getSelectedItem().getId_product();

                        loader = new FXMLLoader(getClass().getResource("/Product/ProductComputer.fxml"));
                        ProductMobileController computerController = loader.getController();
                        break;
                    case "Smartphone":
                    case "Tablet":
                        ProductMobileController.id_product = productTable.getSelectionModel().getSelectedItem().getId_product();

                        loader = new FXMLLoader(getClass().getResource("/Product/ProductMobile.fxml"));
                        ProductMobileController mobileController = loader.getController();

                        break;
                }

                Parent root = (Parent) loader.load();
                Scene scene = new Scene(root);
                descriptionStage.setScene(scene);
                descriptionStage.setTitle("Product description");
                descriptionStage.setResizable(false);


                descriptionStage.show();
            }
        } catch ( Exception ex){
            ex.printStackTrace();
        }
    }

    public void addToCart(){
        System.out.println("CART ----------- = "+CustomerController.id_cart);
        try {
        // checking quantity ( is parsable to int )
            Integer.valueOf(quantityField.getText());

        // checking cart exist
            String sqlQuery;


            if (CustomerController.id_cart == -1) {
                sqlQuery = "INSERT INTO cart (id_client, ordered) VALUES (?,false )";

                PreparedStatement preparedStatement = Database.connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, CustomerController.id);

                int countRow = preparedStatement.executeUpdate();
                ResultSet result = preparedStatement.getGeneratedKeys();

                if(countRow != 0  && result.next()){
                    CustomerController.id_cart=result.getInt(1);
                    System.out.println("New cart - id = "+CustomerController.id_cart);
                } else {
                    System.out.println("Problem z dodaniem nowego koszyka");
                }
            }
         //  checking selected product is in cart
            sqlQuery = "SELECT * FROM product_list WHERE id_cart = ? AND id_product = ?";

            PreparedStatement preparedStatement1 = Database.connection.prepareStatement(sqlQuery);
            preparedStatement1.setInt(1, CustomerController.id_cart);
            preparedStatement1.setInt(2, productTable.getSelectionModel().getSelectedItem().getId_product());

            ResultSet result = preparedStatement1.executeQuery();
            if(result.next()){
                sqlQuery = "UPDATE product_list SET quantity=quantity + ? WHERE id_cart = ? AND id_product = ?";

                PreparedStatement preparedStatement2 = Database.connection.prepareStatement(sqlQuery);
                // walidacja do inta
                preparedStatement2.setInt(1, Integer.parseInt(quantityField.getText()));
                preparedStatement2.setInt(2, CustomerController.id_cart);
                preparedStatement2.setInt(3, productTable.getSelectionModel().getSelectedItem().getId_product());

                int updated = preparedStatement2.executeUpdate();
                if(updated != 0){
                    System.out.println("CART UPDATE");
                    cartTable.getItems().clear();
                    fillCartTable();
                }
            } else {
                sqlQuery = "INSERT INTO product_list (id_cart,id_product,quantity) VALUES (?,?,?)";

                PreparedStatement preparedStatement2 = Database.connection.prepareStatement(sqlQuery);

                preparedStatement2.setInt(1, CustomerController.id_cart);
                preparedStatement2.setInt(2, productTable.getSelectionModel().getSelectedItem().getId_product());
                // walidacja do inta
                preparedStatement2.setInt(3, Integer.parseInt(quantityField.getText()));


                int added = preparedStatement2.executeUpdate();

                if(added != 0){
                    System.out.println("INSERT INTO CART");
                    cartTable.getItems().clear();
                    fillCartTable();
                }
            }
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - quantity");
            alert.setHeaderText(null);
            alert.setContentText("Problem with quantity value - it should be integer number");

            alert.showAndWait();
        } catch (SQLException ex){
            ex.printStackTrace();
        }

    }



// ------------------------------- CART ------------------------------------

    private void fillCartTable(){

        String sqlQuery = "SELECT * FROM cartview c WHERE c.id_client = ?";

        showCartTable(sqlQuery);
    }

    public void showCartTable(String sqlQuery){
        boolean isCartSet = false;
        try{
            PreparedStatement preparedStatement = Database.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1,CustomerController.id);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                ShortcutProduct product = new ShortcutProduct(
                        result.getInt(1), // id_product
                        result.getString(2), // name
                        result.getInt(3), // quantity
                        result.getDouble(4)// price
                );
                if(!isCartSet){
                    CustomerController.id_cart=result.getInt(5);
                    isCartSet = true;
                }

                System.out.println(product);
                cartTable.getItems().add(product);
            }
        }catch (SQLException ex){
            ex.printStackTrace();

        }
        computeCartPrice();
    }

    public void removeFromCart(){
        if (cartTable.getSelectionModel().getSelectedItem() != null) {
            int id = cartTable.getSelectionModel().getSelectedItem().getId_product();

            String sqlQuery = "DELETE FROM product_list p WHERE id_product=? AND p.id_cart=? ";

            try{
                PreparedStatement preparedStatement = Database.connection.prepareStatement(sqlQuery);
                preparedStatement.setInt(1,id);
                preparedStatement.setInt(2,CustomerController.id_cart);

                int deletedRow = preparedStatement.executeUpdate();

                if (deletedRow != 0){
                    cartTable.getItems().clear();
                    fillCartTable();
                } else {
                    System.out.println("problem z usuwaniem z koszyka");
                }
            }catch (SQLException ex){
                ex.printStackTrace();
            }


        }
    }


    public void clearCart(){
        String sqlQuery = "DELETE FROM product_list p WHERE p.id_cart=? ";

        try{
            PreparedStatement preparedStatement = Database.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1,CustomerController.id_cart);

            int deletedRows = preparedStatement.executeUpdate();

            if (deletedRows > 0){
                //sqlQuery = "DELETE FROM cart c WHERE c.id_cart=?";
                cartTable.getItems().clear();
                fillCartTable();
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }


    private void computeCartPrice() {
        try {

            String sqlQuery = "UPDATE cart SET cart_price = (SELECT SUM(p.price * pl.quantity) FROM product_list pl JOIN product p ON pl.id_product=p.id_product WHERE pl.id_cart=? GROUP BY pl.id_cart) WHERE id_cart=?";

            PreparedStatement preparedStatement = Database.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1,CustomerController.id_cart);
            preparedStatement.setInt(2,CustomerController.id_cart);

            int countRow = preparedStatement.executeUpdate();
            System.out.println("compute price - countRow = "+countRow);
            if(countRow != 0){
                String sqlQuery1 = "SELECT cart_price FROM cart WHERE id_client=? AND ordered = false";
                PreparedStatement preparedStatement1 = Database.connection.prepareStatement(sqlQuery1);
                preparedStatement1.setInt(1, CustomerController.id);

                ResultSet result = preparedStatement1.executeQuery();
                if(result.next()){
                    System.out.println("compute price - setting Label" );
                    cartPriceLabel.setText(Double.toString(result.getDouble(1)));
                }
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void makeOrder(){
        System.out.println("--- MAKE ORDER ---- ID CART "+CustomerController.id_cart );
        try {
            String sqlQuery = "UPDATE cart SET ordered=true WHERE id_cart = ? ";

            PreparedStatement preparedStatement = Database.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, CustomerController.id_cart);

            int countrow = preparedStatement.executeUpdate();
            System.out.println("countrrow 1 = "+countrow);
            if( countrow != 0){
                String sqlQuery1 = "INSERT INTO transactions (id_client, id_cart, creation_date) VALUES (?,?, NOW()) ";

                preparedStatement = Database.connection.prepareStatement(sqlQuery1);
                preparedStatement.setInt(1,CustomerController.id);
                preparedStatement.setInt(2,CustomerController.id_cart);

                countrow = preparedStatement.executeUpdate();
                System.out.println("countrrow 2 = "+countrow);
                if( countrow != 0 ){
                    System.out.println("Transaction made succesfully");
                }
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        cartTable.getItems().clear();
        cartPriceLabel.setText("");
        CustomerController.id_cart = -1;
        System.out.println("--- MAKE ORDER ---- NEW VALUE"+CustomerController.id_cart );
    }


// ----------------------------------- ORDERS ---------------------------------

// ---------------------------- USER ---------------------------------------------------
    private void showUserInformation(){
        firstNameLabel.setText(this.customer.client.getFirstName());
        lastNameLabel.setText(this.customer.client.getLastName());
        emailLabel.setText(this.customer.contact.getEmail());
        if(this.customer.contact.getPhone_number() == 0)
            phoneNumberLabel.setText("--- --- ---");
        else
            phoneNumberLabel.setText(Integer.toString(this.customer.contact.getPhone_number()));
        streetAddressLabel.setText(this.customer.address.getStreet_address());
        if(this.customer.address.getStreet_address() == "")
            apartmentLabel.setText("-----");
        else
            apartmentLabel.setText(this.customer.address.getApartment());
        zipCodeLabel.setText(Integer.toString(this.customer.address.getZip_code()));
        cityLabel.setText(this.customer.address.getCity());
        countryLabel.setText(this.customer.address.getCountry());
        moneyLabel.setText(Double.toString(this.customer.client.getMoney()));
    }

}
