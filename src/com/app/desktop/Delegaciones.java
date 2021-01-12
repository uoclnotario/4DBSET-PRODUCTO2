package com.app.desktop;

import com.app.console.Apartados;
import dao.IDao;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import logicaEmpresarial.Delegacion;
import logicaEmpresarial.Usuario;


public class Delegaciones implements Initializable {

    IDao modelo;
    private Usuario userAutenticado;
    private  Alert alertDialog = new Alert(Alert.AlertType.CONFIRMATION);
    private Delegacion delegacionSeleccionada;


    @FXML
    private Button btnCrear;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;


    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtTelefono;

    @FXML
    private  TableView tvDelegaciones;

    @FXML
    private TableColumn colNombre;

    @FXML
    private TableColumn colDireccion;

    @FXML
    private TableColumn colTelefono;


    @FXML
    private void save(ActionEvent event) throws IOException {
        insertar();
    }

    @FXML
    private void modificar(ActionEvent event) throws IOException {
        update();
    }

    @FXML
    private void borrar(ActionEvent event) throws IOException {
        delete();
    }


    public  void loadModel(IDao dao, Usuario userAutenticado){
        modelo = dao;
        this.userAutenticado=userAutenticado;
    }

    public void visualizarLista(){
        delegacionSeleccionada = null;

        if(modelo.descargaDatos(Apartados.DELEGACIONES)){

            btnEliminar.setDisable(true);
            btnModificar.setDisable(true);

            ObservableList<Delegacion> listDelegaciones = FXCollections.observableArrayList(modelo.recogerListado(Apartados.DELEGACIONES));

            colNombre.setCellValueFactory(new PropertyValueFactory<Delegacion, String>("nombre"));
            colDireccion.setCellValueFactory(new PropertyValueFactory<Delegacion, String>("direccion"));
            colTelefono.setCellValueFactory(new PropertyValueFactory<Delegacion, String>("telefono"));
            tvDelegaciones.setItems(listDelegaciones);

        }else{
            //Muestra un alert indicando errro en la descarga de los datos.
        }


    }

    public  void insertar(){

        if(delegacionSeleccionada == null){
            Delegacion nueva = new Delegacion();
            nueva.setTelefono(txtTelefono.getText());
            nueva.setNombre(txtNombre.getText());
            nueva.setDireccion(txtDireccion.getText());
            try{
                if(modelo.crear(nueva,Apartados.DELEGACIONES)){
                    visualizarLista();
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
           delegacionSeleccionada = null;
           txtTelefono.setText("");
           txtDireccion.setText("");
           txtNombre.setText("");
           btnCrear.setText("Crear");
        }

    }


    public void update(){
        if(delegacionSeleccionada != null){
            delegacionSeleccionada.setNombre(txtNombre.getText());
            delegacionSeleccionada.setTelefono(txtTelefono.getText());
            delegacionSeleccionada.setDireccion(txtDireccion.getText());

            if(modelo.modificar(delegacionSeleccionada,delegacionSeleccionada.getIndice(),Apartados.DELEGACIONES)){
                visualizarLista();
            }
        }
    }


    public void delete(){
        if(delegacionSeleccionada!=null){
            if(modelo.borrar(delegacionSeleccionada.getIndice(),Apartados.DELEGACIONES)){
                visualizarLista();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvDelegaciones.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue observableValue, Object oldValue, Object newValue) -> {
                    Delegacion selected = (Delegacion)newValue;
                    if(selected != null){
                        btnCrear.setText("Insertar nuevo");
                        btnEliminar.setDisable(false);
                        btnModificar.setDisable(false);
                        this.delegacionSeleccionada = selected;
                        txtDireccion.setText(selected.getDireccion());
                        txtNombre.setText(selected.getNombre());
                        txtTelefono.setText(selected.getTelefono());
                    }
                });
    }

}
