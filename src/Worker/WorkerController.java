package Worker;

import connectors.Close;
import connectors.Database;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class WorkerController implements Initializable {
    public static int id_product;

    @FXML
    private AnchorPane ap;

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

// INIT
    @Override
    public void initialize(URL url, ResourceBundle rb){



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


    public void showDescription(){
        // [EDYCJA]
        String sqlQuery = "SELECT * FROM description_mobile d JOIN resolution r ON d.id_resolution=r.id_resolution WHERE d.id_product = ?";
        try{
            PreparedStatement preparedStatement = Database.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id_product);

            ResultSet result = preparedStatement.executeQuery();

            if(result.next()) {
                mobileNameLabel.setText();
                mobileBrandLabel;
                mobileModelLabel;
                mobileCPUCoresLabel;
                mobileCPUSpeedLabel;
                mobileSizeLabel;
                mobileCameraFrontLabel;
                mobileCameraBackLabel;
                mobileInternalLabel;
                mobileExternalLabel;
                mobileFingerprintLabel;
                mobilePriceLabel;
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }


    }





}
