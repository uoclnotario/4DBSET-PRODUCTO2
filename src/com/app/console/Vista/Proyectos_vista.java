package com.app.console.Vista;

import logicaEmpresarial.*;

import javax.print.DocFlavor;
import java.awt.desktop.SystemEventListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Proyectos_vista implements Vista{

    @Override
    public String MostrarLIstado(List listado, String salir, Usuario user){

        if(listado == null){

        }else{
            List<Proyecto> proyectos = (List<Proyecto>)listado;
            System.out.println("Listado de proyectos:");
            System.out.printf("%-10s %-10s %-10s %-10s %-10s\n", "INDICE", "NOMBRE", "ID","TIPO","DELEGACIÓN");

            for(int i = 0; i < proyectos.size();i++){
                String del="No asignado";

                if(proyectos.get(i).getDelegacion() != null){
                    del = proyectos.get(i).getDelegacion()getNombre();
                }

                System.out.printf("%-10s %-10s %-10s %-10s %-10s\n", +(i+1),proyectos.get(i).getNombre(), proyectos.get(i).getId(),proyectos.get(i).getTipo(),del);
            }
            System.out.println("Indique que desea realizar:");
            System.out.println("\t- Indique el indice del proyecto a visualizar o modificar ");
            System.out.println("\t- 0 Crear un nuevo.");
            System.out.println("\t- Escriba "+salir+" para volver al menu");
        }
        return FuncionesConsola.leerConsola();
    }




    @Override
    public String MostrarUno(Object elemento, String salir, Usuario user) {

        System.out.println("---MOSTRANDO DATOS DE PROYECTO---");
        MostrarDato((Proyecto)elemento);

        System.out.println("Indique que desea realizar:");;
        System.out.println("\t- 0 Modificar este proyecto.");
        System.out.println("\t- 1 Borrar ha este proyecto.");

        System.out.println("\t- Escriba "+salir+" para volver atras.");

        return FuncionesConsola.leerConsola();
    }
    public void MostrarDato(Proyecto proyecto){

        DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        System.out.printf("%-5s %-5s\n", "Nombre:", proyecto.getNombre());
        System.out.printf("%-5s %-5s\n", "Identificador:", proyecto.getId());
        if(proyecto.getFechaDeInicio() != null)
            System.out.printf("%-5s %-5s\n", "Fecha de inicio:", formato.format(proyecto.getFechaDeInicio()));
        System.out.printf("%-5s %-5s\n", "Delegacion:", proyecto.getDelegacion());
        System.out.printf("%-5s %-5s\n", "Tipo:", proyecto.getTipo());

        if(proyecto.getFechaDeInicio() != null)
            System.out.printf("%-5s %-5s\n", "Fecha de Alta:", formato.format(proyecto.getFechaDeInicio()));

        if(proyecto.getEstado()){
            System.out.printf("%-5s %-5s\n", "Estado:", "Alta");
        }else{
            System.out.printf("%-5s %-5s\n", "Estado:", "Baja");
        }

        if(proyecto.getDelegacion() != null){
            System.out.printf("%-5s %-5s\n", "Delegación:", proyecto.getDelegacion().getNombre());
        }else{
            System.out.printf("%-5s %-5s\n", "Delegación:", "No asignado.");
        }


    }

    @Override
    public Object Crear(Ong datos, String PALABRACANCEALR) {
        return solicitarNuevo(datos,-1, PALABRACANCEALR);
    }

    @Override
    public Object Modificar(Ong datos,int indice, String PALABRACANCEALR){
        return solicitarNuevo(datos,indice,PALABRACANCEALR);
    }
    private Object solicitarNuevo(Ong datos, int indice, String PALABRACANCELAR){
        Proyecto nuevoProyecto;
        String entradaTexto;
        int entradaNumero;
        boolean esMOdificacion = indice != -1;

        System.out.println("Creacion de nuevo proyecto:");
        System.out.println("Seleccione el tipo:");
        System.out.println("\t 1-Nacional");
        System.out.println("\t 2-Internacional");





        if(esMOdificacion){
            String valor = "";
            switch (datos.getProyectos().get(indice).getClass().getName()) {
                case "Nacional":valor = "1";break;
                case "Internacional":valor= "2";break;

            }

            System.out.println("Seleccione el numero del tipo de proyecto:["+valor+"]");
        }else
            System.out.println("Seleccione el numero del tipo de proyecto:");

        entradaTexto= FuncionesConsola.forzarEntradaNumero(FuncionesConsola.MASCARANUMERO,
                FuncionesConsola.comprobaConversion.ENTERO,
                PALABRACANCELAR,
                1,
                2);


        entradaNumero = Integer.parseInt(entradaTexto);

        switch (entradaNumero) {
            case 1: nuevoProyecto = new Nacional();break;
            case 2: nuevoProyecto =new Internacional();break;

            default :
                System.out.println("Se ha producido un error");
                return null;

        }


        //Nombre
        if(esMOdificacion)
            System.out.println("Inserte Nombre:"+datos.getProyectos().get(indice).getNombre()+"]");
        else
            System.out.println("Inserte Nombre:");

        entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARATEXTO,
                FuncionesConsola.comprobaConversion.TEXTO,
                PALABRACANCELAR,
                esMOdificacion);
        if(entradaTexto != null) {
            if(entradaTexto.equals("(default)"))
                nuevoProyecto.setNombre(datos.getProyectos().get(indice).getNombre());
            else
                nuevoProyecto.setNombre(entradaTexto);
        }else{
            return null;
        }


        if(datos.getDelegaciones().size() > 0){

            for(int i = 0; i < datos.getDelegaciones().size();i++)
                System.out.println(i+1.+"\t"+datos.getDelegaciones().get(i).getNombre());
            System.out.println("0- No seleccionar nada.");


            if(esMOdificacion)
                if(datos.getProyectos().get(indice).getDelegacion() != null){
                    System.out.println("Seleccione la delegación en la que está asginado:["+datos.getProyectos().get(indice).getDelegacion().getNombre()+"]");
                }else{
                    System.out.println("Seleccione la delegación en la que está asginado:[Nignuna]");
                }
            else{
                System.out.println("Seleccione la delegación en la que está asginado:");
            }


            entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARANUMERO,
                    FuncionesConsola.comprobaConversion.ENTERO,
                    PALABRACANCELAR,
                    esMOdificacion);


            entradaNumero = Integer.parseInt(entradaTexto);
            if(entradaNumero <= datos.getDelegaciones().size() && entradaNumero > 0 ){
                nuevoProyecto.setDelegacion(datos.getDelegaciones().get(entradaNumero-1));
            }
        }

        if(!esMOdificacion){
            nuevoProyecto.setEstado(true);
            nuevoProyecto.setFechaAlta(new Date(System.currentTimeMillis()));
        }

        return nuevoProyecto;
    }
}
