package Customer;

import NewCustomer.CheckingFormulas;
import Product.GeneralProduct;
import connectors.Close;
import connectors.Database;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    static public int id; // id_client
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

        // onCloseRequest
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

        String sqlQuery = "SELECT p.product_name, p.id_product, t.type_name, p.price, p.quantity_store FROM product p JOIN product_type t ON p.id_type = t.id_type";
        // [EDYCJA]
        // ZMIENIC NA WIDOK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        boolean isFilter = false;

        String type = productTypeComboBox.getSelectionModel().getSelectedItem();

        if( !type.equals("All")){
            sqlQuery += " WHERE t.type_name= \'"+type+"\'";
            isFilter = true;
        }

        if(isAvailableCheckBox.isSelected()){
            if(!isFilter)
                sqlQuery += " WHERE";
            else
                sqlQuery += " AND";
            sqlQuery += " p.quantity_store > 0";
        }

        if(inBudgetCheckBox.isSelected()){
            if(!isFilter)
                sqlQuery += " WHERE";
            else
                sqlQuery += " AND";
            sqlQuery += " p.price < "+ customer.client.getMoney();
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


// ------------------------------- CART ------------------------------------


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
