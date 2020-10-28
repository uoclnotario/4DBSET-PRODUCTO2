package com.app.console.Vista;

import logicaEmpresarial.*;

import javax.print.DocFlavor;
import java.awt.desktop.SystemEventListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

public class Personal_vista implements Vista {

    @Override
    public String MostrarLIstado(List listado, String salir, Usuario user){

        if(listado == null){



        }else{
            List<Personal> personal = (List<Personal>)listado;
            System.out.println("Listado de personal:");
            System.out.println("\tINDICE\tNOMBRE\tDNI");

            for(int i = 0; i < personal.size();i++)
                System.out.println("\t"+(i+1)+"\t"+personal.get(i).getGetIdentificacion().getNombre()+ "\t"+personal.get(i).getGetIdentificacion().getNif_dni());

            System.out.println("Indique que desea realizar:");
            System.out.println("\t- Indique el indice del usuario a visualizar o modificar ");
            System.out.println("\t- 0 Crear un nuevo.");
            System.out.println("\t- Escriba "+salir+" para volver al menu");

        }


        return FuncionesConsola.leerConsola();
    }

    @Override
    public String MostrarUno(Object elemento, String salir, Usuario user) {

        System.out.println("---MOSTRANDO DATOS DE PERSONA---");
        MostrarDato((Personal)elemento);

        System.out.println("Indique que desea realizar:");;
        System.out.println("\t- 0 Modificar esta persona.");
        System.out.println("\t- 1 Borrar ha esta persona.");

        System.out.println("\t- Escriba "+salir+" para volver atras.");

        return FuncionesConsola.leerConsola();
    }

    public void MostrarDato(Personal persona){
        System.out.println("");
        System.out.println("Nombre:\t"+ persona.getGetIdentificacion().getNombre());
        System.out.println("Fecha de nacimiento:\t"+ persona.getGetIdentificacion().getFechaDeNacimiento());
        System.out.println("Domicilio:\t"+ persona.getGetIdentificacion().getDomicilio());
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
        Personal nuevoPersonal;
        String entradaTexto;
        int entradaNumero;
        boolean esMOdificacion = indice != -1;


        System.out.println("Creación de nuevo personal:");
        System.out.println("Seleccione el tipo:");
        System.out.println("\t 1-Contratado");
        System.out.println("\t 2-Colaborador");
        System.out.println("\t 3-Voluntario");
        System.out.println("\t 4-Voluntario internacional");

        if(esMOdificacion){
            String valor = "";
            switch (datos.getPersonal().get(indice).getClass().getName()) {
                case "Contratados"->valor="1";
                case "Colaboradores"->valor="2";
                case "Voluntarios"->valor="3";
                case "VoluntariosInternacionales"->valor="4";
            }

            System.out.println("Seleccióne el número del tipo de personal:["+valor+"]");
        }else
            System.out.println("Seleccióne el número del tipo de personal:");

        entradaTexto= FuncionesConsola.forzarEntradaNumero(FuncionesConsola.MASCARANUMERO,
                FuncionesConsola.comprobaConversion.ENTERO,
                PALABRACANCELAR,
                1,
                5);


        entradaNumero = Integer.parseInt(entradaTexto);

        switch (entradaNumero) {
            case 1 -> nuevoPersonal = new Contratados();
            case 2 -> nuevoPersonal = new Colaboradores();
            case 3 -> nuevoPersonal = new Voluntarios();
            case 4 -> nuevoPersonal = new VoluntariosInternacionales();
            default -> {
                System.out.println("Se ha producido un error");
                return null;
            }
        }



        //dni

        if(esMOdificacion)
            System.out.println("Inserte el DNI:"+datos.getPersonal().get(indice).getGetIdentificacion().getNif_dni()+"]");
        else
            System.out.println("Inserte el DNI:");

        entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARADNI,
                                                            FuncionesConsola.comprobaConversion.TEXTO,
                                                            PALABRACANCELAR,
                                                            esMOdificacion);
        if(entradaTexto != null) {
            if(entradaTexto.equals("(default)"))
                nuevoPersonal.getGetIdentificacion().setNif_dni(datos.getPersonal().get(indice).getGetIdentificacion().getNif_dni());
            else
                nuevoPersonal.getGetIdentificacion().setNif_dni(entradaTexto);
        }else{
            return null;
        }

        //Nombre
        if(esMOdificacion)
            System.out.println("Inserte Nombre:"+datos.getPersonal().get(indice).getGetIdentificacion().getNombre()+"]");
        else
            System.out.println("Inserte Nombre:");

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
