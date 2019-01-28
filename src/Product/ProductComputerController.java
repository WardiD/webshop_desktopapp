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

public class ProductComputerController implements Initializable {

    public static int id_product;

    @FXML
    private Label computerNameLabel;
    @FXML
    private Label computerBrandLabel;
    @FXML
    private Label computerModelLabel;
    @FXML
    private Label computerCPULabel;
    @FXML
    private Label computerCPUCoreLabel;
    @FXML
    private Label computerCPUSpeedLabel;
    @FXML
    private Label computerGraphicCardLabel;
    @FXML
    private Label computerGraphicMemoryLabel;
    @FXML
    private Label computerRAMLabel;
    @FXML
    private Label computerHDDLabel;
    @FXML
    private Label computerSSDLabel;
    @FXML
    private Label computerDVDLabel;
    @FXML
    private Label computerScreenLabel;
    @FXML
    private Label computerResolutionLabel;
    @FXML
    private Label computerPriceLabel;


    @Override
    public void initialize(URL url, ResourceBundle rb){
        showDescription();
    }

    public void showDescription(){
        String sqlQuery = "SELECT * FROM computerproductview d WHERE d.id_product = ?";
        try{
            PreparedStatement preparedStatement = Database.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id_product);

            ResultSet result = preparedStatement.executeQuery();
        //Double.toString(result.getDouble(5))
            // Integer.toString(result.getInt(8))

            if(result.next()){
                computerNameLabel.setText(result.getString(2));
                computerBrandLabel.setText(result.getString(3));
                computerModelLabel.setText(result.getString(4));
                computerCPULabel.setText(result.getString(5));
                computerCPUCoreLabel.setText(Integer.toString(result.getInt(6)));
                computerCPUSpeedLabel.setText(Double.toString(result.getDouble(7)));
                computerGraphicCardLabel.setText(result.getString(8));
                computerGraphicMemoryLabel.setText(Integer.toString(result.getInt(9)));
                computerRAMLabel.setText(Integer.toString(result.getInt(10)));
                computerHDDLabel.setText(Integer.toString(result.getInt(11)));
                computerSSDLabel.setText(Integer.toString(result.getInt(12)));
                computerDVDLabel.setText(Boolean.toString(result.getBoolean(13)));;
                computerScreenLabel.setText(Double.toString(result.getDouble(14)));
                computerResolutionLabel.setText(
                        Integer.toString(result.getInt(15))+"x"+result.getInt(16));
                computerPriceLabel.setText(Double.toString(result.getDouble(17)));
            }






        } catch (SQLException ex){
            ex.printStackTrace();
        }


    }
}
