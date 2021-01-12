package com.app.desktop;

import dao.FactoryDAO;
import dao.IDao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logicaEmpresarial.Usuario;

public class Main extends Application {

    private IDao modelo;//Modelo seleccionado por el usuario en el que se encuentra actualmente la aplicacion.
    private Usuario usuarioAutintificado;

    @Override
    public void start(Stage primaryStage) throws Exception{

        modelo = FactoryDAO.loadModel(FactoryDAO.typeDao.SQL);
        if(modelo !=null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getResource("./views/Login.fxml").openStream());
                LoginController loadLogginController = (LoginController)loader.getController();
                usuarioAutintificado = new Usuario();
                loadLogginController.loadModel(modelo,usuarioAutintificado);


                primaryStage.setTitle("Inicio de Sesión");
                primaryStage.setScene(new Scene(root, 663, 400));
                primaryStage.resizableProperty().setValue(false);
                primaryStage.show();

            } catch (Exception ex) {
                System.out.println("Se ha producido un erro inesperado:"+ex.getMessage());
            }
        }else{
            System.out.println("Se ha producido un error");
        }


    }


    public static void main(String[] args) {
        launch(args);
    }
}
