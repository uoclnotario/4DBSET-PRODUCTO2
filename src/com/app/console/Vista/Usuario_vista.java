package com.app.console.Vista;

import logicaEmpresarial.Ong;
import logicaEmpresarial.Personal;
import logicaEmpresarial.Usuario;

import java.util.List;
import java.util.Optional;

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
    public Object Crear(Ong datos, String PALABRACANCELAR) {
        return null;
    }

    @Override
    public Object Modificar(Ong datos, int indice, String PALABRACANCELAR) {
        return null;
    }



}
