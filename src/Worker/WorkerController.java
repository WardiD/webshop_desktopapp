package Worker;

import Customer.CustomerController;
import Product.Transaction;
import connectors.Close;
import connectors.Database;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class WorkerController implements Initializable {
    static public int id_worker;
    static public boolean superAdmin;

    @FXML
    private AnchorPane ap;

    // --- Customer contact list
    @FXML
    private ListView<String> customerList;
    // --- Workers table
    @FXML
    private TableView<Workers> workerTable;
    @FXML
    private TableColumn<Workers, Integer> workerIDColumn, workerPhoneColumn;
    @FXML
    private TableColumn<Workers,String> workerNameColumn,workerEmailColumn;
    @FXML
    private Button newWorkerButton,removeWorkerButton;
    // --- Orders table
    @FXML
    private TableView<Transaction> orderTable;
    @FXML
    private TableColumn<Transaction,Integer> orderIDColumn;
    @FXML
    private TableColumn<Transaction,String> orderWorkerColumn,orderStatusColumn;
    @FXML
    private TableColumn<Transaction,Double> orderPriceColumn;
    @FXML
    private TableColumn<Transaction, Date> orderPlacedDateColumn,orderRealizationDateColumn;
    @FXML
    private ComboBox<String> orderStatusComboBox;
    @FXML
    private Button nextStatusButton;
    // --- New product
    @FXML
    private ComboBox<String > productTypeComboBox;
    @FXML
    private Button addProductButton;

    // INIT
    @Override
    public void initialize(URL url, ResourceBundle rb){
        if(!WorkerController.superAdmin)
            removeWorkerButton.setDisable(true);
        // Customer contact list
        customerList.setItems(makeCustomerContactsData());

        // Workers table
        fillWorkerTable();
        workerIDColumn.setCellValueFactory(new PropertyValueFactory<>("id_worker"));
        workerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        workerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        workerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));


        // Orders table
        orderStatusComboBox.setItems(makeOrderTypeData());
        orderStatusComboBox.getSelectionModel().selectFirst();

        fillOrderTable();
        orderIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderWorkerColumn.setCellValueFactory(new PropertyValueFactory<>("workerName"));
        orderStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        orderPlacedDateColumn.setCellValueFactory(new PropertyValueFactory<>("placedDate"));
        orderRealizationDateColumn.setCellValueFactory(new PropertyValueFactory<>("realizationDate"));
        orderPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        // New product
        productTypeComboBox.setItems(makeProductTypeData());
        productTypeComboBox.getSelectionModel().selectFirst();

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


    // --- Customer contact list
    private ObservableList<String> makeCustomerContactsData(){
        ObservableList<String> data = FXCollections.observableArrayList();

        String sqlQuery = "SELECT CONCAT(cl.first_name,' ',cl.last_name),co.email, co.phone_number FROM client cl JOIN contact co ON cl.id_contact=co.id_contact;";

        try {

            PreparedStatement preparedStatement = Database.connection.prepareStatement(sqlQuery);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                String contact = result.getString(1);
                contact += "  |  "+result.getString(2);
                if(result.getInt(3) != 0)
                    contact += "  |  "+result.getInt(3);
                data.add(contact);
            }
            return data;
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    // --- Workers table
    public void fillWorkerTable(){
        String sqlQuery = "SELECT id_worker, CONCAT(first_name,' ',last_name), phone_number, email,id_log,superadmin FROM worker";

        showWorkerTable(sqlQuery);
    }

    private void showWorkerTable(String sqlQuery) {
        try {

            PreparedStatement preparedStatement = Database.connection.prepareStatement(sqlQuery);

            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Workers workers = new Workers(
                        result.getInt(1),
                        result.getString(2),
                        result.getInt(3),
                        result.getString(4),
                        result.getInt(5),
                        result.getBoolean(6)
                );
                System.out.println(workers);
                workerTable.getItems().add(workers);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public void removeWorker(){
        if(workerTable.getSelectionModel().getSelectedItem().isSuperAdmin()){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("ERROR!");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("You cannot remove SuperAdmin from database!!!");

            errorAlert.showAndWait();

            return;
        }
        System.out.println(" przed usuwaniem");
        try{
            Database.connection.setAutoCommit(false);
            String sqlQuery = "DELETE FROM worker WHERE id_worker = ?";

            PreparedStatement preparedStatement = Database.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1,workerTable.getSelectionModel().getSelectedItem().getId_worker());

            int countRow = preparedStatement.executeUpdate();
            System.out.println("usuwanie worker - "+countRow);
            if(countRow == 1){
                String sqlQuery1 = "DELETE FROM administration_panel WHERE id_log = ?";
                PreparedStatement preparedStatement1 = Database.connection.prepareStatement(sqlQuery1);

                preparedStatement1.setInt(1,workerTable.getSelectionModel().getSelectedItem().getId_log());
                int countRow1 = preparedStatement1.executeUpdate();
                System.out.println("usuwanie administration - "+countRow1);
                if(countRow1 == 1){
                    Database.connection.commit();
                    System.out.println("commit");
                } else {
                    Database.connection.rollback();
                }
            } else {
                Database.connection.rollback();
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        System.out.println("setting autommit true");
        Database.setAutoCommitTrue();
        workerTable.getItems().clear();
        fillWorkerTable();
    }

    // --- Orders table
    private ObservableList<String> makeOrderTypeData(){
        ObservableList<String> data = FXCollections.observableArrayList();

        String sqlQuery = "SELECT status_name FROM status";

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

    public void fillOrderTable(){
        String sqlQuery = "SELECT * FROM orderview";

        showOrderTable(sqlQuery);
    }

    private void showOrderTable(String sqlQuery) {
        try {

            PreparedStatement preparedStatement = Database.connection.prepareStatement(sqlQuery);

            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Transaction transaction = new Transaction(
                        result.getInt(1),
                        (result.getString(2).isEmpty() ? "" : result.getString(2) ),
                        result.getString(3),
                        result.getDate(4),
                        result.getDate(5),
                        result.getDouble(6),
                        result.getInt(7)
                );
                System.out.println(transaction);
                this.orderTable.getItems().add(transaction);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // --- New product
    private ObservableList<String> makeProductTypeData(){
        ObservableList<String> data = FXCollections.observableArrayList();
        data.add("Computer");
        data.add("Mobile");
        return data;
    }







}
