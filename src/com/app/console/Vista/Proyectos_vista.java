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
    public String mostrarLIstado(List listado, String salir, Usuario user){

        FuncionesConsola.mostrarEncabezado("LISTADO DE PERSONAL");

        if(listado == null || listado.size() == 0){
            System.out.println("No hay ningún personal almacenado.");

            System.out.println("Indique que desea realizar:");
        }else{
            List<Proyecto> proyectos = (List<Proyecto>)listado;

            System.out.printf("%-10s %-10s %-10s\n", "INDICE", "NOMBRE", "TIPO");


            for(int i = 0; i < proyectos.size();i++){
                System.out.printf("%-10s %-10s %-10s %-10s %-10s\n", +(i+1),
                                                                     proyectos.get(i).getNombre(),
                                                                     proyectos.get(i).getTipoString());
            }


            System.out.println("Indique que desea realizar:");
            System.out.println("\t- Indique el indice del usuario a visualizar o modificar ");
        }

        System.out.println("\t- 0 Crear un nuevo.");
        System.out.println("\t- Escriba "+salir+" para volver al menu");

        return FuncionesConsola.leerConsola();
    }




    @Override
    public String mostrarUnElemento(Object elemento, String salir, Usuario user) {

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

        System.out.printf("%-5s %-5s\n", "Tipo:", proyecto.getTipo());

        if(proyecto.getFechaDeInicio() != null)
            System.out.printf("%-5s %-5s\n", "Fecha de Alta:", formato.format(proyecto.getFechaDeInicio()));

        if(proyecto.getEstado()){
            System.out.printf("%-5s %-5s\n", "Estado:", "Alta");
        }else{
            System.out.printf("%-5s %-5s\n", "Estado:", "Baja");
        }


    }

    @Override
    public Object crearElemento(Ong datos, String PALABRACANCEALR) {
        return solicitarNuevo(datos,-1, PALABRACANCEALR);
    }

    @Override
    public Object modificarElemento(Ong datos, int indice, String PALABRACANCEALR){
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
                2, true);


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




        if(!esMOdificacion){
            nuevoProyecto.setEstado(true);
            nuevoProyecto.setFechaAlta(new Date(System.currentTimeMillis()));
        }

        return nuevoProyecto;
    }
}
