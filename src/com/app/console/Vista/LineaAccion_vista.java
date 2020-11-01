package com.app.console.Vista;

import logicaEmpresarial.Ong;
import logicaEmpresarial.Usuario;

public class LineaAccion_vista {
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
