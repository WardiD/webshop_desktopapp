package Loginapp;

import connectors.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Application starting class
 */
public class LoginApp extends Application {
    /**
     *
     * @param stage window of application
     */
    public void start(Stage stage){
        try {
            System.out.println(1);
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("/Loginapp/login.fxml"));
            System.out.println(2);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Electronics");
            stage.setResizable(false);

            stage.setOnCloseRequest(e -> Close.closeProgram());

            Platform.setImplicitExit(true);

            SSH.connectSSH();
            Database.connectToDatabase();

            stage.show();
        } catch ( Exception ex){
            ex.printStackTrace();
        }

    }

    /**
     *
     * @param args array of starting application arguments
     */
    public static void main(String [] args){
        launch(args);
    }

}
