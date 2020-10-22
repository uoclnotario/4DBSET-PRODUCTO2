package com.app.console.Vista;

import logicaEmpresarial.Usuario;

public class Menu_vista {

    public String MostrarMenu(Usuario user, String salir) {

        int valorRecogido = 0;
        System.out.println("---SELECCIONE LA ACCION A SELECCIONAR---");
        System.out.println("--1\t PERSONAL ");

        if(user.getRol() == Usuario.tipoUsuarios.ADMINISTRADOR)
            System.out.println("--2\t USUARIOS ");

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
}
