package com.app.console.Vista;

import logicaEmpresarial.Delegacion;
import logicaEmpresarial.Ong;
import logicaEmpresarial.Personal;
import logicaEmpresarial.Usuario;
import javax.print.DocFlavor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;


public class Delegaciones_vista implements Vista {
    @Override
    public String MostrarLIstado(List listado, String salir, Usuario user) {

        if(listado == null){

    }else{
        List<Delegacion> delegacion = (List<Delegacion>)listado;
        System.out.println("Listado de las delegaciones:");
        System.out.println("\tINDICE\tNOMBRE\tDIRECCION");

        for(int i = 0; i < delegacion.size();i++)
            System.out.println("\t"+(i+1)+"\t"+delegacion.get(i).getNombre()+ "\t"+delegacion.get(i).getDireccion());



        System.out.println("Indique que desea realizar:");
        System.out.println("\t- Indique el indice del usuario a visualizar o modificar ");
        System.out.println("\t- 0 Crear un nuevo.");
        System.out.println("\t- Escriba "+salir+" para volver al menu");

        }


        return FuncionesConsola.leerConsola();
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
