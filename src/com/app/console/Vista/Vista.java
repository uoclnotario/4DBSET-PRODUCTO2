package com.app.console.Vista;

import logicaEmpresarial.Ong;
import logicaEmpresarial.Personal;
import logicaEmpresarial.Usuario;

import java.text.ParseException;
import java.util.List;

public interface Vista {
    public String mostrarLIstado(List listado, String salir, Usuario user);
    public String mostrarUnElemento(Object elemento, String salir, Usuario user);
    public Object crearElemento(Ong datos, String PALABRACANCEALR);
    public Object modificarElemento(Ong datos, int indice, String PALABRACANCEALR);
}
