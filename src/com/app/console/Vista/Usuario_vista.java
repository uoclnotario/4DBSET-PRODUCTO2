package com.app.console.Vista;

import logicaEmpresarial.Usuario;

import java.util.List;

public class Usuario_vista implements Vista {

    @Override
    public int MostrarLIstado(List listado) {
        return 0;
    }

    @Override
    public Object Crear() {
        return null;
    }

    //Pide por consola usuario y password.
    public Object PedirCredenciales(){
        Usuario newUser = new Usuario();
        newUser.setNombre("Usuario");
        newUser.setPassword("prueba");
        return newUser;
    }

    public void MostrarBienvenida(Usuario user){
        System.out.println("Bienvenido "+user.getNombre());

    }
    public void MostrarError(){
        System.out.println("El usuario o la clave introducidos no son correctos");
    }
}
