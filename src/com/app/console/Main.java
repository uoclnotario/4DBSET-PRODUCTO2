package com.app.console;

import com.app.console.AppConsole;
import com.app.console.Vista.Menu_vista;
import com.app.console.Vista.Vista;
import dao.IDao;
import logicaEmpresarial.Usuario;


public class Main {
    private final String PALABRAPARAVOLVER = "v";
    private Usuario usuarioAutentificado;
    private Menu_vista vistaMenu;
    private IDao modelo;//Modelo seleccionado por el usuario en el que se encuentra actualmente la aplicacion.
    private Vista vista;//Vista seleccionada por el usuario en el que se encuenta actualmente al acpliacion.


    public static void main(String[] args) {

        // Busca la base de datos XML en la maquina local. Si no la encuentra, la crea.
        //GestionFichero.buscaXML();

        AppConsole init = new AppConsole();

    }
}
