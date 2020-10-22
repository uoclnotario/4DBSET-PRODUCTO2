package com.app.console.Vista;

import logicaEmpresarial.Identificacion;
import logicaEmpresarial.Personal;
import logicaEmpresarial.Usuario;

import java.util.List;

public class Personal_vista implements Vista {
    @Override
    public String MostrarLIstado(List listado, String salir, Usuario user){
        List<Personal> personal = (List<Personal>)listado;
        System.out.println("Listado de personal:");
        System.out.println("\tINDICE\tNOMBRE\tDNI");

        for(int i = 0; i < personal.size();i++)
            System.out.println(i+1.+"\t"+personal.get(i).getGetIdentificacion().getNombre()+ "\t"+personal.get(i).getGetIdentificacion().getNif_dni());

        System.out.println("Indique que desea realizar:");
        System.out.println("\t- Indique el indice del usuario a visualizar o modificar ");
        System.out.println("\t- 0 Crear un nuevo.");
        System.out.println("\t- Escriba "+salir+" para volver al menu");

        return FuncionesConsola.leerConsola();
    }

    @Override
    public String MostrarUno(Object elemento, String salir, Usuario user) {

        System.out.println("---MOSTRANDO DATOS DE PERSONA---");
        MostrarDatosPersona((Personal)elemento);

        System.out.println("Indique que desea realizar:");;
        System.out.println("\t- 0 Modificar esta persona.");
        System.out.println("\t- 1 Borrar ha esta persona.");

        System.out.println("\t- Escriba "+salir+" para volver atras.");

        return FuncionesConsola.leerConsola();
    }


    private void MostrarDatosPersona(Personal persona){
        System.out.println("");
        System.out.println("Nombre:\t"+ persona.getGetIdentificacion().getNombre());
        System.out.println("Fecha de nacimiento:\t"+ persona.getGetIdentificacion().getFechaDeNacimiento());
        System.out.println("Domicilio:\t"+ persona.getGetIdentificacion().getFechaDeNacimiento());
    }

    //a partir de aqui tenemos que traernos las funciones de FUNCIONESCONSOLA para verificar en la propia vista que
    //los datos que esta introuciendo el usuario sean correctos, de no serlo le hacemos bucle como en la aplicación de arriba
    //hasta que o canecele escribiendo la palabra de salir, o meta bien el valor, si cancela o le da exit el objeto de vuelvo nulo
    //de esta manera en el controlador vamos a saber si es nulo es que el usuario a cancelado la creación.

    //Modificar y crear casi son lo mismo, quizas la diferenecia es qeu en modifcar le podemos mostrar en pantalla
    //el valor que ya tiene y que si la da a intro y lo deja vacio se quede el valor que estaba

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

    public Object Modificar(){
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
