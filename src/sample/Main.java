package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logicaEmpresarial.Personal;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Inicio de Sesión");
        Personal persona;
        primaryStage.setScene(new Scene(root, 663, 400));
        primaryStage.resizableProperty().setValue(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
