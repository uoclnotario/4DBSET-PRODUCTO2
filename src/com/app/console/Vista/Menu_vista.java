package com.app.console.Vista;

import logicaEmpresarial.Usuario;

import java.awt.desktop.SystemEventListener;

public class Menu_vista {

    public String MostrarMenu(Usuario user, String salir) {

        int valorRecogido = 0;
        System.out.println("---SELECCIONE LA ACCION A SELECCIONAR---");
        System.out.println("\t-1\t INGRESOS ");
        System.out.println("\t-2\t PROYECTOS ");
        System.out.println("\t-3\t SOCIOS ");
        System.out.println("\t-4\t PERSONAL ");
        System.out.println("\t-5\t DELEGACIONES ");

        if(user.getRol() == Usuario.tipoUsuarios.ADMINISTRADOR)
            System.out.println("--6\t USUARIOS ");

        System.out.println("Escriba el indice del apartado o "+ salir + " para cerrar sesión:");
        return FuncionesConsola.leerConsola();
    }

    public  void MostrarErrorEntrada(int minimo, int maximo, String salir){
        System.out.println("El valor introducido no es correcto, debe de introducir de "+minimo+" A "+ maximo);
        System.out.println("o escriba" + salir +" para cerrar la sesión.");
    }

    public void DespedirUsuario(Usuario user){
        System.out.println("Adios "+user.getNombre());
    }

    public  void mensajeElementoCreado(boolean elementoCreado){

        if(elementoCreado){
            System.out.println("Se ha creado correctamente");
        }else{
            System.out.println("No se ha creado, debido a que se ha cancelado.");
        }

    }

    public  void mensajeElementoEditado(boolean elementoCreado){

        if(elementoCreado){
            System.out.println("Se ha Modificado correctamente");
        }else{
            System.out.println("No se ha podido modificar.");
        }

    }

    public void mensajeError(String error){
        System.out.println("Se ha producido un error: "+error);
        System.out.println("La aplicación no pudec continuar.");
    }

}
