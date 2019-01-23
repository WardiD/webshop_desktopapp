package NewCustomer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class NewCustomerController implements Initializable {
    String warningStatement = new String();
    NewCustomer newAccount;

    @FXML
    private Label warningBoxLabel;

    @FXML
    private Button createCustomerButton;

    // form
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordRepeatField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField streetAddressField;
    @FXML
    private TextField apartmentField;
    @FXML
    private TextField zipCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField countryField;
    @FXML
    private TextField moneyField;

    @FXML
    private Label tipsLabel;

    @FXML
    private Button clearButton;

    public void initialize(URL url, ResourceBundle rb){
        tipsLabel.setText("TIPS: \n* fields are obligatory! \nPassword :\nmin - 4 chars, max - 16 chars,\n" +
                "must consists of digits and letters\n");
    }
    // publick checking if fields are empty
    // used in creating new account

    public boolean isPhoneNumberFieldEmpty(){
        return this.phoneNumberField.getText().isEmpty();
    }

    public boolean isApartmentFieldEmpty(){
        return this.apartmentField.getText().isEmpty();
    }


// CHECKING FORM
    private boolean checkFirstName(){
        if(this.firstNameField.getText().isEmpty()){
            warningStatement +="First name's field is empty!\n";
            return false;
        }

        String firstName = new String(this.firstNameField.getText());
        boolean checkedCorrectly = true;

        if(CheckingFormulas.checkTooShort(1,firstName)){
            this.warningStatement +="First name too short!\n";
            checkedCorrectly = false;
        }
        if(CheckingFormulas.checkTooLong(15,firstName)){
            this.warningStatement +="First name too long! (max 15 letters)\n";
            checkedCorrectly = false;
        }
        //checking if only letters
        if(!CheckingFormulas.checkOnlyLetters(firstName)){
            this.warningStatement +="First name contains digit(s)!\n";
            checkedCorrectly = false;
        }

        if(!checkedCorrectly)
            firstNameField.setText("");
        return checkedCorrectly;
    }

    private boolean checkLastName(){
        if(this.lastNameField.getText().isEmpty()){
            warningStatement +="Last name's field is empty!\n";
            return false;
        }


        String lastName = new String(this.lastNameField.getText());
        boolean checkedCorrectly = true;
        if(CheckingFormulas.checkTooShort(1,lastName)){
            this.warningStatement +="Last name too short!\n";
            checkedCorrectly = false;
        }
        if(CheckingFormulas.checkTooLong(35,lastName)){
            this.warningStatement +="Last name too long! (max 35 letters)\n";
            checkedCorrectly = false;
        }
        //checking if only letters
        if(!CheckingFormulas.checkOnlyLetters(lastName)){
            this.warningStatement +="Last name contains digit(s)!\n";
            checkedCorrectly = false;
        }

        if(!checkedCorrectly)
            lastNameField.setText("");
        return checkedCorrectly;
    }

    private boolean checkPassword(){
        if(this.passwordField.getText().isEmpty()){
            warningStatement +="Password's field is empty!\n";
            return false;
        }

        String password = new String(this.passwordField.getText());
        String passwordRepeat = new String(this.passwordRepeatField.getText());
        boolean checkedCorrectly = true;

        if(CheckingFormulas.checkTooShort(4,password)){
            warningStatement +="Password too short! ( min 4 chars )\n";
            checkedCorrectly = false;
        }

        if(CheckingFormulas.checkTooLong(16,password)){
            warningStatement +="Password too long! ( max 16 )\n";
            checkedCorrectly = false;
        }

        if(!CheckingFormulas.checkContainDigitsAndLetters(password)){
            warningStatement +="Password must contains of digits AND letters!\n";
            checkedCorrectly = false;
        }

        // checking "checkedCorrectly" variable now says us if written password is correct
        // after that we know, that eventually problem is with repeated password, so we can leave passwordField uncleared
        if(checkedCorrectly){
            if(password.equals(passwordRepeat))
                return checkedCorrectly;
            else {
                if(this.passwordRepeatField.getText().isEmpty())
                    warningStatement +="Repeat password's field is empty!\n";
                else
                    warningStatement +="Something's wrong with repeated password!\n";
                this.passwordRepeatField.setText("");
                checkedCorrectly = false;
            }
        } else {
            passwordField.setText("");
            passwordRepeatField.setText("");
            checkedCorrectly = false;
        }
        return checkedCorrectly;
    }

    private boolean checkEmail(){
        if(this.emailField.getText().isEmpty()){
            warningStatement +="Email address' field is empty!\n";
            return false;
        }

        String email = new String(this.emailField.getText());
        boolean checkedCorrectly = true;

        if (!CheckingFormulas.checkEmailAddressFormat(email)){
            warningStatement +="Wrong email address' format!\n-> [ local-part@domain ] i.e. name@mail.com\n";
            checkedCorrectly = false;
        }

        if(CheckingFormulas.checkIsEmailUsed(email)){
            warningStatement +="This email address is already in use!\n";
            checkedCorrectly = false;
        }

        if(!checkedCorrectly)
            emailField.setText("");
        return checkedCorrectly;
    }

    private boolean checkPhoneNumber(){
        if(this.phoneNumberField.getText().isEmpty())
            return true;

        String phoneNumber = new String(this.phoneNumberField.getText());
        boolean checkedCorrectly = true;

        if(!CheckingFormulas.checkOnlyDigits(phoneNumber)){
            warningStatement +="Phone number should contains only digits!\n";
            checkedCorrectly = false;
        }

        if(!CheckingFormulas.checkLength(9,phoneNumber)){
            warningStatement +="Phone number consists of 9 digits!\n";
            checkedCorrectly = false;
        }

        if(!checkedCorrectly)
            phoneNumberField.setText("");
        return checkedCorrectly;
    }

    private boolean checkStreetAddress(){
        if(this.streetAddressField.getText().isEmpty()){
            warningStatement +="Street address' field is empty!\n";
            return false;
        }

        String streetAddress = new String(this.streetAddressField.getText());
        boolean checkedCorrectly = true;

        if(!CheckingFormulas.checkIsStreetAddress(streetAddress)){
            warningStatement +="Street address is incorrect!\n" +
                    "->  [ street's name + \"  \" + building's number ]\n";
            checkedCorrectly = false;
        }

        if(!checkedCorrectly)
            streetAddressField.setText("");
        return checkedCorrectly;
    }

    //Apartment can be represented by many ways:
    // numbers, numbers + letters, only letters etc
    // so it can be leave by another checkings for this moment
    private boolean checkApartment(){
        if(this.apartmentField.getText().isEmpty())
            return true;
        return true;
                /*
        String apartment = this.apartmentField.getText();
        //boolean checkedCorrectly = true;



        if(!checkedCorrectly)
            phoneNumberField.setText("");
        return checkedCorrectly;
        */
    }

    private boolean checkZipCode(){
        if(this.zipCodeField.getText().isEmpty()){
            warningStatement +="ZIP Code's field is empty!\n";
            return false;
        }


        String zipCode = new String(this.zipCodeField.getText());
        boolean checkedCorrectly = true;

        if(!CheckingFormulas.checkOnlyDigits(zipCode)){
            warningStatement += "ZIP Code should contains only digits!\n";
            checkedCorrectly = false;
        }

        if(!CheckingFormulas.checkLength(5, zipCode)){
            warningStatement += "ZIP Code's length is equal to 5 digits!\n";
            checkedCorrectly = false;
        }

        if(!checkedCorrectly)
            zipCodeField.setText("");
        return checkedCorrectly;
    }

    private boolean checkCity(){
        if(this.cityField.getText().isEmpty()){
            warningStatement +="City's field is empty!\n";
            return false;
        }

        String city = new String(this.cityField.getText());
        boolean checkedCorrectly = true;

        if(!CheckingFormulas.checkOnlyLetters(city)){
            warningStatement += "City's name cannot contains number(s)!\n";
            checkedCorrectly = false;
        }

        if(!checkedCorrectly)
            cityField.setText("");
        return checkedCorrectly;
    }

    private boolean checkCountry(){
        if(this.countryField.getText().isEmpty()){
            warningStatement +="Country's field is empty!\n";
            return false;
        }

        String country = new String(this.countryField.getText());
        boolean checkedCorrectly = true;

        if(!CheckingFormulas.checkOnlyLetters(country)){
            warningStatement += "Country's name cannot contains number(s)!\n";
            checkedCorrectly = false;
        }

        if(!checkedCorrectly)
            countryField.setText("");
        return checkedCorrectly;
    }

    private boolean checkMoney(){
        if(this.moneyField.getText().isEmpty()){
            warningStatement +="Money's field is empty!\n";
            return false;
        }

        String money = new String(this.moneyField.getText());
        boolean checkedCorrectly = true;

        if(!CheckingFormulas.checkOnlyDigits(money)){
            warningStatement += "Amount of money must be a number!";
            checkedCorrectly = false;
        }

        try {
            if(!CheckingFormulas.checkGreaterThanZero(money));
        } catch (Exception ex){
            warningStatement += "Amount of money must be greater than zero!\n";
            checkedCorrectly = false;
        }


        if(!checkedCorrectly)
            moneyField.setText("");
        return checkedCorrectly;
    }

    private boolean checkForm(){
        boolean isFormCorrect = false;
        isFormCorrect = checkFirstName();
        isFormCorrect = checkLastName();
        isFormCorrect = checkPassword();
        isFormCorrect = checkEmail();
        isFormCorrect = checkPhoneNumber();
        isFormCorrect = checkStreetAddress();
        isFormCorrect = checkApartment();
        isFormCorrect = checkZipCode();
        isFormCorrect = checkCity();
        isFormCorrect = checkCountry();
        isFormCorrect = checkMoney();

        warningBoxLabel.setTextFill(Color.RED);
        warningBoxLabel.setText(warningStatement);
        return isFormCorrect;
    }


    @FXML
    public void createCustomer(){
        warningStatement = new String();
        //System.out.println("is correct? "+checkForm());
        if(checkForm()) {

            warningBoxLabel.setTextFill(Color.GREEN);
            warningBoxLabel.setText("Everything is right! I'm creating new account...");

            newAccount = new NewCustomer(this.firstNameField.getText(),
                    this.lastNameField.getText(),
                    this.passwordField.getText(),
                    Double.parseDouble(this.moneyField.getText()),
                    this.emailField.getText(),
                    isPhoneNumberFieldEmpty() ? 0 : Integer.parseInt(this.phoneNumberField.getText()),
                    this.streetAddressField.getText(),
                    this.apartmentField.getText(),
                    this.cityField.getText(),
                    this.countryField.getText(),
                    Integer.parseInt(this.zipCodeField.getText()),
                    this.isApartmentFieldEmpty(),
                    this.isPhoneNumberFieldEmpty());

            System.out.println(newAccount + "\n    ---------- \n");
            if (newAccount.addCustomerToDatabase()) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Added new customer");
                alert.setHeaderText(null);
                alert.setContentText("New customer added correctly to database!" +
                        "Window will be closed!");
                alert.showAndWait();

                Stage stage = (Stage) this.warningBoxLabel.getScene().getWindow();
                stage.close();

            } else {
                this.warningBoxLabel.setText(this.warningBoxLabel.getText()
                        + "\nProblem with adding new customer!");
            }
        }
    }

    @FXML
    public void clearForm(){
        firstNameField.setText("");
        lastNameField.setText("");
        passwordField.setText("");
        passwordRepeatField.setText("");
        emailField.setText("");
        phoneNumberField.setText("");
        streetAddressField.setText("");
        apartmentField.setText("");
        zipCodeField.setText("");
        cityField.setText("");
        countryField.setText("");
        moneyField.setText("");
    }

}
