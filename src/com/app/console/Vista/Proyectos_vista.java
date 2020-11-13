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
            System.out.println("\tINDICE\tPROYECTO\tUBICACION");

            for(int i = 0; i < proyectos.size();i++)
                System.out.println("\t"+(i+1)+"\t"+proyectos.get(i).getGetIdentificacion().getProyecto()+ "\t"+proyectos.get(i).getGetIdentificacion().getUbicacion_ubicacion());

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
        System.out.println("\t- 0 Modificar proyecto.");

        System.out.println("\t- Escriba "+salir+" para volver atras.");

        return FuncionesConsola.leerConsola();
    }

    public void MostrarDato(Proyectos proyecto){
        System.out.println("");
        System.out.println("Nombre:\t"+ proyecto.getGetIdentificacion().getNombre());
        System.out.println("Ubicacion:\t"+ proyecto.getGetIdentificacion().getUbicacion());
        System.out.println("Equipo:\t"+ proyecto.getGetIdentificacion().getEquipo());
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


        System.out.println("Creación de nuevo proyecto:");
        System.out.println("Seleccione el tipo:");
        System.out.println("\t 1-Publico");
        System.out.println("\t 2-Privado");
        System.out.println("\t 3-Local");
        System.out.println("\t 4-Internacional");

        if(esMOdificacion){
            String valor = "";
            switch (datos.getPersonal().get(indice).getClass().getName()) {
                case "Publico": valor="1";break;
                case "Privado":valor="2";break;
                case "Nacional":valor="3";break;
                case "Internacional":valor="4";break;
            }

            System.out.println("Seleccióne el número del tipo de proyecto:["+valor+"]");
        }else
            System.out.println("Seleccióne el número del tipo de proyecto:");

        entradaTexto= FuncionesConsola.forzarEntradaNumero(FuncionesConsola.MASCARANUMERO,
                FuncionesConsola.comprobaConversion.ENTERO,
                PALABRACANCELAR,
                1,
                5);


        entradaNumero = Integer.parseInt(entradaTexto);

        switch (entradaNumero) {
            case 1 : nuevoProyecto = new Publico();break;
            case 2 : nuevoProyecto = new Privado();break;
            case 3 : nuevoProyecto = new Nacional();break;
            case 4 : nuevoProyecto = new Internacional();break;
            default :
                System.out.println("Se ha producido un error");
                return null;
        }



        //dni

        if(esMOdificacion)
            System.out.println("Inserte el Indice:"+datos.getProyectos().get(indice).getGetIndice().getIndice()+"]");
        else
            System.out.println("Inserte el Indice:");

        entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARAINDICE,
                FuncionesConsola.comprobaConversion.TEXTO,
                PALABRACANCELAR,
                esMOdificacion);
        if(entradaTexto != null) {
            if(entradaTexto.equals("(default)"))
                nuevoPersonal.getGetIndice().setIndice(datos.getIndice().get(indice).getGetIndice().getIndice());
            else
                nuevoPersonal.getGetIndice().setIndice(entradaTexto);
        }else{
            return null;
        }

        //Nombre
        if(esMOdificacion)
            System.out.println("Inserte Proyecto:"+datos.getProyectos().get(indice).getGetProyecto().getProyecto()+"]");
        else
            System.out.println("Inserte Proyecto:");

        entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARATEXTO,
                FuncionesConsola.comprobaConversion.TEXTO,
                PALABRACANCELAR,
                esMOdificacion);
        if(entradaTexto != null) {
            if(entradaTexto.equals("(default)"))
                nuevoPersonal.getGetIdentificacion().setNombre(datos.getPersonal().get(indice).getGetIdentificacion().getNombre());
            else
                nuevoPersonal.getGetIdentificacion().setNombre(entradaTexto);
        }else{
            return null;
        }
        //fecha de nacimiento


        if(esMOdificacion)
            System.out.println("Inserte la fecha de nacimiento, formato dd/mm/yyyy:["+datos.getPersonal().get(indice).getGetIdentificacion().getFechaDeNacimiento()+"]");
        else
            System.out.println("Inserte la fecha de nacimiento, formato dd/mm/yyyy:");


        entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARAFECHA,
                FuncionesConsola.comprobaConversion.FECHA,
                PALABRACANCELAR,
                esMOdificacion);
        if(entradaTexto != null) {
            try {
                if(entradaTexto.equals("(default)"))
                    nuevoPersonal.getGetIdentificacion().setFechaDeNacimiento(datos.getPersonal().get(indice).getGetIdentificacion().getFechaDeNacimiento());
                else
                    nuevoPersonal.getGetIdentificacion().setFechaDeNacimiento(FuncionesConsola.convertirAFEcha(entradaTexto));
            } catch (ParseException e) {
                return null;
            }
        }else{
            return null;
        }
        //domicilio

        if(esMOdificacion)
            System.out.println("Inserte el domicilio:["+datos.getPersonal().get(indice).getGetIdentificacion().getDomicilio()+"]");
        else
            System.out.println("Inserte el domicilio:");

        entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARATEXTO,
                FuncionesConsola.comprobaConversion.TEXTO,
                PALABRACANCELAR,
                esMOdificacion);
        if(entradaTexto != null) {
            if(entradaTexto.equals("(default)"))
                nuevoPersonal.getGetIdentificacion().setDomicilio(datos.getPersonal().get(indice).getGetIdentificacion().getDomicilio());
            else
                nuevoPersonal.getGetIdentificacion().setDomicilio(entradaTexto);
        }else{
            return null;
        }

        if(datos.getDelegaciones().size() > 0){

            for(int i = 0; i < datos.getDelegaciones().size();i++)
                System.out.println(i+1.+"\t"+datos.getDelegaciones().get(i).getNombre());
            System.out.println("0- No seleccionar nada.");


            if(esMOdificacion)
                if(datos.getPersonal().get(indice).getDelegacion() != null){
                    System.out.println("Seleccione la delegación en la que está asginado:["+datos.getPersonal().get(indice).getDelegacion().getNombre()+"]");
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
            if(entradaNumero < datos.getDelegaciones().size() && entradaNumero > 0 ){
                nuevoPersonal.setDelegacion(datos.getDelegaciones().get(entradaNumero -1));
            }
        }

        return nuevoPersonal;
    }

}
