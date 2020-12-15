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


    @Override
    public String mostrarLIstado(List listado, String salir, Usuario user){

        FuncionesConsola.mostrarEncabezado("LISTADO DE PERSONAL");

        if(listado == null || listado.size() == 0){
            System.out.println("No hay ningún personal almacenado.");

            System.out.println("Indique que desea realizar:");
        }else{
            List<Personal> personal = (List<Personal>)listado;

            System.out.printf("%-25s %-25s %-25s %-25s %-10s\n", "INDICE", "NOMBRE", "DNI","TIPO","DELEGACIÓN");


            for(int i = 0; i < personal.size();i++){
                String del="No asignado";

                if(personal.get(i).getDelegacion() != null){
                    del = personal.get(i).getDelegacion().getNombre();
                }

                System.out.printf("%-25s %-25s %-25s %-25s %-10s\n", +(i+1),personal.get(i).getNombre(), personal.get(i).getNif_dni(),personal.get(i).getTipoString(),del);
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

        System.out.println("***** MOSTRANDO DATOS DE PERSONA *****");
        mostrarDato((Personal)elemento);

        System.out.println("Indique que desea realizar:");;
        System.out.println("\t- 0 Modificar esta persona.");
        System.out.println("\t- 1 Borrar ha esta persona.");

        System.out.println("\t- Escriba "+salir+" para volver atras.");

        return FuncionesConsola.leerConsola();
    }


    private void mostrarDato(Personal persona){


        System.out.printf("%-25s %-5s\n", "Nombre:", persona.getNombre());
        System.out.printf("%-25s %-5s\n", "NIF:", persona.getNif_dni());
        if(persona.getFechaDeNacimiento() != null)
            System.out.printf("%-25s %-5s\n", "Fecha de nacimiento:", FuncionesConsola.formatoFecha.format(persona.getFechaDeNacimiento()));
        System.out.printf("%-25s %-5s\n", "Domicilio:", persona.getDomicilio());
        System.out.printf("%-25s %-5s\n", "Tipo:", persona.getTipoString());

        if(persona.getFechaAlta() != null)
            System.out.printf("%-25s %-5s\n", "Fecha de Alta:", FuncionesConsola.formatoFecha.format(persona.getFechaAlta()));

        if(persona.getEstado()){
            System.out.printf("%-25s %-5s\n", "Estado:", "Alta");
        }else{
            System.out.printf("%-25s %-5s\n", "Estado:", "Baja");

            if(persona.getFechaBaja() != null)
                System.out.printf("%-25s %-5s\n", "Fecha de Baja:", FuncionesConsola.formatoFecha.format(persona.getFechaBaja()));
        }

        if(persona.getDelegacion() != null){
            System.out.printf("%-25s %-5s\n", "Delegación:", persona.getDelegacion().getNombre());
        }else{
            System.out.printf("%-25s %-5s\n", "Delegación:", "No asignado.");
        }


        switch (persona.getClass().getName()) {
            case "logicaEmpresarial.Contratados":

                    if(((Contratados)persona).getTipoContrato() != null){
                        System.out.printf("%-25s %-5s\n", "Tipo de Contrato:", ((Contratados)persona).getTipoContrato());
                    }else{
                        System.out.printf("%-25s %-5s\n", "Tipo de Contrato:", "No asignado.");
                    }

                    if(((Contratados)persona).getSalario() != null){
                        System.out.printf("%-25s %-5s\n", "Salario:", ((Contratados)persona).getSalario());
                    }else{
                        System.out.printf("%-25s %-5s\n", "Salario:", "No asignado.");
                    }
                break;

            case "logicaEmpresarial.Colaboradores":
                    if(persona.getTipoString() != null){
                        System.out.printf("%-25s %-5s\n", "Tipo Colaboración:", persona.getTipoString());
                    }else{
                        System.out.printf("%-25s %-5s\n", "Tipo Colaboración:", "No asignado.");
                    }
                break;

            case "logicaEmpresarial.Voluntarios":
            case "logicaEmpresarial.VoluntariosInternacionales":
                if(((Voluntarios)persona).getAreaVoluntariado() != null){
                    System.out.printf("%-25s %-5s\n", "Area:", ((Voluntarios)persona).getAreaVoluntariado());
                }else{
                    System.out.printf("%-25s %-5s\n", "Area:", "No asignado.");
                }

                if(persona.getClass().getName() == "logicaEmpresarial.VoluntariosInternacionales")
                    if(((VoluntariosInternacionales)persona).getPais() != null){
                        System.out.printf("%-25s %-5s\n", "Pais voluntariado:", ((VoluntariosInternacionales)persona).getPais());
                    }else{
                        System.out.printf("%-25s %-5s\n", "Pais voluntariado:", "No asignado.");
                    }
                break;

        }
        if(persona.getProyecto() != null){
            System.out.printf("%-25s %-5s\n", "Proyecto:", persona.getProyecto().getNombre());
        }else{
            System.out.printf("%-25s %-5s\n", "Proyecto:", "No asignado.");
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
            System.out.println("Inserte la fecha de nacimiento, formato dd/mm/yyyy:["+FuncionesConsola.formatoFecha.format( viejoPersonal.getFechaDeNacimiento())+"]");
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




        //El personal que quiere ser creado es del mismo tipo que el anterior?
        boolean permiteDefault = false;


        if(esMOdificacion && nuevoPersonal.getClass() == datos.getPersonal().get(indice).getClass())
            permiteDefault = true;

        switch (nuevoPersonal.getClass().getName()) {
            case "logicaEmpresarial.Contratados":

                //TIPO DE CONTRATO
                    if(permiteDefault)
                        System.out.println("Inserte el tipo de contrato:["+((Contratados)viejoPersonal).getTipoContrato()+"]");
                    else
                        System.out.println("Inserte el tipo de contrato:");

                    entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARATEXTO,
                            FuncionesConsola.comprobaConversion.TEXTO,
                            PALABRACANCELAR,
                            permiteDefault);

                    if(entradaTexto != null) {
                        if(entradaTexto.equals("(default)"))
                            if(permiteDefault)
                                ((Contratados)nuevoPersonal).setTipoContrato(((Contratados)datos.getPersonal().get(indice)).getTipoContrato());
                            else
                                ((Contratados)nuevoPersonal).setTipoContrato("");
                        else
                            ((Contratados)nuevoPersonal).setTipoContrato(entradaTexto);
                    }else{
                        return null;
                    }

                //SALARIO.
                if(permiteDefault)
                    System.out.println("Inserte Salario:["+((Contratados)viejoPersonal).getSalario()+"]");
                else
                    System.out.println("Inserte Salario:");

                entradaTexto= FuncionesConsola.forzarEntradaReal(FuncionesConsola. MASCARADECIMAL,
                        FuncionesConsola.comprobaConversion.REAL,
                        PALABRACANCELAR,
                        permiteDefault);

                if(entradaTexto != null) {
                    if(entradaTexto.equals("(default)"))
                        if(permiteDefault)
                            ((Contratados)nuevoPersonal).setSalario(((Contratados)datos.getPersonal().get(indice)).getSalario());
                        else
                            ((Contratados)nuevoPersonal).setSalario((float)0);
                    else
                        ((Contratados)nuevoPersonal).setSalario(Float.parseFloat(entradaTexto));
                }else{
                    return null;
                }
                break;

            case "logicaEmpresarial.Colaboradores":

                    if(permiteDefault)
                        System.out.println("Inserte tipo de colaboración:["+((Colaboradores)datos.getPersonal().get(indice)).getTipoString()+"]");
                    else
                        System.out.println("Inserte tipo de colaboración:");

                    System.out.println("\t\t -1 ECONOMICA");
                    System.out.println("\t\t -2 MANO DE OBRA ");
                    System.out.println("\t\t -3 SOCIAL");
                    System.out.println("Inserte el id:");

                    entradaTexto= FuncionesConsola.forzarEntradaNumero(FuncionesConsola.MASCARANUMERO,
                            FuncionesConsola.comprobaConversion.ENTERO,
                            PALABRACANCELAR,
                            1,3,
                            permiteDefault);

                    if(entradaTexto != null) {
                        if(entradaTexto.equals("(default)")) {
                            if (permiteDefault)
                                ((Colaboradores) nuevoPersonal).setTipoColaboracion(((Colaboradores) datos.getPersonal().get(indice)).getTipoColaboracion());
                        }else{
                            ((Colaboradores) nuevoPersonal).setIntTipoColaboracion(Integer.parseInt(entradaTexto));
                        }
                    }else{
                        return null;
                    }
                break;

            case "logicaEmpresarial.Voluntarios":
            case "logicaEmpresarial.VoluntariosInternacionales":

                //TIPO DE CONTRATO
                if(permiteDefault)
                    System.out.println("Inserte el tipo de voluntariado:["+((Voluntarios)viejoPersonal).getTipoString()+"]");
                else
                    System.out.println("Inserte el tipo de voluntariado:");

                entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARATEXTO,
                        FuncionesConsola.comprobaConversion.TEXTO,
                        PALABRACANCELAR,
                        permiteDefault);

                if(entradaTexto != null) {
                    if(entradaTexto.equals("(default)"))
                        if(permiteDefault)
                            ((Voluntarios)nuevoPersonal).setAreaVoluntariado(((Voluntarios)datos.getPersonal().get(indice)).getAreaVoluntariado());
                        else
                            ((Voluntarios)nuevoPersonal).setAreaVoluntariado("");
                    else
                        ((Voluntarios)nuevoPersonal).setAreaVoluntariado(entradaTexto);
                }else{
                    return null;
                }

                if(nuevoPersonal.getClass().getName() == "logicaEmpresarial.VoluntariosInternacionales")
                {
                        //TIPO DE CONTRATO
                        if(permiteDefault)
                            System.out.println("Inserte el pais:["+((VoluntariosInternacionales)viejoPersonal).getPais()+"]");
                        else
                            System.out.println("Inserte el pais:");

                        entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARATEXTO,
                                FuncionesConsola.comprobaConversion.TEXTO,
                                PALABRACANCELAR,
                                permiteDefault);

                        if(entradaTexto != null) {
                            if(entradaTexto.equals("(default)"))
                                if(permiteDefault)
                                    ((VoluntariosInternacionales)nuevoPersonal).setPais(((VoluntariosInternacionales)datos.getPersonal().get(indice)).getPais());
                                else
                                    ((VoluntariosInternacionales)nuevoPersonal).setPais("");
                            else
                                ((VoluntariosInternacionales)nuevoPersonal).setPais(entradaTexto);
                        }else{
                            return null;
                        }
                }

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



        //SELECCIONAR UN proyecto.
        if(datos.getProyectos().size() > 0){

            for(int i = 0; i < datos.getProyectos().size();i++)
                System.out.println(i+1.+"\t"+datos.getProyectos().get(i).getNombre());


            System.out.println("0- No seleccionar nada.");

            if(esMOdificacion) {
                if (datos.getPersonal().get(indice).getProyecto() != null) {
                    System.out.println("Seleccione el proyecto asignado:[" + datos.getPersonal().get(indice).getProyecto().getNombre() + "]");
                } else {
                    System.out.println("Seleccione el proyecto asignado:[Nignuna]");
                }
            }else{
                System.out.println("Seleccione el proyecto asignado:");
            }


            entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARANUMERO,
                    FuncionesConsola.comprobaConversion.ENTERO,
                    PALABRACANCELAR,
                    esMOdificacion);



            entradaNumero = Integer.parseInt(entradaTexto);
            if(entradaNumero <= datos.getProyectos().size() && entradaNumero > 0 ){
                nuevoPersonal.setProyecto(datos.getProyectos().get(entradaNumero-1));
            }


        }

        nuevoPersonal.setId(datos.getPersonal().get(indice).getId());
      if(!esMOdificacion){
          nuevoPersonal.setEstado(true);
          nuevoPersonal.setFechaAlta(new Date(System.currentTimeMillis()));
      }
        return nuevoPersonal;
    }
}
