package Loginapp;

import connectors.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginApp extends Application {

    public void start(Stage stage) throws Exception {
        System.out.println(1);
        Parent root = (Parent)FXMLLoader.load(getClass().getResource("/Loginapp/login.fxml"));
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

    }

    public static void main(String [] args){
        launch(args);
    }

}
