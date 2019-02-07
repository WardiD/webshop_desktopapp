package Worker;

import NewCustomer.CheckingFormulas;
import connectors.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Represents controller of new worker part of application
 */
public class newWorkerController {

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField repeatPasswordField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;

    @FXML
    private Button addWorkerButton;

    private String message;

    /**
     * Checks that form is completed correctly
     */
    public void newWorker(){
        if(checkForm()){
            System.out.println("after check form");
            addWorkerToDatabase();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Problems in form!");
            alert.setHeaderText(null);
            alert.setContentText(message);

            alert.showAndWait();
        }
    }

    /**
     * Checks form is completed correctly
     *  @return true, when each field is correct
     */
    private boolean checkForm(){
        message = "";
        boolean isFormCorrect = false;
        isFormCorrect = checkLogin();
        isFormCorrect = checkPassword();
        isFormCorrect = checkFirstName();
        isFormCorrect = checkLastName();
        isFormCorrect = checkEmail();
        isFormCorrect = checkPhoneNumber();

        return isFormCorrect;
    }

    /**
     * Adds new worker to database
     */
    private void addWorkerToDatabase(){
        try {
            Database.connection.setAutoCommit(false);

            int id_log = addLogin();
            if(id_log != -1){

                String sqlQuery = "INSERT INTO worker (id_log,first_name,last_name,email,phone_number)" +
                        " VALUES (?,?,?,?,?)";
                PreparedStatement preparedStatement = Database.connection.prepareStatement(sqlQuery);
                preparedStatement.setInt(1,id_log);
                preparedStatement.setString(2,firstNameField.getText());
                preparedStatement.setString(3,lastNameField.getText());
                preparedStatement.setString(4,emailField.getText());
                preparedStatement.setInt(5,Integer.parseInt(phoneField.getText()));

                int countRow = preparedStatement.executeUpdate();
                if(countRow == 1) {
                    Database.connection.commit();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Added new customer");
                    alert.setHeaderText(null);
                    alert.setContentText("New worker added correctly to database!\n" +
                            "Window will be closed!");
                    alert.showAndWait();

                    Stage stage = (Stage) this.addWorkerButton.getScene().getWindow();
                    stage.close();
                }
                else
                    Database.connection.rollback();
            } else {
                Database.connection.rollback();
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        Database.setAutoCommitTrue();
    }

    /**
     * Adds new login to administration panel
     * @return ID of login record from database
     */
    private int addLogin(){
        try{

            String sqlQuery = "INSERT INTO administration_panel (login, password) VALUES (?,?)";

            PreparedStatement preparedStatement = Database.connection.prepareStatement(sqlQuery,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,loginField.getText());
            preparedStatement.setString(2,passwordField.getText());

            int countRow = preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(countRow == 1 && resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return -1;
    }

    //CHECKING

    /**
     * Checks first name is written correctly
     * @return true if it's correct
     */
    private boolean checkFirstName(){
        if(firstNameField.getText().isEmpty()){
            message +="First name's field is empty!\n";
            return false;
        }

        String firstName = new String(firstNameField.getText());
        boolean checkedCorrectly = true;

        if(CheckingFormulas.checkTooShort(1,firstName)){
            message+="First name too short!\n";
            checkedCorrectly = false;
        }
        if(CheckingFormulas.checkTooLong(15,firstName)){
            message+="First name too long! (max 15 letters)\n";
            checkedCorrectly = false;
        }
        //checking if only letters
        if(!CheckingFormulas.checkOnlyLetters(firstName)){
            message +="First name contains digit(s)!\n";
            checkedCorrectly = false;
        }

        if(!checkedCorrectly)
            firstNameField.setText("");
        return checkedCorrectly;
    }

    /**
     * Checks last name is written correctly
     * @return true if it's correct
     */
    private boolean checkLastName(){
        if(lastNameField.getText().isEmpty()){
            message +="Last name's field is empty!\n";
            return false;
        }


        String lastName = new String(lastNameField.getText());
        boolean checkedCorrectly = true;
        if(CheckingFormulas.checkTooShort(1,lastName)){
            message +="Last name too short!\n";
            checkedCorrectly = false;
        }
        if(CheckingFormulas.checkTooLong(35,lastName)){
            message +="Last name too long! (max 35 letters)\n";
            checkedCorrectly = false;
        }
        //checking if only letters
        if(!CheckingFormulas.checkOnlyLetters(lastName)){
            message +="Last name contains digit(s)!\n";
            checkedCorrectly = false;
        }

        if(!checkedCorrectly)
            lastNameField.setText("");
        return checkedCorrectly;
    }

    /**
     * Firstly checks password is written correctly, then checks repeated password is same as first one
     * @return true if both passwords are correct and the same
     */
    private boolean checkPassword(){
        if(passwordField.getText().isEmpty()){
            message +="Password's field is empty!\n";
            return false;
        }

        String password = new String(passwordField.getText());
        String passwordRepeat = new String(repeatPasswordField.getText());
        boolean checkedCorrectly = true;

        if(CheckingFormulas.checkTooShort(4,password)){
            message +="Password too short! ( min 4 chars )\n";
            checkedCorrectly = false;
        }

        if(CheckingFormulas.checkTooLong(16,password)){
            message +="Password too long! ( max 16 )\n";
            checkedCorrectly = false;
        }

        if(!CheckingFormulas.checkContainDigitsAndLetters(password)){
            message +="Password must contains of digits AND letters!\n";
            checkedCorrectly = false;
        }

        // checking "checkedCorrectly" variable now says us if written password is correct
        // after that we know, that eventually problem is with repeated password, so we can leave passwordField uncleared
        if(checkedCorrectly){
            if(password.equals(passwordRepeat))
                return checkedCorrectly;
            else {
                if(this.repeatPasswordField.getText().isEmpty())
                    message +="Repeat password's field is empty!\n";
                else
                    message +="Something's wrong with repeated password!\n";
                this.repeatPasswordField.setText("");
                checkedCorrectly = false;
            }
        } else {
            passwordField.setText("");
            repeatPasswordField.setText("");
            checkedCorrectly = false;
        }
        return checkedCorrectly;
    }

    /**
     * Checks email is written correctly
     * @return true if it's correct
     */
    private boolean checkEmail(){
        if(this.emailField.getText().isEmpty()){
            message +="Email address' field is empty!\n";
            return false;
        }

        String email = new String(emailField.getText());
        boolean checkedCorrectly = true;

        if (!CheckingFormulas.checkEmailAddressFormat(email)){
            message +="Wrong email address' format!\n-> [ local-part@domain ] i.e. name@mail.com\n";
            checkedCorrectly = false;
        }

        if(CheckingFormulas.checkIsEmailUsed(email)){
            message +="This email address is already in use!\n";
            checkedCorrectly = false;
        }

        if(!checkedCorrectly)
            emailField.setText("");
        return checkedCorrectly;
    }

    /**
     * Checks phone number is written correctly
     * @return true if it's correct
     */
    private boolean checkPhoneNumber(){
        if(this.phoneField.getText().isEmpty()){
            message +="Phone number's field is empty!\n";
            return false;
        }

        String phoneNumber = new String(this.phoneField.getText());
        boolean checkedCorrectly = true;

        if(!CheckingFormulas.checkOnlyDigits(phoneNumber)){
            message +="Phone number should contains only digits!\n";
            checkedCorrectly = false;
        }

        if(!CheckingFormulas.checkLength(9,phoneNumber)){
            message +="Phone number consists of 9 digits!\n";
            checkedCorrectly = false;
        }

        if(!checkedCorrectly)
            phoneField.setText("");
        return checkedCorrectly;
    }

    /**
     * Checks login is written correctly
     * @return true if it's correct
     */
    private boolean checkLogin(){
        if(loginField.getText().isEmpty()){
            message +="Login's field is empty!\n";
            return false;
        }

        String login = new String(loginField.getText());
        boolean checkedCorrectly = true;

        if(CheckingFormulas.checkOnlyDigits(login)){
            message +="Login cannot contains only digits!\n";
            checkedCorrectly = false;
        }


        if(!checkedCorrectly)
            loginField.setText("");
        return checkedCorrectly;
    }

}
