package Product;

import connectors.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProductMobileController implements Initializable {
    public static int id_product;

    @FXML
    private Label mobileNameLabel;

    @FXML
    private Label mobileBrandLabel;

    @FXML
    private Label mobileModelLabel;

    @FXML
    private Label mobileCPUCoresLabel;

    @FXML
    private Label mobileCPUSpeedLabel;

    @FXML
    private Label mobileSizeLabel;

    @FXML
    private Label mobileResolutionLabel;

    @FXML
    private Label mobileCameraFrontLabel;

    @FXML
    private Label mobileCameraBackLabel;

    @FXML
    private Label mobileInternalLabel;

    @FXML
    private Label mobileExternalLabel;

    @FXML
    private Label mobileFingerprintLabel;

    @FXML
    private Label mobilePriceLabel;


    @Override
    public void initialize(URL url, ResourceBundle rb){
        showDescription();
    }

    public void showDescription(){
        String sqlQuery = "SELECT * FROM mobileproductview d WHERE d.id_product = ?";
        try{
            PreparedStatement preparedStatement = Database.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id_product);

            ResultSet result = preparedStatement.executeQuery();

            if(result.next()) {
                mobileNameLabel.setText(result.getString(2));
                mobileBrandLabel.setText(result.getString(3));
                mobileModelLabel.setText(result.getString(4));
                mobileSizeLabel.setText(Double.toString(result.getDouble(5)));
                mobileCPUCoresLabel.setText(Integer.toString(result.getInt(6)));
                mobileCPUSpeedLabel.setText(Double.toString(result.getDouble(7)));
                mobileCameraFrontLabel.setText(Integer.toString(result.getInt(8)));
                mobileCameraBackLabel.setText(Integer.toString(result.getInt(9)));
                mobileInternalLabel.setText(Integer.toString(result.getInt(10)));
                mobileExternalLabel.setText(Integer.toString(result.getInt(11)));
                mobileFingerprintLabel.setText(Boolean.toString(result.getBoolean(12)));
                mobileResolutionLabel.setText(
                        Integer.toString(result.getInt(13))+"x"+result.getInt(14));
                mobilePriceLabel.setText(Double.toString(result.getDouble(15)));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }


    }

}
