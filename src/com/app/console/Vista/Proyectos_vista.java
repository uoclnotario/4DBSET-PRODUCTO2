package com.app.console.Vista;

import logicaEmpresarial.Ong;
import logicaEmpresarial.Usuario;

import java.util.List;

public class Proyectos_vista implements Vista{
    @Override
    public String MostrarLIstado(List listado, String salir, Usuario user) {
        return null;
    }

    @Override
    public String MostrarUno(Object elemento, String salir, Usuario user) {
        return null;
    }

    @Override
    public Object Crear(Ong datos, String PALABRACANCEALR) {
        return null;
    }

    @Override
    public Object Modificar(Ong datos, int indice, String PALABRACANCEALR) {
        return null;
    }
}
