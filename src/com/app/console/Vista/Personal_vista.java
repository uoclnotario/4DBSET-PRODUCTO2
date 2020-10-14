package com.app.console.Vista;

import logicaEmpresarial.Identificacion;
import logicaEmpresarial.Personal;

import java.util.List;

public class Personal_vista implements Vista {

    public int MostrarLIstado(List listado){
        List<Personal> personal = (List<Personal>)listado;
        System.out.println("Listado de personal:");
        System.out.println("Nombre\t Apellido");

        for(int i = 0; i < personal.size();i++)
            System.out.println(personal.get(i).getGetIdentificacion().getNombre()+ "\t"+personal.get(i).getGetIdentificacion().getNif_dni());

        System.out.println("Indique que desea realizar:");
        System.out.println("\t- 0 Volver Atras.");
        System.out.println("\t- 1 Ver personal del listado.");
        System.out.println("\t- 2 Añadir un nuevo personal.");

        return 0;
    }

    public Object Crear(){
        Personal retorno = new Personal(null,
                                        null,
                                        false,
                                        new Identificacion("12345",
                                                            "nombre",
                                                            null,
                                                            "domicilio",
                                                Identificacion.Tipo.PERSONA)
                                        );
        return retorno;
    }
}
