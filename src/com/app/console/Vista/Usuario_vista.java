package com.app.console.Vista;

import logicaEmpresarial.Usuario;

import java.util.List;

public class Usuario_vista implements Vista {

    @Override
    public String MostrarLIstado(List listado,String salir, Usuario user) {
        return "0";
    }

    @Override
    public String MostrarUno(Object elemento, String salir, Usuario user) {
        return null;
    }

    @Override
    public Object Crear() {
        return null;
    }

    //Pide por consola usuario y password.
    public Object PedirCredenciales(){
        String entradaUser,entradaPass;
        System.out.println("Introduce el nombre de usuario:");
        entradaUser="prueba";

        System.out.println("Introduzca la contraseña:");
        entradaPass="pass";


        return new Usuario(entradaUser,entradaPass, Usuario.tipoUsuarios.USUARIO);
    }

    public void MostrarBienvenida(Usuario user){
        System.out.println("Bienvenido "+user.getNombre());

    }
    public void MostrarError(){
        System.out.println("El usuario o la clave introducidos no son correctos");
    }
}
