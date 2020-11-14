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

public class Personal_vista implements Vista {
    private DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public String MostrarLIstado(List listado, String salir, Usuario user){

        System.out.println("***** ***** LISTADO DE PERSONAL ***** *****");

        if(listado == null || listado.size() == 0){
            System.out.println("No hay ningún personal almacenado.");

            System.out.println("Indique que desea realizar:");
        }else{
            List<Personal> personal = (List<Personal>)listado;

            System.out.printf("%-10s %-10s %-10s %-10s %-10s\n", "INDICE", "NOMBRE", "DNI","TIPO","DELEGACIÓN");


            for(int i = 0; i < personal.size();i++){
                String del="No asignado";

                if(personal.get(i).getDelegacion() != null){
                    del = personal.get(i).getDelegacion().getNombre();
                }

                System.out.printf("%-10s %-10s %-10s %-10s %-10s\n", +(i+1),personal.get(i).getNombre(), personal.get(i).getNif_dni(),personal.get(i).getTipoString(),del);
            }


            System.out.println("Indique que desea realizar:");
            System.out.println("\t- Indique el indice del usuario a visualizar o modificar ");
        }

        System.out.println("\t- 0 Crear un nuevo.");
        System.out.println("\t- Escriba "+salir+" para volver al menu");


        return FuncionesConsola.leerConsola();
    }

    @Override
    public String MostrarUno(Object elemento, String salir, Usuario user) {

        System.out.println("***** MOSTRANDO DATOS DE PERSONA *****");
        MostrarDato((Personal)elemento);

        System.out.println("Indique que desea realizar:");;
        System.out.println("\t- 0 Modificar esta persona.");
        System.out.println("\t- 1 Borrar ha esta persona.");

        System.out.println("\t- Escriba "+salir+" para volver atras.");

        return FuncionesConsola.leerConsola();
    }


    private void MostrarDato(Personal persona){


        System.out.printf("%-5s %-5s\n", "Nombre:", persona.getNombre());
        System.out.printf("%-5s %-5s\n", "NIF:", persona.getNif_dni());
        if(persona.getFechaDeNacimiento() != null)
            System.out.printf("%-5s %-5s\n", "Fecha de nacimiento:", formatoFecha.format(persona.getFechaDeNacimiento()));
        System.out.printf("%-5s %-5s\n", "Domicilio:", persona.getDomicilio());
        System.out.printf("%-5s %-5s\n", "Tipo:", persona.getTipoString());

        if(persona.getFechaAlta() != null)
            System.out.printf("%-5s %-5s\n", "Fecha de Alta:", formatoFecha.format(persona.getFechaAlta()));

        if(persona.getEstado()){
            System.out.printf("%-5s %-5s\n", "Estado:", "Alta");
        }else{
            System.out.printf("%-5s %-5s\n", "Estado:", "Baja");

            if(persona.getFechaBaja() != null)
                System.out.printf("%-5s %-5s\n", "Fecha de Baja:", formatoFecha.format(persona.getFechaBaja()));
        }

        if(persona.getDelegacion() != null){
            System.out.printf("%-5s %-5s\n", "Delegación:", persona.getDelegacion().getNombre());
        }else{
            System.out.printf("%-5s %-5s\n", "Delegación:", "No asignado.");
        }


        switch (persona.getClass().getName()) {
            case "logicaEmpresarial.Contratados":

                    if(((Contratados)persona).getTipoContrato() != null){
                        System.out.printf("%-5s %-5s\n", "Tipo de Contrato:", ((Contratados)persona).getTipoContrato());
                    }else{
                        System.out.printf("%-5s %-5s\n", "Tipo de Contrato:", "No asignado.");
                    }

                    if(((Contratados)persona).getSalario() != null){
                        System.out.printf("%-5s %-5s\n", "Salario:", ((Contratados)persona).getSalario());
                    }else{
                        System.out.printf("%-5s %-5s\n", "Salario:", "No asignado.");
                    }
                break;

            case "logicaEmpresarial.Colaboradores":
                    if(persona.getDelegacion() != null){
                        System.out.printf("%-5s %-5s\n", "Tipo Colaboración:", ((Colaboradores)persona).getTipoString());
                    }else{
                        System.out.printf("%-5s %-5s\n", "Tipo Colaboración:", "No asignado.");
                    }
                break;

            case "logicaEmpresarial.Voluntarios":
            case "logicaEmpresarial.VoluntariosInternacionales":
                if(((Voluntarios)persona).getAreaVoluntariado() != null){
                    System.out.printf("%-5s %-5s\n", "Area:", ((Voluntarios)persona).getAreaVoluntariado());
                }else{
                    System.out.printf("%-5s %-5s\n", "Area:", "No asignado.");
                }

                if(persona.getClass().getName() == "logicaEmpresarial.VoluntariosInternacionales")
                    if(((VoluntariosInternacionales)persona).getPais() != null){
                        System.out.printf("%-5s %-5s\n", "Pais voluntariado:", ((VoluntariosInternacionales)persona).getPais());
                    }else{
                        System.out.printf("%-5s %-5s\n", "Pais voluntariado:", "No asignado.");
                    }
                break;

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
        Personal nuevoPersonal;
        Personal viejoPersonal = null;

        String entradaTexto;
        int entradaNumero = -1;
        boolean esMOdificacion = indice != -1;
        int identificarHijo = -1;

        System.out.println("Creación de nuevo personal:");
        System.out.println("Seleccione el tipo:");
        System.out.println("\t 1-Contratado");
        System.out.println("\t 2-Colaborador");
        System.out.println("\t 3-Voluntario");
        System.out.println("\t 4-Voluntario internacional");




        if(esMOdificacion){
            viejoPersonal = datos.getPersonal().get(indice);
            switch (viejoPersonal.getClass().getName()) {
                case "logicaEmpresarial.Contratados":   identificarHijo = 1;        break;
                case "logicaEmpresarial.Colaboradores":identificarHijo= 2;break;
                case "logicaEmpresarial.Voluntarios":identificarHijo = 3;break;
                case "logicaEmpresarial.VoluntariosInternacionales":identificarHijo = 4;break;
            }

            System.out.println("Seleccióne el número del tipo de personal:["+identificarHijo+"]");

            do {
                entradaTexto = FuncionesConsola.forzarEntradaNumero(FuncionesConsola.MASCARANUMERO,
                        FuncionesConsola.comprobaConversion.ENTERO,
                        PALABRACANCELAR,
                        1,
                        4, true);

                if (entradaTexto.equals("(default)")) {
                    entradaNumero = identificarHijo;
                }else {
                    entradaNumero = Integer.parseInt(entradaTexto);
                }
            }while(entradaNumero < 1);

        }else{
            System.out.println("Seleccióne el número del tipo de personal:");

            entradaTexto = FuncionesConsola.forzarEntradaNumero(FuncionesConsola.MASCARANUMERO,
                    FuncionesConsola.comprobaConversion.ENTERO,
                    PALABRACANCELAR,
                    1,
                    4, false);

            entradaNumero = Integer.parseInt(entradaTexto);


        }


        switch (entradaNumero) {
            case 1: nuevoPersonal = new Contratados();break;
            case 2: nuevoPersonal =new Colaboradores();break;
            case 3: nuevoPersonal = new Voluntarios();break;
            case 4: nuevoPersonal = new VoluntariosInternacionales();break;
            default :
                System.out.println("Se ha producido un error");
                return null;

        }



        //dni
        if(esMOdificacion)
            System.out.println("Inserte el DNI:["+viejoPersonal.getNif_dni()+"]");
        else
            System.out.println("Inserte el DNI:");

        entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARADNI,
                                                            FuncionesConsola.comprobaConversion.TEXTO,
                                                            PALABRACANCELAR,
                                                            esMOdificacion);
        if(entradaTexto != null) {
            if(entradaTexto.equals("(default)"))
                nuevoPersonal.setNif_dni(datos.getPersonal().get(indice).getNif_dni());
            else
                nuevoPersonal.setNif_dni(entradaTexto);
        }else{
            return null;
        }



        //Nombre
        if(esMOdificacion)
            System.out.println("Inserte Nombre:["+viejoPersonal.getNombre()+"]");
        else
            System.out.println("Inserte Nombre:");

        entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARATEXTO,
                                                            FuncionesConsola.comprobaConversion.TEXTO,
                                                            PALABRACANCELAR,
                                                            esMOdificacion);
        if(entradaTexto != null) {
            if(entradaTexto.equals("(default)"))
                nuevoPersonal.setNombre(datos.getPersonal().get(indice).getNombre());
            else
                nuevoPersonal.setNombre(entradaTexto);
        }else{
            return null;
        }


        //fecha de nacimiento
        if(esMOdificacion)
            System.out.println("Inserte la fecha de nacimiento, formato dd/mm/yyyy:["+formatoFecha.format( viejoPersonal.getFechaDeNacimiento())+"]");
        else
            System.out.println("Inserte la fecha de nacimiento, formato dd/mm/yyyy:");


        entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARAFECHA,
                                                            FuncionesConsola.comprobaConversion.FECHA,
                                                            PALABRACANCELAR,
                                                            esMOdificacion);
        if(entradaTexto != null) {
            try {
                if(entradaTexto.equals("(default)"))
                    nuevoPersonal.setFechaDeNacimiento(datos.getPersonal().get(indice).getFechaDeNacimiento());
                else
                    nuevoPersonal.setFechaDeNacimiento(FuncionesConsola.convertirAFEcha(entradaTexto));
            } catch (ParseException e) {
                return null;
            }
        }else{
            return null;
        }


        //domicilio

        if(esMOdificacion)
            System.out.println("Inserte el domicilio:["+viejoPersonal.getDomicilio()+"]");
        else
            System.out.println("Inserte el domicilio:");

        entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARATEXTO,
                                                            FuncionesConsola.comprobaConversion.TEXTO,
                                                            PALABRACANCELAR,
                                                            esMOdificacion);
        if(entradaTexto != null) {
            if(entradaTexto.equals("(default)"))
                nuevoPersonal.setDomicilio(datos.getPersonal().get(indice).getDomicilio());
            else
                nuevoPersonal.setDomicilio(entradaTexto);
        }else{
            return null;
        }





        switch (nuevoPersonal.getClass().getName()) {
            case "logicaEmpresarial.Contratados":


                    if(esMOdificacion)
                        System.out.println("Inserte el tipo de contrato:["+((Contratados)viejoPersonal).getTipoContrato()+"]");
                    else
                        System.out.println("Inserte el tipo de contrato:");

                    entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARATEXTO,
                            FuncionesConsola.comprobaConversion.TEXTO,
                            PALABRACANCELAR,
                            esMOdificacion);
                    if(entradaTexto != null) {
                        if(entradaTexto.equals("(default)"))
                            ((Contratados)nuevoPersonal).setTipoContrato(((Contratados)datos.getPersonal().get(indice)).getTipoContrato());
                        else
                            ((Contratados)nuevoPersonal).setTipoContrato(entradaTexto);
                    }else{
                        return null;
                    }
                break;

            case "logicaEmpresarial.Colaboradores":

                break;

            case "logicaEmpresarial.Voluntarios":
            case "logicaEmpresarial.VoluntariosInternacionales":


                if(datos.getPersonal().get(indice).getClass().getName() == "logicaEmpresarial.VoluntariosInternacionales")

                break;

        }






        //SELECCIONAR UNA DELEGACIÓN.
        if(datos.getDelegaciones().size() > 0){

            for(int i = 0; i < datos.getDelegaciones().size();i++)
                System.out.println(i+1.+"\t"+datos.getDelegaciones().get(i).getNombre());


            System.out.println("0- No seleccionar nada.");

            if(esMOdificacion) {
                if (datos.getPersonal().get(indice).getDelegacion() != null) {
                    System.out.println("Seleccione la delegación en la que está asginado:[" + datos.getPersonal().get(indice).getDelegacion().getNombre() + "]");
                } else {
                    System.out.println("Seleccione la delegación en la que está asginado:[Nignuna]");
                }
            }else{
                System.out.println("Seleccione la delegación en la que está asginado:");
            }


            entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARANUMERO,
                                                                FuncionesConsola.comprobaConversion.ENTERO,
                                                                PALABRACANCELAR,
                                                                esMOdificacion);


            entradaNumero = Integer.parseInt(entradaTexto);
            if(entradaNumero <= datos.getDelegaciones().size() && entradaNumero > 0 ){
                nuevoPersonal.setDelegacion(datos.getDelegaciones().get(entradaNumero-1));
            }
        }



      if(!esMOdificacion){
          nuevoPersonal.setEstado(true);
          nuevoPersonal.setFechaAlta(new Date(System.currentTimeMillis()));
      }

        return nuevoPersonal;
    }
}
