package com.app.console;

import com.app.console.Vista.Personal_vista;
import com.app.console.Vista.Usuario_vista;
import com.app.console.Vista.Vista;
import dao.Dao;
import dao.DaoPersonal;
import dao.DaoUsuarios;
import logicaEmpresarial.*;

public class Main {
    private static Usuario usuarioAutentificado;

    public static void main(String[] args) {
        boolean userLogueado = false;
        //Inicio aplicacion.
        Usuario_vista VistaLogin=new Usuario_vista();    //Vista para loguear.
        DaoUsuarios ModelLogin = new DaoUsuarios();      //Dao para loguear.

        do {
            Usuario usuarioAutentificado = (Usuario) VistaLogin.PedirCredenciales();
            if (ModelLogin.Login(usuarioAutentificado.getHasing())) {
                //El usuario se ha logueado correctamente.
                VistaLogin.MostrarBienvenida(usuarioAutentificado);
            } else {
                //Error el usuario o la clave no es correcto.
                VistaLogin.MostrarError();
            }
        }while (!userLogueado);//Hasta que el usuario no se haya logueado no continuamos.


        //Mostrar menu
        

    }


    private Dao modelo;
    public static boolean  AbrirApartado(int apartado){
        String recogerEntrada;
        Dao modelo;
        Vista vista;
        System.out.println("En el apartado de gestion"+apartado);

        //Cargar modelo factory y llamar a visualizar
        modelo = getFactoyDao(apartado);
        vista = getVista(apartado);

        //Mientras el usuario quiera permanecer dentro del item repetimos.
        while(MostrarListado(modelo,vista));

        return false;
    }


    //Factory sobre modelos y vistas.
    private static Dao getFactoyDao(int apartado){
        switch (apartado){
            case 1: return new DaoPersonal();
            default:return  null;
        }
    }
    private static Vista getVista(int apartado){
        switch (apartado){
            case 1: return new Personal_vista();
            default:return  null;
        }
    }


    //Mientras sea true el usuario quiere seguir en el menu.
    private static boolean MostrarListado(Dao modelo, Vista vista){
        int accion;
        //Mostramos el listado y le pediremos accion.
        accion = vista.MostrarLIstado(modelo.RecogerLIstado());

        switch (accion){
            case 1:break;
            case 2:break;
            case 0:return false;
        }

        return true;
    }
    private static boolean MostarUno(){
        return true;
    }
    private static boolean Crear(){
        return false;
    }
    private static boolean Borrar(){
        return  false;
    }
}