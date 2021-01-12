package com.app.desktop;

import com.app.console.Apartados;
import com.app.console.Vista.Menu_vista;
import com.sun.media.jfxmediaimpl.platform.Platform;
import dao.DaoSql;
import dao.IDao;
import javafx.fxml.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import logicaEmpresarial.Delegacion;
import logicaEmpresarial.Usuario;

import javax.servlet.jsp.el.ScopedAttributeELResolver;

public class LoginController implements Initializable {

    IDao modelo;
    private Usuario userAutenticado;

    private  Alert alertDialog = new Alert(Alert.AlertType.CONFIRMATION);

    public  void loadModel(IDao dao, Usuario userAutenticado){
        modelo = dao;
        this.userAutenticado=userAutenticado;
    }

    @FXML
    private TextField txtUser;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnEnter;


    @FXML
    private void login(ActionEvent event) throws IOException {

        userAutenticado.setNombre(txtUser.getText());
        userAutenticado.setPassword(txtPassword.getText());
        if(modelo.Login(userAutenticado)){

            Stage oldstage = (Stage) txtUser.getScene().getWindow();


            alertDialog.setAlertType(Alert.AlertType.CONFIRMATION);
            alertDialog.setContentText("Usuario correcto");
            alertDialog.showAndWait();



            if(modelo.descargaDatos(Apartados.DELEGACIONES)) {

                FXMLLoader loader = new FXMLLoader();
                Stage stage = new Stage();
                Parent root = loader.load(getClass().getResource("./views/Delegaciones.fxml").openStream());
                Delegaciones delegacionController = (Delegaciones) loader.getController();
                delegacionController.loadModel(modelo, userAutenticado);
                stage.setTitle("Delegaciones");
                stage.setScene(new Scene(root, 916, 554));
                stage.resizableProperty().setValue(false);

                stage.show();
                delegacionController.visualizarLista();
                oldstage.hide();

            }else{

                System.out.println("Se ha producido un erro al recuperar los datos");
                if(modelo.existeUnError())
                    System.out.println(modelo.getMensajeError());
            }


        }else{
            alertDialog.setAlertType(Alert.AlertType.ERROR);
            alertDialog.setTitle("Error de autenticación");
            alertDialog.setContentText("El usuario o la contraseña introducido no es correcto, porfavor revise que la ha introducido correctamente.");
            alertDialog.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
