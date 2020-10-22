package com.app.console.Vista;

import logicaEmpresarial.Personal;
import logicaEmpresarial.Usuario;

import java.util.List;

public interface Vista {
    public String MostrarLIstado(List listado, String salir, Usuario user);
    public String MostrarUno(Object elemento, String salir, Usuario user);
    /*/
    -Mostrar un elemnto.
    -Crear un nuevo elemento.

    -modificar un elemento.

    -Eliminar un elemento.


    apartados:
        -Personal.
    */

    public Object Crear();
}
