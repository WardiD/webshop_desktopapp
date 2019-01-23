package Loginapp;

import Customer.CustomerController;
import NewCustomer.NewCustomerController;
import Worker.WorkerController;
import connectors.Database;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    static public int id;
    LoginModel loginModel = new LoginModel();

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button guestLoginButton;
    @FXML
    private Label dbStatusLabel;

    @FXML
    private Label loginStatusLabel;

    @FXML
    private TextField adminUsernameField;
    @FXML
    private PasswordField adminPasswordField;
    @FXML
    private Button adminLoginButton;

    @FXML
    private Button createNewAccountButton;


    public void initialize(URL url, ResourceBundle rb){
        if(this.loginModel.isDatabaseConnected()){
            this.dbStatusLabel.setTextFill(Color.GREEN);
            this.dbStatusLabel.setText("Connected to shop's database correctly");
        } else {
            this.dbStatusLabel.setTextFill(Color.RED);
            this.dbStatusLabel.setText("DATABASE ERROR");
        }
    }

    @FXML
    public void loginAsWorker(ActionEvent event){
        try{
            if(this.loginModel.isLoginWorker(this.adminUsernameField.getText(),this.adminPasswordField.getText())){
                System.out.println("login as worker - true");
                Stage stage = (Stage)this.loginButton.getScene().getWindow();
                stage.close();
                workerLogin();
            }
        } catch ( Exception lex){

        }
    }

    @FXML
    public void loginAsCustomer(ActionEvent event){
        /*
        System.out.println("loginAsCustomer");
        System.out.println("email: "+this.emailField.getText());
        System.out.println("passwd: "+this.passwordField.getText());
        */
        try{
            if(this.loginModel.isLoginCustomer(this.emailField.getText(),this.passwordField.getText())){
                Stage stage = (Stage)this.loginButton.getScene().getWindow();
                stage.close();

                customerLogin();
            } else {
                this.loginStatusLabel.setTextFill(Color.RED);
                this.loginStatusLabel.setText("Try again!");
            }
        } catch ( Exception lex){

        }
    }

    @FXML
    public void loginAsGuest(){
        try{
            CustomerController.id = -1;
            Stage customerStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Customer/CustomerView.fxml"));

            Parent root = (Parent) loader.load();
            CustomerController customerController = loader.getController();

            Scene scene = new Scene(root);
            customerStage.setScene(scene);
            customerStage.setTitle("[GUEST MODE] Catalog of products");
            customerStage.setResizable(false);

            Stage stage = (Stage)this.loginButton.getScene().getWindow();
            stage.close();

            customerStage.show();
        } catch (IOException ex){
            ex.printStackTrace();
        }
}


    public void customerLogin(){
        try{
            CustomerController.id = findIDByEmail(this.emailField.getText());
            System.out.println("customerController.id = "+CustomerController.id);
            Stage customerStage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Customer/CustomerView.fxml"));
            CustomerController customerController = loader.getController();

            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            customerStage.setScene(scene);
            customerStage.setTitle("Account : "+this.emailField.getText());
            customerStage.setResizable(false);

            customerStage.show();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void workerLogin(){
        try{
            Stage workerStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("/Worker/Worker.fxml").openStream());

            WorkerController workerController = loader.getController();

            Scene scene = new Scene(root);
            workerStage.setScene(scene);
            workerStage.setTitle("logged as: "+adminUsernameField.getText());
            workerStage.setResizable(false);
            workerStage.show();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    public void createNewAccount() {
        try {
            Stage newCustomerStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/NewCustomer/NewCustomer.fxml"));

            Parent root = (Parent) loader.load();
            NewCustomerController newCustomerController = loader.getController();
            Scene scene = new Scene(root);
            newCustomerStage.setScene(scene);
            newCustomerStage.setTitle("Creating new account");
            newCustomerStage.setResizable(false);
            newCustomerStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private int findIDByEmail(String email){
        PreparedStatement preparedStatement = null;
        String sqlQuery = "SELECT cl.id_client FROM Client cl, Contact co WHERE co.email = ? AND co.id_contact = cl.id_contact";

        try {
            System.out.println(1);
            preparedStatement = Database.connection.prepareStatement(sqlQuery);
            System.out.println(2);
            preparedStatement.setString(1,email);
            System.out.println(3);
            ResultSet result = preparedStatement.executeQuery();
            System.out.println(result.next());
            System.out.println(result.getInt(1));
            System.out.println("findIDByEmail: email = "+email+", id = "+result.getInt(1));

            return result.getInt(1);

        } catch (SQLException ex){
            ex.getMessage();
        }
        System.out.println("findIDByEmail - something goes wrong!");
        return -1;
    }

}

